package cn.damei.entity.financail;
import java.util.Date;
import cn.damei.entity.IdEntity;
import cn.damei.entity.account.User;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.PaymentStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
public class FinancailPayment extends IdEntity {
	private static final long serialVersionUID = -4214979762522263545L;
	private Double totalPrice;
	private Employee applyUser;
	private MdniOrganization company;
	private WfApplyTypeEnum applyType;
	private String budgetMonth;
	private Date applyDate;
	private Integer invoiceTotal;
	private Long wfProcessId;
	private Long formId;
	private String wfProcessTittle;
	private String note;
	private Long paymentHandler;
	private Date paymentHandleDate;
	private Date createTime;
	private PaymentStatus status;
	public Double getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(Double totalPrice) {
		this.totalPrice = totalPrice;
	}
	public Employee getApplyUser() {
		return applyUser;
	}
	public void setApplyUser(Employee applyUser) {
		this.applyUser = applyUser;
	}
	public MdniOrganization getCompany() {
		return company;
	}
	public void setCompany(MdniOrganization company) {
		this.company = company;
	}
	public String getBudgetMonth() {
		return budgetMonth;
	}
	public void setBudgetMonth(String budgetMonth) {
		this.budgetMonth = budgetMonth;
	}
	public Date getApplyDate() {
		return applyDate;
	}
	public void setApplyDate(Date applyDate) {
		this.applyDate = applyDate;
	}
	public Integer getInvoiceTotal() {
		return invoiceTotal;
	}
	public void setInvoiceTotal(Integer invoiceTotal) {
		this.invoiceTotal = invoiceTotal;
	}
	public Long getWfProcessId() {
		return wfProcessId;
	}
	public void setWfProcessId(Long wfProcessId) {
		this.wfProcessId = wfProcessId;
	}
	public String getWfProcessTittle() {
		return wfProcessTittle;
	}
	public void setWfProcessTittle(String wfProcessTittle) {
		this.wfProcessTittle = wfProcessTittle;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public Long getPaymentHandler() {
		return paymentHandler;
	}
	public void setPaymentHandler(Long paymentHandler) {
		this.paymentHandler = paymentHandler;
	}
	public Date getPaymentHandleDate() {
		return paymentHandleDate;
	}
	public void setPaymentHandleDate(Date paymentHandleDate) {
		this.paymentHandleDate = paymentHandleDate;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public PaymentStatus getStatus() {
		return status;
	}
	public void setStatus(PaymentStatus status) {
		this.status = status;
	}
	public WfApplyTypeEnum getApplyType() {
		return applyType;
	}
	public void setApplyType(WfApplyTypeEnum applyType) {
		this.applyType = applyType;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
}
