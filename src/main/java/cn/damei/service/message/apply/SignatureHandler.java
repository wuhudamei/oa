package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.budget.Signature;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.message.MessageManagerService;
@Service
public class SignatureHandler extends BaseHandler{
    @Autowired
    private SignatureService signatureService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Override
    public HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        Signature signature = signatureService.getById(sendParams.getFormId());
        if (signature == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Applicant(sendParams, signature.getApplyTitle());
    }
    @Override
    public HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        Signature signature = signatureService.getById(sendParams.getFormId());
        if (signature == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        return commonlySend2Approver(sendParams, signature.getApplyTitle(), signature.getApplyTitle(), signature.getCreateDate());
    }
}
