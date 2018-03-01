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
public class Payment extends IdEntity implements Serializable {
    private static final long serialVersionUID = -7353174250571582542L;
    private String applyTitle;
    private String applyCode;
    private Long type;
    private Long costItem;
    private String costItemName;
    private MdniOrganization company;
    private String paymentDate;
    private Double totalMoney;
    private Integer invoiceNum;
    private String reason;
    private Long signatureId;
    private String signatureTitle;
    private Integer surpassBudget;
    private ApplyStatus status;
    private String attachment;
    private User createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date createDate;
    private Date updateDate;
    private Long updateUser;
    private Long wfId;
    private String currentApproverName;
    private Long paymentSignatureId; 
    private String deptName; 
    @Transient
    private List<PaymentDetails> paymentDetails;
    private String ccUser;
    public String getSignatureTitle() {
        return signatureTitle;
    }
    public void setSignatureTitle(String signatureTitle) {
        this.signatureTitle = signatureTitle;
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
    public MdniOrganization getCompany() {
        return company;
    }
    public void setCompany(MdniOrganization company) {
        this.company = company;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public Long getType() {
        return type;
    }
    public Payment setType(Long type) {
        this.type = type;
        return this;
    }
    public Integer getInvoiceNum() {
        return invoiceNum;
    }
    public Payment setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }
    public String getReason() {
        return reason;
    }
    public Payment setReason(String reason) {
        this.reason = reason;
        return this;
    }
    public Long getCostItem() {
        return costItem;
    }
    public void setCostItem(Long costItem) {
        this.costItem = costItem;
    }
    public Long getSignatureId() {
        return signatureId;
    }
    public void setSignatureId(Long signatureId) {
        this.signatureId = signatureId;
    }
    public Integer getSurpassBudget() {
        return surpassBudget;
    }
    public void setSurpassBudget(Integer surpassBudget) {
        this.surpassBudget = surpassBudget;
    }
    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
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
    public User getCreateUser() {
        return createUser;
    }
    public void setCreateUser(User createUser) {
        this.createUser = createUser;
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
    public String getCurrentApproverName() {
        return currentApproverName;
    }
    public void setCurrentApproverName(String currentApproverName) {
        this.currentApproverName = currentApproverName;
    }
    public List<PaymentDetails> getPaymentDetails() {
        return paymentDetails;
    }
    public void setPaymentDetails(List<PaymentDetails> paymentDetails) {
        this.paymentDetails = paymentDetails;
    }
    public String getCostItemName() {
        return costItemName;
    }
    public void setCostItemName(String costItemName) {
        this.costItemName = costItemName;
    }
	public Long getPaymentSignatureId() {
		return paymentSignatureId;
	}
	public void setPaymentSignatureId(Long paymentSignatureId) {
		this.paymentSignatureId = paymentSignatureId;
	}
	public String getCcUser() {
		return ccUser;
	}
	public void setCcUser(String ccUser) {
		this.ccUser = ccUser;
	}
	public Long getWfId() {
		return wfId;
	}
	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
}