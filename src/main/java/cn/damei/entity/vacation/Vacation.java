package cn.damei.entity.vacation;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.entity.employee.Employee;
import cn.damei.enumeration.ApplyStatus;
import org.springframework.data.annotation.Transient;
import java.util.Date;
public class Vacation extends IdEntity {
    private String applyTitle;
    private String applyCode;
    private Employee employee;
    @Transient
    private String approver;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date startTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "GMT+8")
    private Date endTime;
    private Double days;
    private String reason;
    private String imgUrl;
    private ApplyStatus status;
    private Integer applyType;
    private String applyTypeCn;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    public String getApplyTitle() { return applyTitle;}
    public void setApplyTitle(String applyTitle) {this.applyTitle = applyTitle;}
    public String getApplyCode() { return applyCode;}
    public void setApplyCode(String applyCode) {this.applyCode = applyCode;}
    public Employee getEmployee() {
        return employee;
    }
    public void setEmployee(Employee employee) {
        this.employee = employee;
    }
    public Date getStartTime() {
        return startTime;
    }
    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Double getDays() {
        return days;
    }
    public void setDays(Double days) {
        this.days = days;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public ApplyStatus getStatus() {
        return status;
    }
    public void setStatus(ApplyStatus status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getApprover() {
        return approver;
    }
    public void setApprover(String approver) {
        this.approver = approver;
    }
    public Integer getApplyType() {
        return applyType;
    }
    public void setApplyType(Integer applyType) {
        this.applyType = applyType;
    }
    public String getApplyTypeCn() {
        return applyTypeCn;
    }
    public void setApplyTypeCn(String applyTypeCn) {
        this.applyTypeCn = applyTypeCn;
    }
}