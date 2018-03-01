package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.oa.ApplyPurchase;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.oa.ApplyPurchaseService;
@Service
public class PurchaseHandler extends BaseHandler {
    @Autowired
    private ApplyPurchaseService purchaseService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public BaseHandler.HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        ApplyPurchase purchase = purchaseService.getById(sendParams.getFormId());
        if (purchase == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Approver(sendParams, purchase.getApplyTitle(), purchase.getDescription(), purchase.getCreateTime());
    }
    public BaseHandler.HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        ApplyPurchase purchase = purchaseService.getById(sendParams.getFormId());
        if (purchase == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Applicant(sendParams, purchase.getApplyTitle());
    }
}
