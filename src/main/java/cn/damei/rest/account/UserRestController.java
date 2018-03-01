package cn.damei.rest.account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.dto.StatusDto;
import cn.damei.service.account.RoleService;
import cn.damei.service.account.UserService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
@RestController
@RequestMapping(value = "/api/users")
public class UserRestController extends BaseController {
    private static final String JOB_NUM = "jobNum";
    private static final String REDIRECT_URL = "redirectUrl";
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private  LogoutRestController logoutRestController;
    @RequestMapping(value = "/modifyPwd")
    public Object updatePwd(HttpServletRequest request) {
        String hostName = getHostName(request);
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser != null) {
            String callBack = hostName + "/api/users/logout";
            String updateUrl =PropertyHolder.getOauthCenterUrl() + Constants.OAUTH_PASSWORD_URL + "?" + JOB_NUM + "=" + loggedUser.getUsername() + "&" +
                    REDIRECT_URL + "=" + callBack;
            return new ModelAndView("redirect:" + updateUrl);
        }else{
            return StatusDto.buildFailure("用户未登录");
        }
    }
    @RequestMapping(method = RequestMethod.GET,value ="/logout" )
    public Object logout(HttpServletRequest request){
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        String hostName = getHostName(request);
        if(loggedUser != null){
            try {
                 logoutRestController.logoutUse(loggedUser);
                return new ModelAndView("redirect:"+hostName + "/logout");
            } catch (Exception e) {
                e.printStackTrace();
                return StatusDto.buildFailure("退出失败!" + e.getMessage());
            }
        }else{
            return StatusDto.buildSuccess();
        }
    }
    @RequestMapping(value = "/role/{id}")
    public Object getUserRoles(@PathVariable Long id) {
        return StatusDto.buildSuccess(this.roleService.getUserRoles(id));
    }
    @RequestMapping(value = "/userrole")
    public Object setUserRole(@RequestParam("userId") Long userId, @RequestParam("roles[]") List<Long> roles) {
        if (userId == null)
            return StatusDto.buildFailure("用户id为空！");
        if (roles == null || roles.size() == 0)
            return StatusDto.buildFailure("角色为空！");
        return roleService.insertUserRoles(userId, roles);
    }
    @RequestMapping(value = "/getLogger/Permission")
    public StatusDto getUserPermissions() {
        ShiroUser shiroUser = WebUtils.getLoggedUser();
        return StatusDto.buildSuccess(shiroUser);
    }
    private String getHostName(HttpServletRequest request){
        StringBuffer url = request.getRequestURL();
        String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append(request.getServletContext().getContextPath()).toString();
        return tempContextUrl;
    }
}
