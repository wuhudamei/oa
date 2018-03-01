package cn.damei.entity.singleSign;
import java.util.Date;
import java.util.List;
import cn.damei.entity.IdEntity;
public class SignBill extends IdEntity{
	private String subCompany;
	private Integer status;
	private String orderNum;
	private String customerName;
	private String customerPhone;
	private String orderAddress;
	private String procedureDescribe;
	private String managerView;
	private String signCode;
	private Date createTime;
	private String createUser;
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	private List<PublishBill> publishBillList;
	public List<PublishBill> getPublishBillList() {
		return publishBillList;
	}
	public void setPublishBillList(List<PublishBill> publishBillList) {
		this.publishBillList = publishBillList;
	}
	public String getSubCompany() {
		return subCompany;
	}
	public void setSubCompany(String subCompany) {
		this.subCompany = subCompany;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public String getOrderNum() {
		return orderNum;
	}
	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getCustomerPhone() {
		return customerPhone;
	}
	public void setCustomerPhone(String customerPhone) {
		this.customerPhone = customerPhone;
	}
	public String getOrderAddress() {
		return orderAddress;
	}
	public void setOrderAddress(String orderAddress) {
		this.orderAddress = orderAddress;
	}
	public String getProcedureDescribe() {
		return procedureDescribe;
	}
	public void setProcedureDescribe(String procedureDescribe) {
		this.procedureDescribe = procedureDescribe;
	}
	public String getManagerView() {
		return managerView;
	}
	public void setManagerView(String managerView) {
		this.managerView = managerView;
	}
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
	}
}
