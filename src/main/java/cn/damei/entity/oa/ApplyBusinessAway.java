package cn.damei.entity.oa;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.ApplyStatus;
import java.math.BigDecimal;
import java.util.Date;
public class ApplyBusinessAway extends IdEntity {
    private Long applyUserId;
    private String applyUserName;
    private String applyTitle;
    private String applyCode;
    private String setOutAddress;
    private String address;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date beginTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date endTime;
    private Integer daysNum;
    private BigDecimal estimatedCost;
    private String reason;
    private Integer createUser;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private ApplyStatus status;
    private String currentApproverName;
    private String ccUser;
    private String ccUserStatus;
    public String getCcUserStatus() {
        return ccUserStatus;
    }
    public void setCcUserStatus(String ccUserStatus) {
        this.ccUserStatus = ccUserStatus;
    }
    public String getCcUser() {
        return ccUser;
    }
    public void setCcUser(String ccUser) {
        this.ccUser = ccUser;
    }
    public Long getApplyUserId() {
        return applyUserId;
    }
    public void setApplyUserId(Long applyUserId) {
        this.applyUserId = applyUserId;
    }
    public String getApplyUserName() {
        return applyUserName;
    }
    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }
    public String getApplyTitle() {
        return applyTitle;
    }
    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }
    public String getApplyCode() {
        return applyCode;
    }
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }
    public Date getBeginTime() {
        return beginTime;
    }
    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }
    public Date getEndTime() {
        return endTime;
    }
    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }
    public Integer getDaysNum() {
        return daysNum;
    }
    public void setDaysNum(Integer daysNum) {
        this.daysNum = daysNum;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason == null ? null : reason.trim();
    }
    public Integer getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public ApplyStatus getStatus() {
        return status;
    }
    public void setStatus(ApplyStatus status) {
        this.status = status;
    }
    public String getCurrentApproverName() {
        return currentApproverName;
    }
    public void setCurrentApproverName(String currentApproverName) {
        this.currentApproverName = currentApproverName;
    }
	public String getSetOutAddress() {
		return setOutAddress;
	}
	public void setSetOutAddress(String setOutAddress) {
		this.setOutAddress = setOutAddress;
	}
	public BigDecimal getEstimatedCost() {
		return estimatedCost;
	}
	public void setEstimatedCost(BigDecimal estimatedCost) {
		this.estimatedCost = estimatedCost;
	}
}