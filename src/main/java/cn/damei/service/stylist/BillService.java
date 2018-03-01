package cn.damei.service.stylist;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.stylist.*;
import cn.damei.enumeration.stylist.Indirect;
import cn.damei.enumeration.stylist.Level;
import cn.damei.enumeration.stylist.Status;
import cn.damei.repository.stylist.BillDao;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
@Service
public class BillService extends CrudService<BillDao, Bill> {
    @Autowired
    private StylistService stylistService;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private ContractService contractService;
    @Autowired
    private BillDetailsService billDetailsService;
    @Autowired
    private MonthBillService monthBillService;
    private static final Double ZERO = 0d;
    private static final String PLUS_SIGN = "+";
    private static final String PERCENT_SIGN = "%";
    private static final String TITLE_TEMPLATE = "设计部%s份提成账单";
    private static final Double PERCENT_VALUE = 0.01;
    private static final Double STYLIST_RATIO = 0.9;
    private static final Double MINISTER_RATIO = 0.1;
    public void batchInsert(List<Bill> bills) {
        if (CollectionUtils.isNotEmpty(bills)) {
            for (Bill bill : bills) {
                this.entityDao.insert(bill);
                if (CollectionUtils.isNotEmpty(bill.getBillDetails())) {
                    for (BillDetails billDetails : bill.getBillDetails()) {
                        billDetails.setBillId(bill.getId());
                    }
                    billDetailsService.batchInsert(bill.getBillDetails());
                }
            }
        }
    }
    @Transactional
    public StatusDto generateBill(String generateMonth, ShiroUser logged) {
        final List<Stylist> stylists = stylistService.findAll();
        final List<Contract> contracts = contractService.findByMonth(generateMonth);
        final List<Rule> rules = ruleService.findAll();
        List<Evaluate> evaluates = evaluateService.findByMonth(generateMonth);
        if (CollectionUtils.isNotEmpty(stylists) && CollectionUtils.isNotEmpty(contracts)) {
            List<Bill> allBills = new ArrayList<>();
            Map<String, Level> stylistLevels = buildStylistLevels(evaluates, rules);
            for (final Stylist stylist : stylists) {
                List<Contract> stylistContracts = contracts.stream().filter((contract) -> stylist.getUserId().equals(contract.getEmpId())).collect(Collectors.toList());
                Level level = stylistLevels.get(stylist.getJobNo());
                if (level == null) {
                    continue;
                }
                List<BillDetails> stylistBillDetails = new ArrayList<>();
                for (Contract contract : stylistContracts) {
                    BillDetails billDetails = buildBillDetail(stylist, level, contract, rules);
                    stylistBillDetails.add(billDetails);
                }
                Double billTotalMoney = calculateBillTotalMoney(stylistBillDetails);
                Bill bill = new Bill(stylist.getName(), stylist.getJobNo(), stylist.getMobile(), generateMonth, billTotalMoney, MonthBill.Status.DRAFT);
                bill.setBillDetails(stylistBillDetails);
                allBills.add(bill);
            }
            Map<String, List<Stylist>> ministers = stylists.stream().collect(groupingBy(Stylist::getMinister));
            for (Map.Entry<String, List<Stylist>> entry : ministers.entrySet()) {
                String ministerJobNo = entry.getKey();
                List<Stylist> ministerStylists = entry.getValue();
                Double ministerBillMoney = calculateMinisterBillMoney(ministerStylists, allBills);
                Bill bill = new Bill(ministerStylists.get(0).getMinisterName(), ministerJobNo, ministerStylists.get(0).getMinisterMobile(), generateMonth, ministerBillMoney, MonthBill.Status.DRAFT);
                allBills.add(bill);
            }
            MonthBill monthBill = new MonthBill();
            Double monthBillMoney = calculateMonthTotalMoney(allBills);
            monthBill.setTitle(String.format(TITLE_TEMPLATE, DateUtils.format(DateUtils.parse(generateMonth, DateUtils.YYYY_MM), DateUtils.CHINESE_YYYY_MM)));
            monthBill.setMonth(generateMonth);
            monthBill.setTotalMoney(monthBillMoney);
            monthBill.setStatus(MonthBill.Status.DRAFT);
            monthBill.setCreateUser(logged.getId());
            monthBill.setCreateUserName(logged.getName());
            monthBill.setCreateTime(new Date());
            monthBillService.insert(monthBill);
            for (Bill bill : allBills) {
                bill.setMonthBillId(monthBill.getId());
            }
            this.batchInsert(allBills);
            return StatusDto.buildSuccess(MapUtils.of("monthBill", monthBill, "bills", allBills));
        }
        return StatusDto.buildWithCode("2", "没有可以生成的设计师账单！");
    }
    private Double calculateMonthTotalMoney(List<Bill> bills) {
        Double totalMoney = ZERO;
        if (CollectionUtils.isNotEmpty(bills)) {
            for (Bill bill : bills) {
                totalMoney += Optional.ofNullable(bill.getTotalMoney()).orElse(ZERO);
            }
        }
        return totalMoney;
    }
    private Double calculateMinisterBillMoney(List<Stylist> ministerStylists, List<Bill> allBills) {
        Double billMoney = 0d;
        if (CollectionUtils.isNotEmpty(ministerStylists) && CollectionUtils.isNotEmpty(allBills)) {
            for (Bill bill : allBills) {
                for (Stylist stylist : ministerStylists) {
                    if (bill.getJobNo().equals(stylist.getJobNo())) {
                        billMoney += (bill.getTotalMoney()) * MINISTER_RATIO;
                    }
                }
            }
        }
        return billMoney;
    }
    private Map<String, Level> buildStylistLevels(List<Evaluate> evaluates, List<Rule> rules) {
        Map<String, Level> stylistLevels = new HashMap<>();
        evaluates.sort((e1, e2) -> e1.getScore() >= e2.getScore() ? -1 : 1);
        int start = 0;
        int end = 0;
        for (Level level : Level.values()) {
            Double levelRatio1 = findRuleByCode(rules, level.name()).getRatio1();
            end += (int) Math.floor((levelRatio1 * PERCENT_VALUE * evaluates.size()));
            for (; start < end; start++) {
                if (start >= evaluates.size()) {
                    break;
                }
                stylistLevels.put(evaluates.get(start).getJobNo(), level);
            }
        }
        for (; end < evaluates.size(); end++) {
            stylistLevels.put(evaluates.get(end).getJobNo(), Level.D);
        }
        return stylistLevels;
    }
    private Rule findRuleByCode(List<Rule> rules, String code) {
        if (CollectionUtils.isNotEmpty(rules) && StringUtils.isNotBlank(code)) {
            for (Rule rule : rules) {
                if (code.equals(rule.getCode())) {
                    return rule;
                }
            }
        }
        throw new RuntimeException("this rule's code not find!");
    }
    private Double calculateBillTotalMoney(final List<BillDetails> stylistBillDetails) {
        Double totalMoney = 0D;
        if (CollectionUtils.isNotEmpty(stylistBillDetails)) {
            for (BillDetails billDetails : stylistBillDetails) {
                totalMoney += (billDetails.getBillMoney() == null ? 0D : billDetails.getBillMoney());
            }
        }
        return totalMoney;
    }
    private BillDetails buildBillDetail(final Stylist stylist, final Level level, final Contract contract, final List<Rule> rules) {
        if (stylist == null || level == null || contract == null || CollectionUtils.isEmpty(rules)) {
            throw new IllegalArgumentException("计算提成账单详情，缺少必要参数！");
        }
        BillDetails billDetails = new BillDetails()
                .setCustomerName(contract.getCustomerName())
                .setContractName(contract.getName())
                .setProjectMoney(contract.getMoney())
                .setContractStatus(contract.getStatus().getStatus())
                .setTaxesMoney(contract.getTaxesMoney())
                .setManagerMoney(contract.getManagerMoney())
                .setDesignMoney(contract.getDesignMoney())
                .setRemoteMoney(contract.getRemoteMoney())
                .setOthersMoney(contract.getOthersMoney())
                .setPrivilegeMoney(contract.getPrivilegeMoney());
        Double billMoney = calculateBillDetailsMoney(contract, level, rules);
        billDetails.setBillMoney(billMoney);
        return billDetails;
    }
    private Double calculateBillDetailsMoney(final Contract contract, final Level level, final List<Rule> rules) {
        if (Optional.ofNullable(contract).map(Contract::getStatus).map(ContractStatus::getStatus).orElse(null) == null || level == null || CollectionUtils.isEmpty(rules)) {
            throw new IllegalArgumentException("计算详情账单，必要参数为空！");
        }
        Status status = contract.getStatus().getStatus();
        Double billRatio = findRuleByCode(rules, status.name()).getRatio1() * PERCENT_VALUE;
        StringBuilder calculateDetails = new StringBuilder();
        Double directRatio = findRuleByCode(rules, level.name()).getRatio2();
        Double directMoney = Optional.ofNullable(contract.getMoney()).orElse(ZERO) * directRatio * PERCENT_VALUE * billRatio;
        if (Status.THREE_STAGE.equals(status)) {
            if (contract.getModifyMoney() != null) {
                directMoney += (Optional.ofNullable(contract.getModifyMoney()).orElse(ZERO) - Optional.ofNullable(contract.getMoney()).orElse(ZERO)) * directRatio * PERCENT_VALUE;
            } else {
                throw new IllegalArgumentException("计算详情账单缺少合同确认金额！合同编号为" + contract.getContractNo());
            }
        }
        calculateDetails.append(Optional.ofNullable(contract.getMoney()).orElse(ZERO) * directRatio).append(PERCENT_SIGN);
        Double taxesRatio = findRuleByCode(rules, Indirect.TAXES.name()).getRatio1();
        Double taxesMoney = OptionalDouble.of(contract.getTaxesMoney()).orElse(ZERO) * taxesRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(OptionalDouble.of(contract.getTaxesMoney()).orElse(ZERO) * taxesRatio * billRatio).append(PERCENT_SIGN);
        Double managerRatio = findRuleByCode(rules, Indirect.MANAGER.name()).getRatio1();
        Double managerMoney = Optional.ofNullable(contract.getManagerMoney()).orElse(ZERO) * managerRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(Optional.ofNullable(contract.getManagerMoney()).orElse(ZERO) * managerRatio * billRatio).append(PERCENT_SIGN);
        Double designRatio = findRuleByCode(rules, Indirect.DESIGN.name()).getRatio1();
        Double designMoney = Optional.ofNullable(contract.getDesignMoney()).orElse(ZERO) * designRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(Optional.ofNullable(contract.getDesignMoney()).orElse(ZERO) * designRatio * billRatio).append(PERCENT_SIGN);
        Double remoteRatio = findRuleByCode(rules, Indirect.REMOTE.name()).getRatio1();
        Double remoteMoney = Optional.ofNullable(contract.getRemoteMoney()).orElse(ZERO) * remoteRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(Optional.ofNullable(contract.getRemoteMoney()).orElse(ZERO) * remoteRatio * billRatio).append(PERCENT_SIGN);
        Double otherRatio = findRuleByCode(rules, Indirect.OTHERS.name()).getRatio1();
        Double otherMoney = Optional.ofNullable(contract.getOthersMoney()).orElse(ZERO) * otherRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(Optional.ofNullable(contract.getOthersMoney()).orElse(ZERO) * otherRatio * billRatio).append(PERCENT_SIGN);
        Double privilegeRatio = findRuleByCode(rules, Indirect.PRIVILEGE.name()).getRatio1();
        Double privilegeMoney = Optional.ofNullable(contract.getPrivilegeMoney()).orElse(ZERO) * privilegeRatio * PERCENT_VALUE * billRatio;
        calculateDetails.append(PLUS_SIGN).append(Optional.ofNullable(contract.getPrivilegeMoney()).orElse(ZERO) * privilegeRatio * billRatio).append(PERCENT_SIGN);
        return directMoney + taxesMoney + managerMoney + designMoney + remoteMoney + otherMoney - privilegeMoney;
    }
    @Transactional
    public void commit(Long id) {
        if (id != null) {
            MonthBill monthBill = monthBillService.getById(id);
            if (monthBill != null) {
                monthBillService.changeStatusByMonth(monthBill.getMonth(),MonthBill.Status.DRAFT);
                monthBillService.changeStatus(monthBill.getId(),MonthBill.Status.NORMAL);
            }
        }
    }
    @Transactional
    public void rollback(Long id) {
        if (id != null) {
            MonthBill monthBill = monthBillService.getById(id);
            if(monthBill != null){
                List<Bill> bills = this.getByMonthBillId(monthBill.getId());
                List<Long> billIds = new ArrayList<>();
                for(Bill bill : bills){
                    billIds.add(bill.getId());
                }
                billDetailsService.deleteByBillIds(billIds);
                this.entityDao.deleteByIds(billIds);
                monthBillService.deleteById(monthBill.getId());
            }
        }
    }
    public List<Bill> getByMonthBillId(Long id) {
        if(id != null){
            return this.entityDao.getByMonthBillId(id);
        }
        return Collections.emptyList();
    }
}