package cn.damei.rest.wxTemplate;
import com.google.common.collect.Lists;
import cn.damei.common.BaseController;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.utils.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/wx/template")
public class WxTemplateApiController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(WxTemplateApiController.class);// 日志
	@Autowired
	private MessageManagerService messageManagerService;
	@Autowired
	private WechatUserService wechatUserService;
	@RequestMapping(value = "sendScheduleTemplateMessage", method = RequestMethod.POST)
	public void sendScheduleTemplateMessage(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			List<Map<String, String>> jsonList = JsonUtils.fromJson(messageParam, List.class);
			List<String> jobNumList = new ArrayList<String>();
			Map<String, String> param = new HashMap<String, String>();
			if (jsonList != null && jsonList.size() > 0) {
				jsonList.forEach(map -> {
					jobNumList.add(map.get("jobNo"));
				});
				if (jobNumList != null && jobNumList.size() > 0) {
					List<WechatUser> wechatUserList = getOpenIdByJobNum(jobNumList);
					if (wechatUserList != null && wechatUserList.size() > 0) {
						wechatUserList.forEach(wechatUser -> {
							param.put(wechatUser.getJobNum(), wechatUser.getOpenid());
						});
					}
					jsonList.forEach(jsonMap -> {
						messageManagerService.sendCrmScheduleTemplateMessage(jsonMap.get("url"),
								param.get(jsonMap.get("jobNo")), jsonMap.get("head"), jsonMap.get("keyword1"),
								jsonMap.get("keyword2"));
					});
				}
			}
		}
	}
	@RequestMapping(value = "sendTaskDistributeTemplateMessage", method = RequestMethod.POST)
	public void sendTaskDistributeTemplateMessage(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			Map<String, String> jsonMap = JsonUtils.fromJsonAsMap(messageParam, String.class, String.class);
			List<WechatUser> wechatUserList = getOpenIdByJobNum(Lists.newArrayList(jsonMap.get("jobNo")));
			if (wechatUserList != null && wechatUserList.size() > 0) {
				messageManagerService.sendCrmScheduleTemplateMessage(jsonMap.get("url"),
						wechatUserList.get(0).getOpenid(), jsonMap.get("head"), jsonMap.get("keyword1"),
						jsonMap.get("keyword2"));
			}
		}
	}
	@RequestMapping(value = "sendWorkOrderStageTemplate", method = RequestMethod.POST)
	public void sendWorkOrderStageTemplate(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			Map<String, String> jsonMap = JsonUtils.fromJsonAsMap(messageParam, String.class, String.class);
			List<WechatUser> wechatUserList = getOpenIdByJobNum(Lists.newArrayList(jsonMap.get("jobNo")));
			if (wechatUserList != null && wechatUserList.size() > 0) {
				messageManagerService.sendWorkOrderStageTemplate(jsonMap.get("url"),
						wechatUserList.get(0).getOpenid(), jsonMap.get("head"), jsonMap.get("keyword1"),
						jsonMap.get("keyword2"), jsonMap.get("keyword3"), jsonMap.get("remark"));
			}
		}
	}
	@RequestMapping(value = "sendApprovalTemplate", method = RequestMethod.POST)
	public void sendApprovalTemplate(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			Map<String, String> jsonMap = JsonUtils.fromJsonAsMap(messageParam, String.class, String.class);
			List<WechatUser> wechatUserList = getOpenIdByJobNum(Lists.newArrayList(jsonMap.get("jobNo")));
			if (wechatUserList != null && wechatUserList.size() > 0) {
				messageManagerService.sendApprovalTemplate(jsonMap.get("url"),
						wechatUserList.get(0).getOpenid(), jsonMap.get("head"), jsonMap.get("keyword1"),
						jsonMap.get("keyword2"), jsonMap.get("keyword3"), jsonMap.get("keyword4"));
			}
		}
	}
	private List<WechatUser> getOpenIdByJobNum(List<String> jobNums) {
		return this.wechatUserService.getByUserJobNum(jobNums);
	}
}
