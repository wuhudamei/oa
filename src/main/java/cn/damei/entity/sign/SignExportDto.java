package cn.damei.entity.sign;
import java.util.Map;
public class SignExportDto{
    private Long empId;
    private String name;
    private String orgCode;
    private Double shouldWorkDays;
    private Double practicalWorkDays;
    private Double overTimeWorkDays;
    private Map<Long,Sign> map;
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public Double getShouldWorkDays() {
        return shouldWorkDays;
    }
    public void setShouldWorkDays(Double shouldWorkDays) {
        this.shouldWorkDays = shouldWorkDays;
    }
    public Double getPracticalWorkDays() {
        return practicalWorkDays;
    }
    public void setPracticalWorkDays(Double practicalWorkDays) {
        this.practicalWorkDays = practicalWorkDays;
    }
    public Double getOverTimeWorkDays() {
        return overTimeWorkDays;
    }
    public void setOverTimeWorkDays(Double overTimeWorkDays) {
        this.overTimeWorkDays = overTimeWorkDays;
    }
    public Map<Long, Sign> getMap() {
        return map;
    }
    public void setMap(Map<Long, Sign> map) {
        this.map = map;
    }
}
