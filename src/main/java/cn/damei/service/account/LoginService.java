package cn.damei.service.account;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.dto.StatusDto;
import cn.damei.shiro.ShiroUser;
import cn.damei.shiro.token.CustomUsernamePasswordToken;
import cn.damei.utils.WebUtils;
@Service
public class LoginService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private LogoutService logoutService;
    public StatusDto login(String username, String password, boolean ssoLogin) {
        try {
            login(new CustomUsernamePasswordToken(username, password, ssoLogin));
            ShiroUser loginUser = WebUtils.getLoggedUser();
            return StatusDto.buildSuccess("登录成功", loginUser);
        } catch (AuthenticationException e) {
            return StatusDto.buildFailure(getErrorResponseEntity(e));
        }
    }
    public void login(AuthenticationToken token) throws AuthenticationException {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            logoutService.logout();
        }
        subject.login(token);
    }
    private String getErrorResponseEntity(AuthenticationException e) {
        final String loginFail = "[账户/密码]不正确或账户被锁定";
        if (e instanceof IncorrectCredentialsException) {
            return "账户或密码不正确";
        }
        if (e instanceof ExpiredCredentialsException) {
            return "密码已过期";
        }
        if (e instanceof CredentialsException) {
            return "密码验证失败";
        }
        if (e instanceof UnknownAccountException) {
            return "账户或密码不正确";
        }
        if (e instanceof LockedAccountException) {
            return "账户已被锁定";
        }
        if (e instanceof DisabledAccountException) {
            return "账户已被禁用";
        }
        if (e instanceof ExcessiveAttemptsException) {
            return "尝试次数太多";
        }
        if (e instanceof AccountException) {
            String error = e.getMessage();
            if (StringUtils.isBlank(error)) {
                error = loginFail;
            }
            return error;
        }
        logger.debug(loginFail, e);
        return loginFail;
    }
}
