package cn.damei.entity.vacation;
import java.util.Date;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.ApplyStatus;
public class VacationBusiness extends IdEntity {
	private static final long serialVersionUID = 1L;
    private String applyTitle;
    private String applyCode;
    private String type;
    @Transient
    private String approver;
    private String reason;
    private Double days;
    private ApplyStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    public String getApplyTitle() { return applyTitle;}
    public void setApplyTitle(String applyTitle) {this.applyTitle = applyTitle;}
    public String getApplyCode() { return applyCode;}
    public void setApplyCode(String applyCode) {this.applyCode = applyCode;}
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Double getDays() {
		return days;
	}
	public void setDays(Double days) {
		this.days = days;
	}
}