package cn.damei.shiro.token;
import java.util.List;
import org.apache.shiro.authc.UsernamePasswordToken;
public class CustomUsernamePasswordToken extends UsernamePasswordToken {
	private static final long serialVersionUID = 1L;
    private boolean ssoLogin;
	private Long id;
	private String username;
	private String name;
	private List<String> roles;
	private List<String> permission;
    public CustomUsernamePasswordToken(String username, String password,boolean ssoLogin) {
        super(username, password);
        this.ssoLogin = ssoLogin;
    }
	public CustomUsernamePasswordToken(Long id,String username, String name, List<String> roles, List<String> permission,
			boolean ssoLogin) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.roles = roles;
		this.permission = permission;
		this.ssoLogin = ssoLogin;
	}
    public boolean isSsoLogin() {
        return ssoLogin;
    }
    public void setSsoLogin(boolean ssoLogin) {
        this.ssoLogin = ssoLogin;
    }
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getPermission() {
		return permission;
	}
	public void setPermission(List<String> permission) {
		this.permission = permission;
	}
}
