package cn.damei.entity.oa;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.entity.employee.Employee;
import cn.damei.enumeration.WfNatureEnum;
public class WfProcess extends IdEntity {
	private static final long serialVersionUID = 1L;
	private String nodeId;
	private String nodeType;
	private WfNatureEnum wfNature;
	private Long isSign;
	private String status;
	private String type;
	private Employee approverEmployee;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date createTime;
	private Date readTime;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
	private Date approveTime;
	private String approveResult;
	private String superNodeId;
	private Long formId;
	private String applyCode;
	private String applyTitle;
	private Long applyUserId;
	private String applyUserName;
	private String remark;
	private Long targetUserId;
	private String currentApprove;
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Employee getApproverEmployee() {
		return approverEmployee;
	}
	public void setApproverEmployee(Employee approverEmployee) {
		this.approverEmployee = approverEmployee;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getReadTime() {
		return readTime;
	}
	public void setReadTime(Date readTime) {
		this.readTime = readTime;
	}
	public Date getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(Date approveTime) {
		this.approveTime = approveTime;
	}
	public String getApproveResult() {
		return approveResult;
	}
	public void setApproveResult(String approveResult) {
		this.approveResult = approveResult;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSuperNodeId() {
		return superNodeId;
	}
	public void setSuperNodeId(String superNodeId) {
		this.superNodeId = superNodeId;
	}
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public String getNodeId() {
		return nodeId;
	}
	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public Long getIsSign() {
		return isSign;
	}
	public void setIsSign(Long isSign) {
		this.isSign = isSign;
	}
	public String getApplyCode() {
		return applyCode;
	}
	public void setApplyCode(String applyCode) {
		this.applyCode = applyCode;
	}
	public String getApplyTitle() {
		return applyTitle;
	}
	public void setApplyTitle(String applyTitle) {
		this.applyTitle = applyTitle;
	}
	public Long getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getApplyUserName() {
		return applyUserName;
	}
	public void setApplyUserName(String applyUserName) {
		this.applyUserName = applyUserName;
	}
	public WfNatureEnum getWfNature() {
		return wfNature;
	}
	public void setWfNature(WfNatureEnum wfNature) {
		this.wfNature = wfNature;
	}
	public Long getTargetUserId() {
		return targetUserId;
	}
	public void setTargetUserId(Long targetUserId) {
		this.targetUserId = targetUserId;
	}
	public String getCurrentApprove() {
		return currentApprove;
	}
	public void setCurrentApprove(String currentApprove) {
		this.currentApprove = currentApprove;
	}
}
