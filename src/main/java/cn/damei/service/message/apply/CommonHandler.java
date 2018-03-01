package cn.damei.service.message.apply;
import java.util.concurrent.TimeUnit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.commonapply.ApplyCommon;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.commonapply.ApplyCommonService;
import cn.damei.service.message.MessageManagerService;
@Service
public class CommonHandler extends BaseHandler {
	@Autowired
	private ApplyCommonService applyCommonService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	@Override
	public HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
		ApplyCommon applyComm = applyCommonService.getById(sendParams.getFormId());
		if (applyComm == null) {
			for (int i = 0; i < 10; i++) {
				logger.warn(LOGGER_ENTITY_IS_NULL + "\trCount:\t" + (i + 1), sendParams.getFormId(),
						sendParams.getWfType().getLabel());
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				applyComm = applyCommonService.getById(sendParams.getFormId());
				if (null != applyComm) {
					break;
				}
			}
			return null;
		}
		return commonlySend2Applicant(sendParams, applyComm.getTitle());
	}
	@Override
	public HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
		// 1.查询流程详细信息
		ApplyCommon applyComm = applyCommonService.getById(sendParams.getFormId());
		if (applyComm == null) {
			for (int i = 0; i < 10; i++) {
				logger.warn(LOGGER_ENTITY_IS_NULL + "\trCount:\t" + (i + 1), sendParams.getFormId(),
						sendParams.getWfType().getLabel());
				try {
					TimeUnit.SECONDS.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				applyComm = applyCommonService.getById(sendParams.getFormId());
				if (null != applyComm) {
					break;
				}
			}
		}
		return commonlySend2Approver(sendParams, applyComm.getTitle(), applyComm.getTitle(), applyComm.getCreateTime());
	}
}
