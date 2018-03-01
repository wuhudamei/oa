package cn.damei.service.budget;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.service.CrudService;
import cn.damei.dto.budget.BudgetDetailDto;
import cn.damei.dto.budget.BudgetDto;
import cn.damei.entity.account.User;
import cn.damei.entity.budget.Budget;
import cn.damei.entity.budget.BudgetDetails;
import cn.damei.entity.budget.BudgetMoney;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.budget.BudgetDao;
import cn.damei.repository.budget.BudgetDetailsDao;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MathUtils;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class BudgetService extends CrudService<BudgetDao, Budget> {
    @Autowired
    private BudgetDetailsDao budgetDetailsDao;
    @Autowired
    private ApplySequenceService applySequenceService;
    @Autowired
    private WfProcessService wfProcessService;
    @Autowired
    private YearBudgetService yearBudgetService;
    static final List<String> twelveMonth = Lists.newArrayList("01", "02", "03", "04", "05", "06", "07", "08", "09",
            "10", "11", "12");
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(Budget budget, ShiroUser loggedUser, boolean isDraft) {
        List<BudgetDetails> paymentDetails = budget.getBudgetDetails();
        if (isDraft) {
            budget.setStatus(ApplyStatus.DRAFT);
        } else {
            budget.setStatus(ApplyStatus.APPROVALING);
        }
        budget.setCompany(new MdniOrganization(loggedUser.getCompanyId()));
        budget.setUpdateUser(loggedUser.getId());
        budget.setUpdateDate(new Date());
        if (budget.getId() == null) {
            String applyTitle = DateUtils.format(DateUtils.parse(budget.getBudgetDate(), DateUtils.YYYY_MM),
                    DateUtils.CHINESE_YYYY_MM) + " 预算申请";
            String applyCode = applySequenceService.getSequence(WfApplyTypeEnum.BUDGET);
            budget.setApplyTitle(applyTitle);
            budget.setApplyCode(applyCode);
            budget.setCreateUser(new User(loggedUser.getId()));
            budget.setCreateDate(new Date());
            this.entityDao.insert(budget);
        } else {
            this.entityDao.update(budget);
            budgetDetailsDao.deleteByBudgetId(budget.getId());
        }
        for (BudgetDetails budgetDetail : paymentDetails) {
            budgetDetail.setBudgetId(budget.getId());
        }
        budgetDetailsDao.batchInsert(paymentDetails);
        if (!isDraft) {
            try {
                insertWf(budget, loggedUser);
            } catch (Exception e) {
                logger.error("添加年度预算，调用工作流失败！");
                throw new RuntimeException("添加年度预算，调用工作流失败！");
            }
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public boolean commit(Budget budget, ShiroUser loggedUser) {
        budget.setStatus(ApplyStatus.APPROVALING);
        try {
            this.entityDao.update(budget);
            insertWf(budget, loggedUser);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("预算申请调用工作流失败！");
        }
    }
    public Budget getDetails(Long budgetId) {
        Budget budget = this.entityDao.getById(budgetId);
        if (budget == null) {
            return null;
        }
        List<BudgetDetails> budgetDetails = budgetDetailsDao.getByBudgetId(budgetId);
        budget.setBudgetDetails(budgetDetails);
        return budget;
    }
    public Long sumBudgetByCompany(Long companyId) {
        return this.entityDao.sumBudgetByCompany(companyId);
    }
    public BudgetDto buildBudgetDetail(Long id) {
        BudgetDto dto;
        try {
            Budget budget = this.getDetails(id);
            Assert.notNull(budget);
            dto = BudgetDto.fromBudget(budget);
            Long type = budget.getType();
            Long companyId = budget.getCompany().getId();
            String thisYearMonth = budget.getBudgetDate();
            String thisYear = thisYearMonth.substring(0, 4);
            String thisMonth = thisYearMonth.substring(5);
            List<String> months = twelveMonth.stream().filter((o) -> o.compareTo(thisMonth) <= 0)
                    .collect(Collectors.toList());
            String lastYearMonth = DateUtils.lastMonthString(thisYearMonth, "yyyy-MM");
            String lastMonth = lastYearMonth.substring(5);
            Map<Long, Double> lastMonthBudgetTotalMap = this.getMonthBudgetMap(lastYearMonth, type, companyId);
            Map<Long, Double> thisMonthBudgetTotalMap = this.getMonthBudgetMapExcludeId(thisYearMonth, type, companyId,
                    id);
            Map<Long, Map<String, Double>> yearBudgetMap = this.yearBudgetService.getYearBudgetByType(type,
                    thisYear, companyId);
            Map<Long, Map<String, Double>> monthBudgetMap = this.getMonthBudgetTotalMap(thisYear, type, companyId);
            Map<Long, Double> lastMonthExecutionMap = this.getExecution(lastYearMonth, type, companyId);
            Map<Long, Double> thisYearExecutionMap = this.getExecution(thisYear, type, companyId);
            List<BudgetDetailDto> detailDtos = Lists
                    .newArrayListWithCapacity(CollectionUtils.size(budget.getBudgetDetails()));
            for (BudgetDetails details : budget.getBudgetDetails()) {
                Long subId = details.getCostItemId();
                Map<String, Double> subYearBudgetMap = yearBudgetMap.get(subId);
                if (subYearBudgetMap == null) {
                    subYearBudgetMap = Maps.newHashMap();
                }
                Double lastMonthBudgetMoney = this.getIfNotNullOrZero(lastMonthBudgetTotalMap.get(subId),
                        subYearBudgetMap.get(lastMonth));
                Double lastMonthExecution = lastMonthExecutionMap.get(subId) == null ? 0d
                        : lastMonthExecutionMap.get(subId);
                Double thisMonthBudget = details.getMoney();
                String remark = details.getRemark();
                Double thisMonthBudgetTotal = thisMonthBudgetTotalMap.get(id) == null ? 0d
                        : thisMonthBudgetTotalMap.get(id);
                Double thisyearBudgetTotal = this.getYearlBudgetTotal(subId, months, monthBudgetMap.get(subId),
                        yearBudgetMap.get(subId));
                Double thisYearExecution = thisYearExecutionMap.get(subId) == null ? 0d
                        : thisYearExecutionMap.get(subId);
                BudgetDetailDto detailDto = new BudgetDetailDto().setName(details.getCostItemName())
                        .setLastMonthBudget(lastMonthBudgetMoney).setLastMonthExecution(lastMonthExecution)
                        .setThisMonthBudget(thisMonthBudget).setRemark(remark)
                        .setThisMonthBudgetTotal(thisMonthBudgetTotal).setThisYearBudgetTotal(thisyearBudgetTotal)
                        .setThisYearExecution(thisYearExecution);
                detailDtos.add(detailDto);
            }
            dto.setDetails(detailDtos);
            return dto;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public Map<Long, Double> getExecution(String date, Long type, Long companyId) {
        Map<Long, Double> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(date) && type != null && companyId != null) {
            List<BudgetMoney> executions = this.entityDao.getExecution(date, type, companyId);
            map = this.reduceBudgetMoney2Map(executions);
        }
        return map;
    }
    Double getYearlBudgetTotal(Long subId, List<String> months, Map<String, Double> monthBudgetMap,
                               Map<String, Double> yearBudgetMap) {
        Double sum = 0d;
        if (subId != null && CollectionUtils.isNotEmpty(months)) {
            if (monthBudgetMap != null || yearBudgetMap != null) {
                for (String month : months) {
                    Double monthBudget = (monthBudgetMap == null || monthBudgetMap.size() == 0) ? 0d
                            : (monthBudgetMap.get(month) == null ? 0d : monthBudgetMap.get(month));
                    Double yearBudget = (yearBudgetMap == null || yearBudgetMap.size() == 0) ? 0d
                            : (yearBudgetMap.get(month) == null ? 0d : yearBudgetMap.get(month));
                    sum += this.getIfNotNullOrZero(monthBudget, yearBudget);
                }
            }
        }
        return sum;
    }
    public Map<Long, Map<String, Double>> getMonthBudgetTotalMap(String date, Long type, Long companyId) {
        Map<Long, Map<String, Double>> map = Maps.newHashMap();
        if (type != null & StringUtils.isNotBlank(date) && companyId != null) {
            List<BudgetMoney> executions = this.entityDao.getMonthBudgetGroupByMonth(date, type, companyId);
            if (CollectionUtils.isNotEmpty(executions)) {
                map = executions.stream().collect(Collectors.groupingBy(BudgetMoney::getSubId,
                        Collectors.groupingBy((o) -> o.getDate().substring(5),
                                Collectors.mapping(BudgetMoney::getMoney, Collectors.reducing(0d, (m, n) -> m + n)))));
            }
        }
        return map;
    }
    public Map<Long, Double> getMonthBudgetMap(String date, Long type, Long companyId) {
        Map<Long, Double> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(date) && type != null && companyId != null) {
            List<BudgetMoney> executions = this.entityDao.getMonthBudget(date, type, companyId);
            map = this.reduceBudgetMoney2Map(executions);
        }
        return map;
    }
    private void insertWf(Budget budget, ShiroUser loggedUser) throws Exception {
        WfProcessParam param = new WfProcessParam();
        param.setFormId(budget.getId());
        param.setApplyUserId(loggedUser.getId());
        param.setApplyTitle(budget.getApplyTitle());
        param.setApplyCode(budget.getApplyCode());
        param.setCompanyId(loggedUser.getCompanyId());
        param.setDepartmentId(loggedUser.getDepartmentId());
        param.setOrgId(loggedUser.getOrgId());
        param.setApplyType(WfApplyTypeEnum.BUDGET.name());
        param.setGenertaeWfProcessType(2L);
        param.setApplyAmount(budget.getTotalMoney());
        wfProcessService.createWf(param);
    }
    private Map<Long, Double> reduceBudgetMoney2Map(List<BudgetMoney> executions) {
        Map<Long, Double> map = Maps.newHashMap();
        if (CollectionUtils.isNotEmpty(executions)) {
            map = executions.stream().collect(Collectors.groupingBy(BudgetMoney::getSubId,
                    Collectors.mapping(BudgetMoney::getMoney, Collectors.reducing(0d, (m, n) -> m + n))));
        }
        return map;
    }
    private Double getIfNotNullOrZero(Double n1, Double n2) {
        if (n1 != null && !MathUtils.eq(0d, n1)) {
            return n1;
        } else if (n2 != null) {
            return n2;
        } else {
            return 0d;
        }
    }
    public Map<Long, Double> getMonthBudgetMapExcludeId(String date, Long type, Long companyId, Long excludeId) {
        Map<Long, Double> map = Maps.newHashMap();
        if (StringUtils.isNotBlank(date) && type != null && companyId != null) {
            List<BudgetMoney> executions = this.entityDao.getMonthBudgetExcludeId(date, type, companyId, excludeId);
            map = this.reduceBudgetMoney2Map(executions);
        }
        return map;
    }
}