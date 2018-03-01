package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.mdnicontract.StylistContract;
import cn.damei.entity.stylist.Contract;
import cn.damei.entity.stylist.ContractStatus;
import cn.damei.entity.stylist.Stylist;
import cn.damei.enumeration.stylist.Status;
import cn.damei.repository.mdnicontract.StylistContractDao;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import java.util.*;
@Service
public class MdniContractService {
    @Autowired
    private StylistContractDao stylistContractDao;
    @Autowired
    private StylistService stylistService;
    private static final Double MANAGER_RATIO = 0.04;
    private static final Double TAXES_RATIO = 0.0341;
    private Logger logget = LoggerFactory.getLogger(getClass());
    public List<Contract> findByMonth(String currentMonth) {
        if (StringUtils.isNotBlank(currentMonth)) {
            Map<String, String> params = MapUtils.of(StylistContractDao.MONTH, currentMonth);
            List<StylistContract> stylistContracts = this.getContractByCondition(params);
            Date month = DateUtils.parse(currentMonth, DateUtils.YYYY_MM);
            List<Contract> contracts = transfer2LocalContract(stylistContracts, month);
            List<Stylist> stylists = stylistService.findAll();
            buildContractEmpId(contracts, stylists);
            return contracts;
        }
        return Collections.emptyList();
    }
    public List<Contract> findByStylistAndMonth(Stylist stylist, String searchMonth) {
        Map<String, String> params = MapUtils.of(StylistContractDao.MONTH, searchMonth, StylistContractDao.STYLIST_MOBILE, stylist.getMobile());
        List<StylistContract> stylistContracts = this.getContractByCondition(params);
        Date month = DateUtils.parse(searchMonth, DateUtils.YYYY_MM);
        List<Contract> contracts = transfer2LocalContract(stylistContracts, month);
        if (CollectionUtils.isNotEmpty(contracts)) {
            for (Contract contract : contracts) {
                contract.setEmpId(stylist.getUserId());
            }
        }
        return contracts;
    }
    private void buildContractEmpId(List<Contract> contracts, List<Stylist> stylists) {
        if (CollectionUtils.isNotEmpty(contracts) && CollectionUtils.isNotEmpty(stylists)) {
            Map<String, Long> stylistEmpIds = buildStylistMap(stylists);
            Iterator<Contract> iterator = contracts.iterator();
            while (iterator.hasNext()) {
                Contract contract = iterator.next();
                Long empId = stylistEmpIds.get(contract.getMobile());
                if (empId == null) {
                    iterator.remove();
                    continue;
                }
                contract.setEmpId(empId);
            }
        }
    }
    private Map<String, Long> buildStylistMap(List<Stylist> stylists) {
        Map<String, Long> stylistMap = new HashMap<>();
        if (CollectionUtils.isNotEmpty(stylists)) {
            for (Stylist stylist : stylists) {
                if (StringUtils.isNotBlank(stylist.getMobile()) && stylist.getUserId() != null) {
                    stylistMap.put(stylist.getMobile(), stylist.getUserId());
                }
            }
        }
        return stylistMap;
    }
    private List<Contract> transfer2LocalContract(List<StylistContract> stylistContracts, Date month) {
        List<Contract> contracts = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(stylistContracts)) {
            for (StylistContract stylistContract : stylistContracts) {
                if (stylistContract.getFirstAmountPayed().equals(StylistContract.PAYED) && hasSomeMonth(month, stylistContract.getFirstAmountTime())) {
                    Contract contract = buildBaseContract(stylistContract);
                    contract.setStatus(new ContractStatus(null, Status.FIRST_STAGE, stylistContract.getFirstAmountTime()));
                    contracts.add(contract);
                }
                if (stylistContract.getMediumAmountPayed().equals(StylistContract.PAYED) && hasSomeMonth(month, stylistContract.getMediumAmountTime())) {
                    Contract contract = buildBaseContract(stylistContract);
                    contract.setMobile(stylistContract.getStylistMobile());
                    contract.setStatus(new ContractStatus(null, Status.SECOND_STAGE, stylistContract.getMediumAmountTime()));
                    contracts.add(contract);
                }
                if (stylistContract.getFinalAmountPayed().equals(StylistContract.PAYED) && hasSomeMonth(month, stylistContract.getFinalAmountTime())) {
                    Contract contract = buildBaseContract(stylistContract);
                    contract.setStatus(new ContractStatus(null, Status.THREE_STAGE, stylistContract.getFirstAmountTime()));
                    contracts.add(contract);
                    boolean firstHalfYear = DateUtils.isFirstHalfYear(stylistContract.getActualStartTime());
                    Date fourthStageTime = null;
                    if (firstHalfYear) {
                        fourthStageTime = DateUtils.getAssignMonthAndDay(stylistContract.getFirstAmountTime(), Calendar.OCTOBER, 15);
                    } else {
                        fourthStageTime = DateUtils.getNextAssignMonthAndDay(stylistContract.getFirstAmountTime(), Calendar.JULY, 15);
                    }
                    Contract fourthContract = buildBaseContract(stylistContract);
                    fourthContract.setStatus(new ContractStatus(null, Status.FOURTH_STAGE, fourthStageTime));
                    contracts.add(fourthContract);
                }
            }
        }
        return contracts;
    }
    private Contract buildBaseContract(StylistContract stylistContract) {
        Contract contract = new Contract();
        contract.setCustomerName(stylistContract.getCustomerName());
        contract.setName(stylistContract.getContractName());
        contract.setContractNo(stylistContract.getContractNo());
        contract.setMoney(stylistContract.getTotalMoney());
        contract.setTaxesMoney(stylistContract.getTaxesMoney());
        contract.setManagerMoney(stylistContract.getManagerMoney());
        contract.setMobile(stylistContract.getStylistMobile());
        contract.setOrderId(stylistContract.getOrderId());
        contract.setOrderNo(stylistContract.getOrderNo());
        contract.setTaxesMoney(stylistContract.getTotalMoney() * TAXES_RATIO);
        contract.setManagerMoney(stylistContract.getTotalMoney() * MANAGER_RATIO);
        return contract;
    }
    private boolean hasSomeMonth(Date srcDate, Date destDate) {
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(srcDate);
        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(destDate);
        return calendar1.get(Calendar.YEAR) == calendar2.get(Calendar.YEAR) && calendar1.get(Calendar.MONTH) == calendar2.get(Calendar.MONTH);
    }
    private List<StylistContract> getContractByCondition(Map<String, ? extends Object> parMap) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return stylistContractDao.getByCondition(parMap);
        } catch (Exception e) {
            logget.error("查询设计师合同失败！", e.getMessage());
            throw new RuntimeException("查询设计师合同失败！", e);
        } finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
