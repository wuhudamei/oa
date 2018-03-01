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
public class Signature extends IdEntity implements Serializable {
	private static final long serialVersionUID = -7353174250571582542L;
	private String applyTitle;
	private String applyCode;
	private MdniOrganization company;
	private String companyName;// 公司名称
	private Long type;
	private Long costItem;
	private String costItemName;
	private String signatureDate;
	private Double totalMoney;
	private String reason;
	private ApplyStatus status;
	private String attachment;
	private Integer surpassBudget;
	private User createUser;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createDate;
	private Date updateDate;
	private Long updateUser;
	private String currentApproverName;
	private Long wfId;
	public Long getWfId() {
		return wfId;
	}
	public void setWfId(Long wfId) {
		this.wfId = wfId;
	}
	@Transient
	private List<SignatureDetails> signatureDetails;
	private String ccUser;
	public Long getCostItem() {
		return costItem;
	}
	public void setCostItem(Long costItem) {
		this.costItem = costItem;
	}
	public String getSignatureDate() {
		return signatureDate;
	}
	public void setSignatureDate(String signatureDate) {
		this.signatureDate = signatureDate;
	}
	public List<SignatureDetails> getSignatureDetails() {
		return signatureDetails;
	}
	public void setSignatureDetails(List<SignatureDetails> signatureDetails) {
		this.signatureDetails = signatureDetails;
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
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
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
	public Long getType() {
		return type;
	}
	public void setType(Long type) {
		this.type = type;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
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
	public Integer getSurpassBudget() {
		return surpassBudget;
	}
	public void setSurpassBudget(Integer surpassBudget) {
		this.surpassBudget = surpassBudget;
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
	public String getCostItemName() {
		return costItemName;
	}
	public void setCostItemName(String costItemName) {
		this.costItemName = costItemName;
	}
	public String getCcUser() {
		return ccUser;
	}
	public void setCcUser(String ccUser) {
		this.ccUser = ccUser;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
}