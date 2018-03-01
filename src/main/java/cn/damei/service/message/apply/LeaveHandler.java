package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.vacation.Vacation;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.vacation.VacationService;
@Service
public class LeaveHandler extends BaseHandler {
    @Autowired
    private VacationService vacationService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public BaseHandler.HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        Vacation vacation = vacationService.getById(sendParams.getFormId());
        if (vacation == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Approver(sendParams, vacation.getApplyTitle(), vacation.getReason(), vacation.getCreateTime());
    }
    public BaseHandler.HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        Vacation vacation = vacationService.getById(sendParams.getFormId());
        if (vacation == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Applicant(sendParams, vacation.getApplyTitle());
    }
}
