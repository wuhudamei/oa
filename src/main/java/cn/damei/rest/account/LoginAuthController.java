package cn.damei.rest.account;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.entity.employee.Employee;
import cn.damei.service.employee.EmployeeService;
import cn.damei.shiro.token.CustomUsernamePasswordToken;
import cn.damei.utils.HttpUtil;
import cn.damei.utils.JsonUtils;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/oauthCallBack")
public class LoginAuthController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(LoginAuthController.class);// 日志
	@Autowired
	private EmployeeService employeeService;
	private static final String CODE = "code";
	private static final String CODE_1 = "1";
	private static final String MESSAGE = "message";
	private static final String ROLES = "roles";
	private static final String SCOPE = "scope";
	private static final String DATA = "data";
	private static final String DATA_ACCESS_TOKEN = "access_token";
	private static final String DATA_USERINFO = "userinfo";
	private static final String DATA_USERINFO_NAME = "name";
	private static final String DATA_USERINFO_MOBILE = "mobile";
	private static final String DATA_USERINFO_EMAIL = "email";
	private static final String DATA_USERINFO_ORGCODE  = "orgCode";	//员工号(登录账号)
	private static final String INDEX_PAGE = "redirect:/index";
	@RequestMapping(method = RequestMethod.GET)
	public Object oauthCallBack(@RequestParam String code, @RequestParam String state) {
		String requiredUrl = INDEX_PAGE;
		StringBuilder getTokenUrl = new StringBuilder();
		getTokenUrl.append(PropertyHolder.getOauthCenterUrl()).append(Constants.OAUTH_CENTER_TOKEN_URL);
		String retParam = "";
		try {
			retParam = HttpUtil.post(getTokenUrl.toString(), PropertyHolder.getOauthCenterAppid(),
					PropertyHolder.getOauthCenterSecret(), code, true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (StringUtils.isNotBlank(retParam)) {
			Map<String, Object> jsonMap = JsonUtils.fromJsonAsMap(retParam, String.class, Object.class);
			if (jsonMap.get(CODE) != null && CODE_1.equals(jsonMap.get(CODE).toString())) {
				if (jsonMap.get(DATA) != null) {
					Map<String, Object> userMap = (Map<String, Object>) jsonMap.get(DATA);
					Map<String, Object> userinfoMap = (Map<String, Object>) userMap.get(DATA_USERINFO);
					List<String> rolesList = (List<String>) userMap.get(ROLES);
					List<String> scopeList = (List<String>) userMap.get(SCOPE);
					Employee employee = employeeService
							.getByOrgCode(userinfoMap.get(DATA_USERINFO_ORGCODE).toString());
					if(employee !=null){
						login(new CustomUsernamePasswordToken(employee.getId(),userinfoMap.get(DATA_USERINFO_ORGCODE).toString(),
								userinfoMap.get(DATA_USERINFO_NAME).toString(), rolesList, scopeList, true));
					}else{
						logger.error("员工信息不存在!");
					}
				}
			} else {
				logger.error("oauth return code is 0 , error message 【" + jsonMap.get(MESSAGE).toString() + "】");
			}
		}
		if(StringUtils.isNotBlank(state) && !"state".equals(state)){
			requiredUrl = "redirect:" + state;
		}
		return new ModelAndView(requiredUrl);
	}
	public void login(AuthenticationToken token) throws AuthenticationException {
		Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
        	subject.logout();
        }
		subject.login(token);
	}
}
