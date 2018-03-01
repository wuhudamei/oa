package cn.damei.entity.process;
import org.apache.ibatis.type.Alias;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfApprovalTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import java.io.Serializable;
@Alias(value = "appPermission")
public class ProcessEntity extends IdEntity implements Serializable {
	private static final long serialVersionUID = 6107942344749094011L;
	private String nodeTitle;
	private WfApplyTypeEnum wfType;
	private WfNatureEnum wfNature;
	private WfApprovalTypeEnum approvalType;
	private String approver;
	private String approverName;
	private Double approvalAmount;
	private Long approvalDayNum;
	private String applyOrg;
	private String applyOrgName;
	private Integer seq;
	private Long pid;
	private Integer status;
	private String ccUser;
	private String ccUserName;
	private String allPath;
	private Boolean checked;
	public String getNodeTitle() {
		return nodeTitle;
	}
	public void setNodeTitle(String nodeTitle) {
		this.nodeTitle = nodeTitle;
	}
	public WfApplyTypeEnum getWfType() {
		return wfType;
	}
	public void setWfType(WfApplyTypeEnum wfType) {
		this.wfType = wfType;
	}
	public WfNatureEnum getWfNature() {
		return wfNature;
	}
	public void setWfNature(WfNatureEnum wfNature) {
		this.wfNature = wfNature;
	}
	public WfApprovalTypeEnum getApprovalType() {
		return approvalType;
	}
	public void setApprovalType(WfApprovalTypeEnum approvalType) {
		this.approvalType = approvalType;
	}
	public String getApprover() {
		return approver;
	}
	public void setApprover(String approver) {
		this.approver = approver;
	}
	public String getApplyOrg() {
		return applyOrg;
	}
	public void setApplyOrg(String applyOrg) {
		this.applyOrg = applyOrg;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public Long getPid() {
		return pid;
	}
	public void setPid(Long pid) {
		this.pid = pid;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Boolean getChecked() {
		return checked;
	}
	public void setChecked(Boolean checked) {
		this.checked = checked;
	}
	public String getApproverName() {
		return approverName;
	}
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	public String getApplyOrgName() {
		return applyOrgName;
	}
	public void setApplyOrgName(String applyOrgName) {
		this.applyOrgName = applyOrgName;
	}
	public Double getApprovalAmount() {
		return approvalAmount;
	}
	public void setApprovalAmount(Double approvalAmount) {
		this.approvalAmount = approvalAmount;
	}
	public Long getApprovalDayNum() {
		return approvalDayNum;
	}
	public void setApprovalDayNum(Long approvalDayNum) {
		this.approvalDayNum = approvalDayNum;
	}
	public String getCcUser() {
		return ccUser;
	}
	public void setCcUser(String ccUser) {
		this.ccUser = ccUser;
	}
	public String getCcUserName() {
		return ccUserName;
	}
	public void setCcUserName(String ccUserName) {
		this.ccUserName = ccUserName;
	}
	public String getAllPath() {
		return allPath;
	}
	public void setAllPath(String allPath) {
		this.allPath = allPath;
	}
}