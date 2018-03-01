package cn.damei.dto.employee;
import java.io.Serializable;
import cn.damei.enumeration.org.EmployeeOrgType;
public class EmpOrgDto implements Serializable {
    private Long id;
    private Long pid;
    private String name;
    private String type;
    private EmployeeOrgType orgType;
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getPid() {
        return pid;
    }
    public void setPid(Long pid) {
        this.pid = pid;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public EmployeeOrgType getOrgType() {
        return orgType;
    }
    public void setOrgType(EmployeeOrgType orgType) {
        this.orgType = orgType;
    }
}
