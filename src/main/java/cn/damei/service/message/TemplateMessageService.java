package cn.damei.service.message;
import com.rocoinfo.weixin.api.MessageApi;
import com.rocoinfo.weixin.model.ApiResult;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.dto.message.TemplateMessage.NameValue;
import cn.damei.utils.JsonUtils;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class TemplateMessageService {
	private static Logger logger = LoggerFactory.getLogger(TemplateMessageService.class);// 日志
	public boolean sendTemplateMessage(TemplateMessage templateMessage) {
		String json = JsonUtils.toJson(templateMessage);
		ApiResult result = MessageApi.sendTemplateMsg(json);
		String rawJson = result.getRawJson();
		String toUser = templateMessage.getToUser();
		logger.warn("TemplateMessageService -> sendTemplateMessage:\t" + rawJson + "\tresult.isSuccess():\t"
				+ result.isSuccess() + "\ttoUser:\t" + toUser);
		return result.isSuccess();
	}
}
