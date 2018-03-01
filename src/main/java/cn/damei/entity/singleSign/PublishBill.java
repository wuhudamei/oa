package cn.damei.entity.singleSign;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class PublishBill extends IdEntity{
	private String punishCode;
	private String punishDepartment;
	private String punishMan;
	private String punishReason;
	private String punishMoney;
	private String signCode;
	private Integer status;
	private String punishDepartName;
	private String showOrgTree;
	private String createUser;
	private Date createTime;
	public String getShowOrgTree() {
		return showOrgTree;
	}
	public void setShowOrgTree(String showOrgTree) {
		this.showOrgTree = showOrgTree;
	}
	public String getPunishDepartName() {
		return punishDepartName;
	}
	public void setPunishDepartName(String punishDepartName) {
		this.punishDepartName = punishDepartName;
	}
	private String punishManName;
	public String getPunishManName() {
		return punishManName;
	}
	public void setPunishManName(String punishManName) {
		this.punishManName = punishManName;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public String getCreateUser() {
		return createUser;
	}
	public void setCreateUser(String createUser) {
		this.createUser = createUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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
}
