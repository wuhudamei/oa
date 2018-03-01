package cn.damei.entity.account;
import cn.damei.entity.IdEntity;
public class Permission extends IdEntity {
    private static final long serialVersionUID = 7124970537818894923L;
    private String name;
    private String module;
    private String permission;
    private Boolean checked;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getModule() {
        return module;
    }
    public void setModule(String module) {
        this.module = module;
    }
    public String getPermission() {
        return permission;
    }
    public void setPermission(String permission) {
        this.permission = permission;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
}
