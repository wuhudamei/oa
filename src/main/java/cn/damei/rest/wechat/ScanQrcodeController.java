package cn.damei.rest.wechat;
import com.corundumstudio.socketio.SocketIOClient;
import com.google.common.collect.Lists;
import com.rocoinfo.weixin.api.OAuthApi;
import com.rocoinfo.weixin.api.TagApi;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.User;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.enumeration.Status;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.service.account.LoginService;
import cn.damei.service.account.UserService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.utils.JsonUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.cache.SocketIOClientCache;
import cn.damei.utils.cache.StringCache;
import cn.damei.utils.des.DesUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
@RestController
@RequestMapping(value = "/wx/scan")
public class ScanQrcodeController extends BaseController {
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private LoginService loginService;
	@Autowired
	private UserService userService;
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static final String BIND_PAGE = "redirect:/wx/scan/bind/page";
	private static final String INDEX_PAGE = "redirect:/index";
	private static final String SUBSCRIBE_PAGE = "redirect:/wx/subscribe";
	@RequestMapping()
	public Object login(String code, String state, HttpServletRequest request) {
		if (StringUtils.isAnyBlank(code, state))
			return StatusDto.buildFailure("code不能为null");
		String uuid = state;
		// 根据code请求微信换取openid
		String openid = OAuthApi.getOpenid(code);
		// 查询本地系统中是否已经保存了此微信用户
		WechatUser wechatUser = this.getWechatUserByOpenid(openid);
		// 查询绑定关系,无绑定关系跳转到账号绑定的页面
		if (wechatUser.getUserId() == null) {
			// 如果用户未关注，先跳转到关注页面
			if (wechatUser.getSubscribe() == null || wechatUser.getSubscribe().equals(0)) {
				return new ModelAndView(SUBSCRIBE_PAGE);
			}
			// 在session中保存用户和uuid
			Session session = WebUtils.getSession();
			session.setAttribute(Constants.WECHAT_USER_SESSION_KEY, wechatUser);
			session.setAttribute("uuid", state);
			// 跳转到绑定页面
			return new ModelAndView(BIND_PAGE);
		}
		// 存在绑定关系，则查询绑定的用户是否存在，如果存在并且为在职状态且账号正常，则将用户openid传递给前台进行登录
		User user = this.userService.getById(wechatUser.getUserId());
		if (user == null || !Status.OPEN.equals(user.getAccountStatus())
				|| EmployeeStatus.DIMISSION.equals(user.getEmployeeStatus())) {
			return StatusDto.buildFailure("用户不存在或已离职");
		}
		SocketIOClient client;
		client = SocketIOClientCache.get(uuid);
		if (client == null)
			return StatusDto.buildFailure("扫码超时，请刷新页面重新扫码");
		// 推消息
		this.sendEvent2Client(client, wechatUser.getOpenid(), user.getUsername(), request);
		// 用户登录
		StatusDto res = this.loginService.login(user.getUsername(), null, true);
		// 登录失败直接返回错误信息
		if (!res.isSuccess())
			return res;
		return new ModelAndView(INDEX_PAGE);
	}
	@RequestMapping(value = "/bind", method = RequestMethod.POST)
	public Object bind(String username) {
		if (StringUtils.isAnyBlank(username)) {
			return StatusDto.buildFailure("参数不能为空");
		}
		User user = this.userService.getByUsernameOrMobile(username);
		if (user == null) {
			logger.warn("scan bind:##################### can't find employee!");
			return StatusDto.buildFailure("系统查询不到此用户");
		}
		try {
			this.validateSessionData();
		} catch (IllegalAccessException e) {
			logger.error("scan:##################### params: uuid:" + e.getMessage());
			return StatusDto.buildFailure("信息超时，请刷新页面重新扫码登录");
		}
		// 用户登录
		StatusDto res = this.loginService.login(user.getUsername(), null, true);
		// 登录失败直接返回错误信息
		if (!res.isSuccess())
			return res;
		WechatUser wechatUser = (WechatUser) WebUtils.getSession().getAttribute(Constants.WECHAT_USER_SESSION_KEY);
		this.bindWechatUserAndLocal(wechatUser.getId(), user.getId(), user.getUsername());
		try {
			TagApi.batchTag(Constants.INSIDE_USER_GROUP_ID, Lists.newArrayList(wechatUser.getOpenid()));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return StatusDto.buildSuccess();
	}
	private void bindWechatUserAndLocal(Long wechatUserId, Long localUserId, String userName) {
		WechatUser wu = WechatUser.fromId(wechatUserId);
		wu.setType(Constants.WECHAT_USER_TYPE_INSIDE);
		wu.setUserId(localUserId);
		wu.setJobNum(userName);
		this.wechatUserService.update(wu);
	}
	private void validateSessionData() throws IllegalAccessException {
		Session session = WebUtils.getSession();
		String uuid = String.valueOf(session.getAttribute("uuid"));
		WechatUser wechatUser = (WechatUser) session.getAttribute(Constants.WECHAT_USER_SESSION_KEY);
		SocketIOClient client = SocketIOClientCache.get(uuid);
		if (uuid == null || wechatUser == null || client == null) {
			logger.error("##################### scan:##################### params: uuid:" + uuid + ",wechatUser: "
					+ wechatUser + ", client:" + client);
			throw new IllegalAccessException();
		}
	}
	@RequestMapping(value = "/password", method = RequestMethod.POST)
	public Object modifyPassword(String newPassword, String confirmPassword, HttpServletRequest request) {
		if (StringUtils.isAnyBlank(newPassword, confirmPassword) || !newPassword.equals(confirmPassword)) {
			return StatusDto.buildFailure("密码为空或两次输入密码不一致");
		}
		try {
			this.validateSessionData();
		} catch (IllegalAccessException e) {
			return StatusDto.buildFailure("信息超时，请刷新页面重新扫码登录!");
		}
		StatusDto res = this.userService.modifyPwd(WebUtils.getLoggedUserId(), newPassword);
		if (res.isSuccess()) {
			Session session = WebUtils.getSession();
			String uuid = String.valueOf(session.getAttribute("uuid"));
			WechatUser wechatUser = (WechatUser) session.getAttribute(Constants.WECHAT_USER_SESSION_KEY);
			SocketIOClient client = SocketIOClientCache.get(uuid);
			this.sendEvent2Client(client, wechatUser.getOpenid(), WebUtils.getLoggedUser().getUsername(), request);
		}
		return res;
	}
	@RequestMapping(value = "/login")
	public Object ssoLogin(String secret) throws UnsupportedEncodingException {
		if (StringUtils.isNotBlank(secret)) {
			// 将secret解析成map
			Map<String, String> params = JsonUtils.fromJsonAsMap(DesUtil.decode(secret), String.class, String.class);
			if (org.apache.commons.collections.MapUtils.isNotEmpty(params)) {
				String openid = params.get("openid");
				String username = params.get("username");
				// openid username不能为空，并且StringCache中必须有值
				if (StringUtils.isAnyBlank(openid, username, StringCache.get(openid))) {
					return StatusDto.buildFailure("秘钥无效，请返回重新扫码登录");
				}
				// 用户登录
				StatusDto res = loginService.login(username, null, true);
				if (res.isSuccess()) {
					// 移除StringCache中的缓存
					StringCache.remove(openid);
					return new ModelAndView("redirect:/index");
				}
				return StatusDto.buildFailure("系统异常，请使用用户名和密码登录");
			}
		}
		return StatusDto.buildFailure("秘钥无效，请返回重新扫码登录");
	}
	private void sendEvent2Client(SocketIOClient client, String openid, String username, HttpServletRequest request) {
		// 为了保证登录的安全性，后台通过StringCache缓存一个openid和username的映射用来进行登录时的验证
		StringCache.put(openid, username);
		// 构建pc端用来单点登录的url
		String url = this.buildPcLoginUrl(PropertyHolder.getBaseurl(), openid, username);
		// 向客户端发送单点登录的消息
		client.sendEvent("req", JsonUtils.toJson(MapUtils.of("code", "1", "url", url)));
		client.disconnect();
	}
	private String buildPcLoginUrl(String baseUrl, String openid, String username) {
		String url = baseUrl + "/wx/scan/login";
		String params = DesUtil.encode(JsonUtils.toJson(MapUtils.of("openid", openid, "username", username)));
		try {
			// 需要进行urlencode，否则参数传递过程中+号会被转成空字符串导致DesUtil.decode(s)失败
			url += "?secret=" + URLEncoder.encode(params, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return url;
	}
	private WechatUser getWechatUserByOpenid(String openid) {
		// 先查询本地系统中是否已经有微信用户
		WechatUser user = this.wechatUserService.getByOpenid(openid);
		// 如果没有保存此微信用户,那么将改用户插入到数据（只保存openid）
		if (user == null) {
			user = new WechatUser();
			user.setOpenid(openid);
			this.wechatUserService.insert(user);
		}
		return user;
	}
}
