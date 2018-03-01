package cn.damei.service.oa;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.entity.oa.ApplyPurchase;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.FirstTypes;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.oa.ApplyPurchaseDao;
import cn.damei.service.budget.BudgetService;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
@Service
public class ApplyPurchaseService extends CrudService<ApplyPurchaseDao, ApplyPurchase> {
    @Autowired
    ApplySequenceService applySequenceService;
    @Autowired
    MdniDictionaryService mdniDictionaryService;
    @Autowired
    EmployeeOrgService employeeOrgService;
    @Autowired
    WfProcessService wfProcessService;
    @Autowired
    BudgetService budgetService;
    @Autowired
    PaymentService paymentService;
    @Transactional
    public int add(ApplyPurchase applyPurchase, boolean isSubmit) {
        ShiroUser loginUser = WebUtils.getLoggedUser();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        String applyTitle = "";
        String applyCode = "";
        try {
            Date date = sdf.parse(applyPurchase.getPurchaseMonth());
            applyTitle = DateUtils.format(date, "yyyy年MM月") + " 采购申请";
            applyCode = applySequenceService.getSequence(WfApplyTypeEnum.PURCHASE);
            applyPurchase.setCreateTime(new Date());
            applyPurchase.setApplyUser(loginUser.getId());
            applyPurchase.setApplyTitle(applyTitle);
            applyPurchase.setApplyCode(applyCode);
            if (isSubmit && applyPurchase.getStatus()==null) {
                applyPurchase.setStatus(ApplyStatus.APPROVALING);
            }else if (isSubmit && (applyPurchase.getStatus().equals(ApplyStatus.DRAFT) ||
                    applyPurchase.getStatus().equals(ApplyStatus.REFUSE))) {
                applyPurchase.setStatus(ApplyStatus.APPROVALING);
            } else {
                applyPurchase.setStatus(ApplyStatus.DRAFT);
            }
            applyPurchase.setApplyCompany(employeeOrgService.getDirectByEmpId(loginUser.getId()).getCompany().getId());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        int result = 0;
        try {
			if (applyPurchase.getId() == null) {
			    result = this.entityDao.insert(applyPurchase);
			} else {
			    result = this.entityDao.update(applyPurchase);
			}
			//如果是提交，走审批流程
			if (result > 0 && isSubmit) {
			    createWf(applyPurchase, loginUser);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
        return result;
    }
    public ApplyPurchase getByIdWithDic(Long id) {
        ShiroUser shiroUser = WebUtils.getLoggedUser();
        Long total = budgetService.sumBudgetByCompany(shiroUser.getCompanyId());
        Double purchaseTotal = this.entityDao.sumPurchaseByCompany(shiroUser.getCompanyId());
        Double paymentTotal = paymentService.sumPaymentByCompany(shiroUser.getCompanyId());
        BigDecimal b1 = new BigDecimal(total + "");
        BigDecimal b2 = new BigDecimal(purchaseTotal + "");
        BigDecimal b3 = new BigDecimal(paymentTotal + "");
        Double left = (b1.subtract(b2).subtract(b3)).doubleValue();
        ApplyPurchase applyPurchase = this.entityDao.getById(id);
        MdniDictionary mdniDictionary = mdniDictionaryService.getById(applyPurchase.getFirstTypeId());
        MdniDictionary mdniDictionary1 = mdniDictionaryService.getById(applyPurchase.getSecondTypeId());
        applyPurchase.setFirstName(mdniDictionary.getName());
        applyPurchase.setSecondName(mdniDictionary1.getName());
        applyPurchase.setTotalBudget(total.doubleValue());
        applyPurchase.setLeftBudget(left);
        return applyPurchase;
    }
    private int createWf(ApplyPurchase applyPurchase, ShiroUser loginUser) throws Exception{
        WfProcessParam param = new WfProcessParam();
        param.setFormId(applyPurchase.getId());
        param.setApplyUserId(loginUser.getId());
        param.setApplyTitle(applyPurchase.getApplyTitle());
        param.setApplyCode(applyPurchase.getApplyCode());
        param.setCompanyId(loginUser.getCompanyId());
        param.setDepartmentId(loginUser.getDepartmentId());
        param.setOrgId(loginUser.getOrgId());
        param.setApplyType(WfApplyTypeEnum.PURCHASE.name());
        param.setGenertaeWfProcessType(0L);
        return wfProcessService.createWf(param);
    }
    @Transactional
    public int submit(ApplyPurchase applyPurchase) {
        try {
            ShiroUser loginUser = WebUtils.getLoggedUser();
            applyPurchase.setStatus(ApplyStatus.APPROVALING);
            applyPurchase.setCreateTime(new Date());
            applyPurchase.setType(FirstTypes.getLabelForId(applyPurchase.getFirstTypeId()));
            int result = this.entityDao.update(applyPurchase);
            if (result > 0) {
                createWf(applyPurchase, loginUser);
            }
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
    public Map<Long, Double> getMonthItemMoney(Long companyId, Long typeId, String month) {
        return new HashMap<>();
    }
    public Map<Long, Double> getYearItemMoney(Long companyId, Long typeId, String month) {
        return new HashMap<>();
    }
    public Map<Long, Double> getEachMoneyByFirstType(Long companyId, String purchaseMonth, Integer type) {
		return null;
	}
}