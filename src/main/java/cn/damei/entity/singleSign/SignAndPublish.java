package cn.damei.entity.singleSign;
import java.text.SimpleDateFormat;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class SignAndPublish extends IdEntity{
	private Integer pid;
	private String punishCode;
	private String punishDepartment;
	private String punishMan;
	private String punishView;
	private String punishReason;
	private String punishMoney;
	private String signCode;
	private String subCompany;
	private String statusName;
	private Integer status;
	private String orderNum;
	private String customerName;
	private String customerPhone;
	private String orderAddress;
	private String procedureDescribe;
	private String managerView;
	private Date createTime;
	public Integer getPid() {
		return pid;
	}
	public void setPid(Integer pid) {
		this.pid = pid;
	}
	public String getPunishCode() {
		return punishCode;
	}
	public void setPunishCode(String punishCode) {
		this.punishCode = punishCode;
	}
	public String getPunishDepartment() {
		return punishDepartment;
	}
	public void setPunishDepartment(String punishDepartment) {
		this.punishDepartment = punishDepartment;
	}
	public String getPunishMan() {
		return punishMan;
	}
	public void setPunishMan(String punishMan) {
		this.punishMan = punishMan;
	}
	public String getPunishView() {
		return punishView;
	}
	public void setPunishView(String punishView) {
		this.punishView = punishView;
	}
	public String getPunishReason() {
		return punishReason;
	}
	public void setPunishReason(String punishReason) {
		this.punishReason = punishReason;
	}
	public String getPunishMoney() {
		return punishMoney;
	}
	public void setPunishMoney(String punishMoney) {
		this.punishMoney = punishMoney;
	}
	public String getSignCode() {
		return signCode;
	}
	public void setSignCode(String signCode) {
		this.signCode = signCode;
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
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
}
