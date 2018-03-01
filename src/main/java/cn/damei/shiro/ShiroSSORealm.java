package cn.damei.shiro;
import com.google.common.collect.Lists;
import cn.damei.entity.account.User;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.shiro.token.CustomUsernamePasswordToken;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
public class ShiroSSORealm extends ShiroAbstractRealm {
    private EmployeeOrgService empOrgService;
    public static final String REAM_NAME = "shiroSSORealm";
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        super.setCredentialsMatcher((token, info) -> {
            if (token instanceof CustomUsernamePasswordToken) {
                CustomUsernamePasswordToken customToken = (CustomUsernamePasswordToken) token;
                if (StringUtils.isNotBlank(customToken.getUsername()) && customToken.isSsoLogin() && info != null) {
                    return true;
                }
            }
            return false;
        });
    }
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authenticationToken;
        User user = userService.getAllInfoByUsername(token.getUsername());
        userService.validateUser(user);
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user.getName(), token.getRoles(), token.getPermission());
        shiroUser.setPosition(user.getPosition());
        EmployeeOrg empOrg = this.empOrgService.getDirectByEmpId(user.getId());
        shiroUser.buildEmployeeOrg(empOrg);
        return new SimpleAuthenticationInfo(shiroUser, null, getName());
    }
    @Override
    public boolean supports(AuthenticationToken token) {
        if (token instanceof CustomUsernamePasswordToken) {
            return ((CustomUsernamePasswordToken) token).isSsoLogin();
        }
        return false;
    }
    public void setName(String name){
        super.setName(REAM_NAME);
    }
    public String getName(){
        return REAM_NAME;
    }
    public void setEmpOrgService(EmployeeOrgService empOrgService) {
        this.empOrgService = empOrgService;
    }
}
