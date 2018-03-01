package cn.damei.entity.stylist;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class Stylist extends IdEntity implements Serializable {
    private static final long serialVersionUID = 2161370766893734676L;
    private Long userId;
    private String jobNo;
    private String name;
    private String mobile;
    private String minister;
    private String ministerName;
    private String ministerMobile;
    private Type type;
    private Long department;
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJobNo() {
        return jobNo;
    }
    public void setJobNo(String jobNo) {
        this.jobNo = jobNo;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getMinister() {
        return minister;
    }
    public void setMinister(String minister) {
        this.minister = minister;
    }
    public String getMinisterName() {
        return ministerName;
    }
    public void setMinisterName(String ministerName) {
        this.ministerName = ministerName;
    }
    public String getMinisterMobile() {
        return ministerMobile;
    }
    public void setMinisterMobile(String ministerMobile) {
        this.ministerMobile = ministerMobile;
    }
    public Type getType() {
        return type;
    }
    public void setType(Type type) {
        this.type = type;
    }
    public Long getDepartment() {
        return department;
    }
    public void setDepartment(Long department) {
        this.department = department;
    }
    public enum Type {
        STYLIST("设计师"), MINISTER("部长");
        private String label;
        Type(String label) {
            this.label = label;
        }
        public String getLabel() {
            return label;
        }
    }
}