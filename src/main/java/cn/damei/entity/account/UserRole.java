package cn.damei.entity.account;
import cn.damei.entity.IdEntity;
public class UserRole extends IdEntity {
    private static final long serialVersionUID = -5493732869243219341L;
    private Long userId;
    private Long roleId;
    public UserRole() {
    }
    public UserRole(Long userId, Long roleId) {
        this.userId = userId;
        this.roleId = roleId;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
}
