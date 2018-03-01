package cn.damei.shiro;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import com.google.common.collect.Lists;
import cn.damei.entity.account.User;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.shiro.token.CustomUsernamePasswordToken;
public class ShiroDbAdminRealm extends ShiroAbstractRealm {
    private EmployeeOrgService empOrgService;
    public static final String REAL_NAME = "shiroDbAdminRealm";
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
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        CustomUsernamePasswordToken token = (CustomUsernamePasswordToken) authcToken;
        User user = userService.getByUsername(token.getUsername());
        ShiroUser shiroUser = new ShiroUser(user.getId(), user.getUsername(), user.getName(), token.getRoles(), token.getPermission());
        shiroUser.setPosition(user.getPosition());
        shiroUser.setOrgCode(user.getOrgCode());
        EmployeeOrg empOrg = this.empOrgService.getDirectByEmpId(user.getId());
        shiroUser.buildEmployeeOrg(empOrg);
        return new SimpleAuthenticationInfo(shiroUser, null, getName());
    }
    public void setName(String realName) {
        super.setName(REAL_NAME);
    }
    public String getName() {
        return REAL_NAME;
    }
    public void setEmpOrgService(EmployeeOrgService empOrgService) {
        this.empOrgService = empOrgService;
    }
}
