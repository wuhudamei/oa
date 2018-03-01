package cn.damei.rest.wechat;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.session.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import com.rocoinfo.weixin.api.OAuthApi;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.User;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.entity.wechat.tag.TagEmployee;
import cn.damei.service.account.LoginService;
import cn.damei.service.account.UserService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.service.wechat.tag.TagEmployeeService;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/wx/click")
public class ClickMenuController extends BaseController {
	private static Logger logger = LoggerFactory.getLogger(ClickMenuController.class);//日志
    @Autowired
    private UserService userService;
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private LoginService loginService;
    @Autowired
    private TagEmployeeService tagEmployeeService;
    private static final String BIND_PAGE = "redirect:/wx/click/bind/page";
    private static final String CRM_BIND_PAGE = "redirect:/oldCrm/bind";
    private static final String INDEX_PAGE = "redirect:/index";
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public Object login(HttpServletRequest request) {
        String redirectUrl = Optional.ofNullable(request.getParameter("state")).orElse(INDEX_PAGE);
        if (!redirectUrl.startsWith("redirect:")) {
            redirectUrl = "redirect:" + redirectUrl;
        }
        String code = request.getParameter("code");
        if (StringUtils.isBlank(code)) {
            return StatusDto.buildFailure("code为null,请检查微信公众号配置");
        }
        String openid = OAuthApi.getOpenid(code);
        if (StringUtils.isBlank(openid)) {
            return StatusDto.buildFailure("获取openid失败,请检查微信公众号配置");
        }
        WechatUser wechatUser = this.wechatUserService.getByOpenid(openid);
        if (wechatUser == null) {
            wechatUser = new WechatUser();
            wechatUser.setOpenid(openid);
            this.wechatUserService.insert(wechatUser);
            WebUtils.getSession().setAttribute("redirectUrl", redirectUrl);
            WebUtils.getSession().setAttribute("openid", openid);
            return new ModelAndView(BIND_PAGE);
        }
        if (WebUtils.isLogin(request)) {
            return new ModelAndView(redirectUrl);
        }
        if (wechatUser.getUserId() == null) {
            WebUtils.getSession().setAttribute("redirectUrl", redirectUrl);
            WebUtils.getSession().setAttribute("openid", openid);
        	if(redirectUrl.indexOf("oldCrm") == -1){
                return new ModelAndView(BIND_PAGE);
        	}else{
                return new ModelAndView(CRM_BIND_PAGE);
        	}
        }
        if(redirectUrl.indexOf("oldCrm") == -1){
        	tagEmployeeService.update(new TagEmployee(null,new Employee(wechatUser.getUserId()),openid));
        }
        User user = this.userService.getById(wechatUser.getUserId());
        if (user != null) {
            StatusDto res = this.loginService.login(user.getUsername(), null, true);
            if ("1".equals(res.getCode())) {
            	WebUtils.getSession().setAttribute("openid", openid);
                return new ModelAndView(redirectUrl);
            }
        }
        WebUtils.getSession().setAttribute("redirectUrl", redirectUrl);
        return new ModelAndView(BIND_PAGE);
    }
    @RequestMapping(value = "/bind")
    public Object bind(String username) {
        if (StringUtils.isBlank(username))
            return StatusDto.buildFailure("员工号/手机号不能为空");
        User user = this.userService.getByUsernameOrMobile(username);
        if (user == null){
        	return StatusDto.buildFailure("系统无此用户");
        }
        Session session = WebUtils.getSession();
        String openid = session.getAttribute("openid") == null ? StringUtils.EMPTY : (String) session.getAttribute("openid");
        if (StringUtils.isEmpty(openid))
            return StatusDto.buildFailure("请求超时，请返回公众号重新点击菜单登录。");
        tagEmployeeService.update(new TagEmployee(null,new Employee(user.getId()),openid));
        WechatUser wechatUser = this.wechatUserService.getByOpenid(openid);
        this.bindWechatUserAndLocal(wechatUser.getId(), user.getId(),user.getUsername());
        StatusDto res = this.loginService.login(user.getUsername(), null, true);
        if (!res.isSuccess()){
        	return res;
        }
        return StatusDto.buildSuccess();
    }
    private void bindWechatUserAndLocal(Long wechatUserId, Long localUserId,String userName) {
        WechatUser wu = WechatUser.fromId(wechatUserId);
        wu.setType(Constants.WECHAT_USER_TYPE_INSIDE);
        wu.setUserId(localUserId);
        wu.setJobNum(userName);
        this.wechatUserService.update(wu);
    }
    @RequestMapping(value = "/password")
    public Object modifyPassword(String newPassword, String confirmPassword) {
        if (StringUtils.isAnyBlank(newPassword, confirmPassword) || !newPassword.equals(confirmPassword)) {
            return StatusDto.buildFailure("密码为空或两次输入密码不一致");
        }
        StatusDto res = this.userService.modifyPwd(WebUtils.getLoggedUserId(), newPassword);
        Session session = WebUtils.getSession();
        String redirectUrl = Optional.ofNullable(session.getAttribute("redirectUrl")).map(String::valueOf).orElse(INDEX_PAGE);
        if (res.isSuccess()) {
            res.setData(redirectUrl);
        }
        return res;
    }
}
