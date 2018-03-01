package cn.damei.service.budget;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.budget.YearBudget;
import cn.damei.entity.budget.YearBudgetDetail;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.budget.YearBudgetDao;
import cn.damei.repository.budget.YearBudgetDetailDao;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.shiro.ShiroUser;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class YearBudgetService extends CrudService<YearBudgetDao, YearBudget> {
    @Autowired
    private ApplySequenceService applySequenceService;
    @Autowired
    private YearBudgetDetailService YearBudgetDetailService;
    @Autowired
    private YearBudgetDetailDao yearBudgetDetailDao;
    @Autowired
    private WfProcessService wfProcessService;
    @Autowired
    private MdniDictionaryService mdniDictionaryService;
    @Transactional(rollbackFor = Exception.class)
    public void createOrUpdate(YearBudget yearBudget, ShiroUser loggedUser, boolean isDraft, boolean isSubmit) {
        if (!isSubmit) {
            yearBudget.setStatus(ApplyStatus.DRAFT);
        } else {
            yearBudget.setStatus(ApplyStatus.APPROVALING);
        }
        yearBudget.setApplyCompany(new MdniOrganization(loggedUser.getCompanyId()));
        yearBudget.setCreateTime(new Date());
        if (yearBudget.getId() == null) {
            String applyTitle = yearBudget.getBudgetYear() + "年 " + yearBudget.getApplyTitle() + "预算申请";
            String applyCode = applySequenceService.getSequence(WfApplyTypeEnum.YEARBUDGET);
            yearBudget.setApplyTitle(applyTitle);
            yearBudget.setApplyCode(applyCode);
            this.entityDao.insert(yearBudget);
        } else {
            this.entityDao.update(yearBudget);
            YearBudgetDetailService.deleteByBudgetId(yearBudget.getId());
        }
        List<YearBudgetDetail> yearBudgetDetailList = yearBudget.getYearBudgetDetailList();
        for (YearBudgetDetail yearBudgetDetail : yearBudgetDetailList) {
            yearBudgetDetail.setYearBudgetId(yearBudget.getId());
        }
        try {
            yearBudgetDetailDao.batchInsert(yearBudgetDetailList);
            if (isSubmit) {
                insertWf(yearBudget, loggedUser);
            }
        } catch (Exception e) {
            throw new RuntimeException("年度预算调用工作流失败！");
        }
    }
    @Transactional(rollbackFor = Exception.class)
    public void commit(YearBudget yearBudget, ShiroUser loggedUser) {
        yearBudget.setStatus(ApplyStatus.APPROVALING);
        try {
            this.entityDao.update(yearBudget);
            insertWf(yearBudget, loggedUser);
        } catch (Exception e) {
            throw new RuntimeException("年度预算调用工作流失败！");
        }
    }
    @Transactional
    public void deleteYearAndDetails(Long id) {
        this.entityDao.deleteById(id);
        yearBudgetDetailDao.deleteByBudgetId(id);
    }
    public Map<Long, Map<String, Double>> getYearBudgetByType(Long type, String year, Long companyId) {
        Map<Long, Map<String, Double>> data = new HashMap<>();
        List<MdniDictionary> dictionaryList = mdniDictionaryService.findChildNodeByParent(type);
        for (MdniDictionary dictionary : dictionaryList) {
            Map<String, Double> budgetData = new HashMap<>();
            YearBudgetDetail yearBudgetDetail = yearBudgetDetailDao.getBySubjectCode(dictionary.getId(), year, companyId);
            if (yearBudgetDetail == null) {
                data.put(dictionary.getId(), new HashMap<>());
                continue;
            }
            budgetData.put("01", yearBudgetDetail.getJanuary().doubleValue());
            budgetData.put("02", yearBudgetDetail.getFebruary().doubleValue());
            budgetData.put("03", yearBudgetDetail.getMarch().doubleValue());
            budgetData.put("04", yearBudgetDetail.getApril().doubleValue());
            budgetData.put("05", yearBudgetDetail.getMay().doubleValue());
            budgetData.put("06", yearBudgetDetail.getJune().doubleValue());
            budgetData.put("07", yearBudgetDetail.getJuly().doubleValue());
            budgetData.put("08", yearBudgetDetail.getAugust().doubleValue());
            budgetData.put("09", yearBudgetDetail.getSeptember().doubleValue());
            budgetData.put("10", yearBudgetDetail.getOctober().doubleValue());
            budgetData.put("11", yearBudgetDetail.getNovember().doubleValue());
            budgetData.put("12", yearBudgetDetail.getDecember().doubleValue());
            data.put(dictionary.getId(), budgetData);
        }
        return data;
    }
    public int getByTypeAndYear(String type, Integer budgetYear, Long applyCompany) {
        return this.entityDao.getByTypeAndYear(type, budgetYear, applyCompany);
    }
    private void insertWf(YearBudget budget, ShiroUser loggedUser) throws Exception {
        WfProcessParam param = new WfProcessParam();
        param.setFormId(budget.getId());
        param.setApplyUserId(loggedUser.getId());
        param.setApplyTitle(budget.getApplyTitle());
        param.setApplyCode(budget.getApplyCode());
        param.setCompanyId(loggedUser.getCompanyId());
        param.setDepartmentId(loggedUser.getDepartmentId());
        param.setOrgId(loggedUser.getOrgId());
        param.setApplyType(WfApplyTypeEnum.YEARBUDGET.name());
        param.setGenertaeWfProcessType(2L);
        param.setApplyAmount(budget.getTotalMoney());
        wfProcessService.createWf(param);
    }
}