package cn.damei.entity.account;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.data.annotation.Transient;
import cn.damei.entity.IdEntity;
import java.util.List;
public class Role extends IdEntity {
    private static final long serialVersionUID = 2510859557988256648L;
    public static final String ADMIN = "admin";
    public static final String USER = "user";
    private Long id;
    private String name;
    private String description;
    @Transient
    private Boolean checked;
    private List<Permission> permission;
    public Role() {
    }
    public Role(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public List<Permission> getPermission() {
        return permission;
    }
    public void setPermission(List<Permission> permission) {
        this.permission = permission;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
