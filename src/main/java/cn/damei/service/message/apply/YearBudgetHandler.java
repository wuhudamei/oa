package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.budget.YearBudget;
import cn.damei.service.budget.YearBudgetService;
import cn.damei.service.message.MessageManagerService;
@Service
public class YearBudgetHandler extends BaseHandler {
    @Autowired
    private YearBudgetService yearBudgetService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        YearBudget budget = yearBudgetService.getById(sendParams.getFormId());
        if (budget == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Applicant(sendParams, budget.getApplyTitle());
    }
    @Override
    public HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        YearBudget budget = yearBudgetService.getById(sendParams.getFormId());
        if (budget == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Approver(sendParams, budget.getApplyTitle(), "", budget.getCreateTime());
    }
}
