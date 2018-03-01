package cn.damei.entity.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.entity.account.User;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.ApplyStatus;
import org.springframework.data.annotation.Transient;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
public class Budget extends IdEntity implements Serializable {
    private static final long serialVersionUID = 7387951979326150141L;
    private String applyTitle;
    private String applyCode;
    private Long type;
    private MdniOrganization company;
    private String budgetDate;
    private Double totalMoney;
    private ApplyStatus status;
    private String attachment;
    private Integer isYear;
    private String remark;
    private User createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createDate;
    private Date updateDate;
    private Long updateUser;
    private String currentApproverName;
    @Transient
    private List<BudgetDetails> budgetDetails;
    public String getApplyTitle() {
        return applyTitle;
    }
    public String getCurrentApproverName() {
        return currentApproverName;
    }
    public void setCurrentApproverName(String currentApproverName) {
        this.currentApproverName = currentApproverName;
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
    public MdniOrganization getCompany() {
        return company;
    }
    public void setCompany(MdniOrganization company) {
        this.company = company;
    }
    public User getCreateUser() {
        return createUser;
    }
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public String getBudgetDate() {
        return budgetDate;
    }
    public void setBudgetDate(String budgetDate) {
        this.budgetDate = budgetDate;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    public ApplyStatus getStatus() {
        return status;
    }
    public void setStatus(ApplyStatus status) {
        this.status = status;
    }
    public Long getType() {
        return type;
    }
    public void setType(Long type) {
        this.type = type;
    }
    public Integer getIsYear() {
        return isYear;
    }
    public void setIsYear(Integer isYear) {
        this.isYear = isYear;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public Long getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public List<BudgetDetails> getBudgetDetails() {
        return budgetDetails;
    }
    public void setBudgetDetails(List<BudgetDetails> budgetDetails) {
        this.budgetDetails = budgetDetails;
    }
}