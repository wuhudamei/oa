package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.budget.Payment;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.commonapply.ApplyCommon;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.commonapply.ApplyCommonService;
import cn.damei.service.message.MessageManagerService;
@Service
public class ExpanseHandler extends BaseHandler {
	@Autowired
	private PaymentService paymentService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
		Payment payment = paymentService.getById(sendParams.getFormId());
		if (null == payment) {
			logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
			return null;
		}
		return commonlySend2Applicant(sendParams, payment.getApplyTitle());
	}
	@Override
	public HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
		// 1.查询流程详细信息
		Payment payment = paymentService.getById(sendParams.getFormId());
		if (null == payment) {
			logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
			return null;
		}
		return commonlySend2Approver(sendParams, payment.getApplyTitle(), payment.getApplyTitle(),
				payment.getCreateDate());
	}
}
