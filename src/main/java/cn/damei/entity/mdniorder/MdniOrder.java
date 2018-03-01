package cn.damei.entity.mdniorder;
import cn.damei.entity.IdEntity;
public class MdniOrder extends IdEntity{
	private static final long serialVersionUID = 1L;
	private String orderId;
	private String orderNo;
	private String customerId;
	private String customerName;
	private String mobile;
	private String address;
	private String contractStartTime;
	private String contractCompleteTime;
	private String actualStartTime;
	private String actualCompletionTime;
	private String styListName;
	private String styListMobile;
	private String superVisorName;
	private String superVisoMobile;
	private String contractor;
	private String contact;
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getStyListName() {
		return styListName;
	}
	public void setStyListName(String styListName) {
		this.styListName = styListName;
	}
	public String getStyListMobile() {
		return styListMobile;
	}
	public void setStyListMobile(String styListMobile) {
		this.styListMobile = styListMobile;
	}
	public String getSuperVisorName() {
		return superVisorName;
	}
	public void setSuperVisorName(String superVisorName) {
		this.superVisorName = superVisorName;
	}
	public String getSuperVisoMobile() {
		return superVisoMobile;
	}
	public void setSuperVisoMobile(String superVisoMobile) {
		this.superVisoMobile = superVisoMobile;
	}
	public String getContractor() {
		return contractor;
	}
	public void setContractor(String contractor) {
		this.contractor = contractor;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getContractStartTime() {
		return contractStartTime;
	}
	public void setContractStartTime(String contractStartTime) {
		this.contractStartTime = contractStartTime;
	}
	public String getContractCompleteTime() {
		return contractCompleteTime;
	}
	public void setContractCompleteTime(String contractCompleteTime) {
		this.contractCompleteTime = contractCompleteTime;
	}
	public String getActualStartTime() {
		return actualStartTime;
	}
	public void setActualStartTime(String actualStartTime) {
		this.actualStartTime = actualStartTime;
	}
	public String getActualCompletionTime() {
		return actualCompletionTime;
	}
	public void setActualCompletionTime(String actualCompletionTime) {
		this.actualCompletionTime = actualCompletionTime;
	}
}
