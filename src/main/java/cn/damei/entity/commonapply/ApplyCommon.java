package cn.damei.entity.commonapply;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
public class ApplyCommon extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String applyPerson;
	private String serialNumber;
	private String title;
	private String content;
	private String photos;
	private String accessories;
	private String ccPerson;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date applyTime;
	private Integer submitUser;
	private String submitUserName;
	private Date createTime;
	private String applyPersonInfo;
	private String ccPersonInfo;
	private String applyPersonName;
	private String ccPersonName;
	private String subOrgName;
	private String applyTimeHMS;
	private String waitApplyPerson;
	private String orgName;
	private String status;
	public String getApplyPerson() {
		return applyPerson;
	}
	public void setApplyPerson(String applyPerson) {
		this.applyPerson = applyPerson;
	}
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getPhotos() {
		return photos;
	}
	public void setPhotos(String photos) {
		this.photos = photos;
	}
	public String getAccessories() {
		return accessories;
	}
	public void setAccessories(String accessories) {
		this.accessories = accessories;
	}
	public String getCcPerson() {
		return ccPerson;
	}
	public void setCcPerson(String ccPerson) {
		this.ccPerson = ccPerson;
	}
	public Date getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(Date applyTime) {
		this.applyTime = applyTime;
	}
	public Integer getSubmitUser() {
		return submitUser;
	}
	public void setSubmitUser(Integer submitUser) {
		this.submitUser = submitUser;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getApplyPersonInfo() {
		return applyPersonInfo;
	}
	public void setApplyPersonInfo(String applyPersonInfo) {
		this.applyPersonInfo = applyPersonInfo;
	}
	public String getCcPersonInfo() {
		return ccPersonInfo;
	}
	public void setCcPersonInfo(String ccPersonInfo) {
		this.ccPersonInfo = ccPersonInfo;
	}
	public String getApplyPersonName() {
		return applyPersonName;
	}
	public void setApplyPersonName(String applyPersonName) {
		this.applyPersonName = applyPersonName;
	}
	public String getCcPersonName() {
		return ccPersonName;
	}
	public void setCcPersonName(String ccPersonName) {
		this.ccPersonName = ccPersonName;
	}
	public String getSubmitUserName() {
		return submitUserName;
	}
	public void setSubmitUserName(String submitUserName) {
		this.submitUserName = submitUserName;
	}
	public String getSubOrgName() {
		return subOrgName;
	}
	public void setSubOrgName(String subOrgName) {
		this.subOrgName = subOrgName;
	}
	public String getWaitApplyPerson() {
		return waitApplyPerson;
	}
	public void setWaitApplyPerson(String waitApplyPerson) {
		this.waitApplyPerson = waitApplyPerson;
	}
	public String getApplyTimeHMS() {
		return applyTimeHMS;
	}
	public void setApplyTimeHMS(String applyTimeHMS) {
		this.applyTimeHMS = applyTimeHMS;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
}
