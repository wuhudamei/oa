package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.oa.ApplyBusinessAway;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.oa.ApplyBusinessAwayService;
@Service
public class BusinessHandler extends BaseHandler {
    @Autowired
    private ApplyBusinessAwayService businessAwayService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public BaseHandler.HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        ApplyBusinessAway applyBusinessAway = businessAwayService.getById(sendParams.getFormId());
        if (applyBusinessAway == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Approver(sendParams, applyBusinessAway.getApplyTitle(), applyBusinessAway.getReason(), applyBusinessAway.getCreateTime());
    }
    public BaseHandler.HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        ApplyBusinessAway applyBusinessAway = businessAwayService.getById(sendParams.getFormId());
        if (applyBusinessAway == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Applicant(sendParams, applyBusinessAway.getApplyTitle());
    }
}
