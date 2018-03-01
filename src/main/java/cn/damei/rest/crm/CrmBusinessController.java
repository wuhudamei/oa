package cn.damei.rest.crm;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.rocoinfo.weixin.config.ParamManager;
import com.rocoinfo.weixin.util.HttpUtils;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.account.LoginService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/crm/crmBusiness")
public class CrmBusinessController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(CrmBusinessController.class);// 日志
	private static final String TEMPLATE_URL = "https://open.weixin.qq.com/connect/oauth2/authorize";
	private static final String RETURN_MESSAGE = "message";
	private static final String RETURN_MEG = "msg";
	private static final String RETURN_OK = "ok";
	private static final String RETURN_FAIL = "fail";
	@Autowired
	private MessageManagerService messageManagerService;
	@Autowired
	private EmployeeService employeeService;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private LoginService loginService;
	@RequestMapping(value = "checkLogin", method = RequestMethod.POST)
	public Object checkLogin(@RequestParam String mobile, @RequestParam String password) {
		String openId = getOpenId();
		String retMessage = HttpUtils.get(PropertyHolder.getOldCrmBaseUrl() + "/api/wechat/bindwx?username=" + mobile
				+ "&password=" + password + "&ts=" + openId);
		logger.error("old-crm return check user json = " + retMessage);
		Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(retMessage, String.class, Object.class);
		if (RETURN_OK.equals(jsonMap.get(RETURN_MESSAGE))) {
			String userInfo = JsonUtils.toJson(jsonMap.get("user"));
			Map<String, Object> userMap = JsonUtils.fromJsonAsMap(userInfo, String.class, Object.class);
			Employee employee = new Employee();
			employee.setName(userMap.get("real_name") == null ? "crmUser" : userMap.get("real_name").toString());
			employee.setJobNum(userMap.get("uid") == null ? "" : userMap.get("uid").toString());
			employee.setPassword(userMap.get("password") == null ? "" : userMap.get("password").toString());
			employee.setPosition("客服人员");
			employee.setMobile(userMap.get("phone_num") == null ? "" : userMap.get("phone_num").toString());
			employee.setCreateUser(-1L);
			int rowNum = employeeService.insert(employee);
			if (rowNum > 0) {
				// 微信用户和本地用户绑定
				WechatUser wechatUser = getWechatUserByOpenId(openId);
				WechatUser wu = WechatUser.fromId(wechatUser.getId());
				wu.setUserId(employee.getId());
				wu.setJobNum(employee.getJobNum());
				this.wechatUserService.update(wu);
				// 用户登录
				StatusDto res = this.loginService.login(userMap.get("uid").toString(), null, true);
				// 登录失败直接返回错误信息
				if (!res.isSuccess()) {
					return StatusDto.buildFailure("登录失败,请退出微信重新登录");
				} else {
					return StatusDto.buildSuccess();
				}
			}
		} else if (RETURN_FAIL.equals(jsonMap.get(RETURN_MESSAGE))) {
			return StatusDto.buildFailure(jsonMap.get(RETURN_MEG).toString());
		}
		return StatusDto.buildFailure();
	}
	@RequestMapping(value = "getAllTask", method = RequestMethod.GET)
	public Object getAllTask() {
		// ts:openId
		String url = PropertyHolder.getOldCrmBaseUrl() + "/api/wechat/tasklist?ts=" + getOpenId();
		String retMessage = HttpUtils.get(url);
		Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(retMessage, String.class, Object.class);
		if (jsonMap.get("code") != null && "1".equals(jsonMap.get("code").toString())) {
			return retMessage;
		}
		// 获取失败
		return StatusDto.buildFailure("获取列表失败");
	}
	@RequestMapping(value = "getTaskById", method = RequestMethod.GET)
	public Object getTaskById(@RequestParam String taskId, @RequestParam(required=false) String ts) {
		if (StringUtils.isBlank(ts)) {
			ts = getOpenId();
		}
		String url = PropertyHolder.getOldCrmBaseUrl() + "/api/wechat/taskdtl?taskId=" + taskId + "&ts=" + ts;
		String retMessage = HttpUtils.get(url);
		return retMessage;
	}
	@RequestMapping(value = "updateTaskStatusById", method = RequestMethod.GET)
	public Object updateTaskStatusById(String taskId) {
		String url = PropertyHolder.getOldCrmBaseUrl() + "/api/wechat/updrwzt?taskId=" + taskId + "&ts=" + getOpenId() + "&taskStatus=" + 0;
		String retMessage = HttpUtils.get(url);
		return retMessage;
	}
	@RequestMapping(value = "checkOpenId", method = RequestMethod.POST)
	public String checkOpenId(@RequestBody String param) {
		String retMessage = "";
		if (StringUtils.isNotBlank(param)) {
			Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(param, String.class, Object.class);
			if(jsonMap.get("openId") != null){
				WechatUser wechatUser = getWechatUserByOpenId(jsonMap.get("openId").toString());
				if (wechatUser == null) {
					retMessage = "{\"code\":0,\"message\":\"success\"}";
				} else {
					retMessage = "{\"code\":1,\"message\":\"Invalidate openId\"}";
				}
			}else{
				retMessage = "{\"code\":3,\"message\":\"openId is null\"}";
			}
		} else {
			retMessage = "{\"code\":2,\"message\":\"Parameter is null\"}";
		}
		return retMessage;
	}
	@RequestMapping(value = "sendWxTemplateMessage", method = RequestMethod.POST)
	public String sendWxTemplateMessage(@RequestBody String messageParam) {
		if (StringUtils.isNotBlank(messageParam)) {
			Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(messageParam, String.class, Object.class);
			if (jsonMap.get("customerName") == null || jsonMap.get("customerMobile") == null
					|| jsonMap.get("optDate") == null || jsonMap.get("ts") == null || jsonMap.get("taskId") == null) {
				return "{\"code\":0,\"message\":\"Parameter missing\"}";
			}
			String url = TEMPLATE_URL + "?appid=" + ParamManager.getAppid() + "&redirect_uri="
					+ PropertyHolder.getBaseurl() + "/wx/click/login?state=/oldCrm/detail?taskId="
					+ jsonMap.get("taskId").toString() + "&ts=" + jsonMap.get("ts").toString()
					+ "&response_type=code&scope=snsapi_base&state=state#wechat_redirect";
			messageManagerService.sendCrmTemplateMessage(url, jsonMap.get("customerName").toString(),
					jsonMap.get("customerMobile").toString(), jsonMap.get("optDate").toString(),
					jsonMap.get("ts").toString());
			return "{\"code\":1,\"message\":\"success\"}";
		} else {
			return "{\"code\":0,\"message\":\"Parameter is null\"}";
		}
	}
	private WechatUser getWechatUserByOpenId(String openId) {
		return this.wechatUserService.getByOpenid(openId);
	}
	private String getOpenId() {
		Session session = WebUtils.getSession();
		String openid = session.getAttribute("openid") == null ? StringUtils.EMPTY
				: (String) session.getAttribute("openid");
		return openid;
	}
}
