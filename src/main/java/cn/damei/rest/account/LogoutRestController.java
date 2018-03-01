package cn.damei.rest.account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.service.account.LogoutService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.HttpUtil;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/logout")
public class LogoutRestController extends BaseController {
    @Autowired
    private LogoutService logoutService;
//    @RequestMapping(method = RequestMethod.GET)
//    public Object logout() {
//        logoutService.logout();
//        return StatusDto.buildSuccess();
//    }
    @RequestMapping(method = RequestMethod.GET)
    public Object logout(){
    	ShiroUser loggedUser = WebUtils.getLoggedUser();
    	if(loggedUser != null){
    		try {
				String logout = logoutUse(loggedUser);
				return StatusDto.buildSuccess(logout);
    		} catch (Exception e) {
    			e.printStackTrace();
    			return StatusDto.buildFailure("退出失败!" + e.getMessage());
    		}
    	}else{
    		return StatusDto.buildSuccess();
    	}
	}
    	public String logoutUse(ShiroUser loggedUser) throws Exception {
		String logoutUrl = PropertyHolder.getOauthCenterUrl() + Constants.OAUTH_LOGOUT_URL;
		try {
			String logout = HttpUtil.post(logoutUrl, PropertyHolder.getOauthCenterAppid(), PropertyHolder.getOauthCenterSecret(),
					loggedUser.getUsername());
			Subject subject = SecurityUtils.getSubject();
			if (subject.getPrincipal() != null) {
				subject.logout();
			}
			return logout;
		}catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
    }
}
