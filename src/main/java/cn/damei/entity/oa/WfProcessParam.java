package cn.damei.entity.oa;
public class WfProcessParam {
	private Long formId;
	private Long applyUserId;
	private String applyTitle;
	private String applyCode;
	@Deprecated
	private Long departmentId;
	@Deprecated
	private Long companyId;
	private Long orgId;
	private Integer overBudget;
	private Long processTypeId;
	private Long subjectId;
	private String applyType;
	private Long genertaeWfProcessType;
    private Double applyAmount = Double.valueOf(0);
    private Long applyDayNum = Long.valueOf(0);
	public Long getFormId() {
		return formId;
	}
	public void setFormId(Long formId) {
		this.formId = formId;
	}
	public Long getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(Long applyUserId) {
		this.applyUserId = applyUserId;
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
	@Deprecated
	public Long getDepartmentId() {
		return departmentId;
	}
	@Deprecated
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	@Deprecated
	public Long getCompanyId() {
		return companyId;
	}
	@Deprecated
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public Long getProcessTypeId() {
		return processTypeId;
	}
	public void setProcessTypeId(Long processTypeId) {
		this.processTypeId = processTypeId;
	}
	public Long getGenertaeWfProcessType() {
		return genertaeWfProcessType;
	}
	public void setGenertaeWfProcessType(Long genertaeWfProcessType) {
		this.genertaeWfProcessType = genertaeWfProcessType;
	}
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public Double getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(Double applyAmount) {
		this.applyAmount = applyAmount;
	}
	public Long getApplyDayNum() {
		return applyDayNum;
	}
	public void setApplyDayNum(Long applyDayNum) {
		this.applyDayNum = applyDayNum;
	}
	public Integer getOverBudget() {
		return overBudget;
	}
	public void setOverBudget(Integer overBudget) {
		this.overBudget = overBudget;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public Long getSubjectId() {
		return subjectId;
	}
	public void setSubjectId(Long subjectId) {
		this.subjectId = subjectId;
	}
}
