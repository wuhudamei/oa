package cn.damei.entity.account;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonIgnore;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.Status;
import cn.damei.enumeration.employee.AccountType;
import cn.damei.enumeration.employee.EmployeeStatus;
public class User extends IdEntity {
    public User() {
    }
    public User(Long id) {
        this.id = id;
    }
    private String orgCode;
    private String jobNum;
    private String name;
    private String username;
    private String position;
    @JsonIgnore
    private String password;
    @Transient
    @JsonIgnore
    private String plainPassword;
    @JsonIgnore
    private String salt;
    private Status accountStatus;
    private EmployeeStatus employeeStatus;
    private AccountType accountType;
    @Transient
    @JsonIgnore
    private List<Role> roles;
    @Transient
    private String headImg;
    public List<String> getRoleNameList() {
        if (roles == null || roles.size() == 0) {
            return Collections.emptyList();
        }
        ArrayList<String> roleNameList = new ArrayList<>();
        for (Role role : roles) {
            roleNameList.add(role.getName());
        }
        return roleNameList;
    }
    public LinkedHashSet<String> getPermissions() {
        LinkedHashSet<String> permissions = new LinkedHashSet<>();
        if (roles != null) {
            for (Role role : roles) {
                List<Permission> permission = role.getPermission();
                if (permission != null) {
                    for (Permission perm : permission) {
                        permissions.add(perm.getPermission());
                    }
                }
            }
        }
        return permissions;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getPlainPassword() {
        return plainPassword;
    }
    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Status getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }
    public List<Role> getRoles() {
        return roles;
    }
    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }
    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public String getPosition() {
        return position;
    }
    public User setPosition(String position) {
        this.position = position;
        return this;
    }
    public String getHeadImg() {
        return headImg;
    }
    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
}
