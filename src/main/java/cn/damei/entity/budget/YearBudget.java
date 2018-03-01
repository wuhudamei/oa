package cn.damei.entity.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.entity.account.User;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.FirstTypes;
import org.springframework.data.annotation.Transient;
import java.util.Date;
import java.util.List;
public class YearBudget extends IdEntity {
    private String applyCode;
    private String applyTitle;
    private MdniOrganization applyCompany;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+08:00")
    private Date applyTime;
    private Integer budgetYear;
    private ApplyStatus status;
    private String attachment;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createTime;
    private String reason;
    private User applyUser;
    private String currentApproverName;
    private Double totalMoney;
    @Transient
    private FirstTypes type;
    @Transient
    private List<YearBudgetDetail> yearBudgetDetailList;
    public String getApplyCode() {
        return applyCode;
    }
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode == null ? null : applyCode.trim();
    }
    public String getApplyTitle() {
        return applyTitle;
    }
    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle == null ? null : applyTitle.trim();
    }
    public MdniOrganization getApplyCompany() {
        return applyCompany;
    }
    public void setApplyCompany(MdniOrganization applyCompany) {
        this.applyCompany = applyCompany;
    }
    public Date getApplyTime() {
        return applyTime;
    }
    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }
    public Integer getBudgetYear() {
        return budgetYear;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public void setBudgetYear(Integer budgetYear) {
        this.budgetYear = budgetYear;
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
    public Double getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    public List<YearBudgetDetail> getYearBudgetDetailList() {
        return yearBudgetDetailList;
    }
    public void setYearBudgetDetailList(List<YearBudgetDetail> yearBudgetDetailList) {
        this.yearBudgetDetailList = yearBudgetDetailList;
    }
    public String getReason() {
        return reason;
    }
    public void setReason(String reason) {
        this.reason = reason;
    }
    public User getApplyUser() {
        return applyUser;
    }
    public void setApplyUser(User applyUser) {
        this.applyUser = applyUser;
    }
    public FirstTypes getType() {
        return type;
    }
    public void setType(FirstTypes type) {
        this.type = type;
    }
    public String getCurrentApproverName() {
        return currentApproverName;
    }
    public void setCurrentApproverName(String currentApproverName) {
        this.currentApproverName = currentApproverName;
    }
}