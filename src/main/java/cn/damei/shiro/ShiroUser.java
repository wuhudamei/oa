package cn.damei.shiro;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.organization.MdniOrganization;
public class ShiroUser implements Serializable {
	private static final long serialVersionUID = -1473281454547002154L;
	private Long id;
	private List<Long> subjectPers;
	private String orgCode;
	private String username;
	private String name;
	private String position;
	private List<String> roles;
	private List<String> permissions;
	private Long orgId;
	private String orgName;
	private String orgFamilyCode;
	private Long departmentId;
	private String departmentName;
	private Long companyId;
	private String companyName;
	private String jobNum;
	private boolean departmentPrincipal;
	private boolean companyPrincipal;
	public ShiroUser(Long id, String username, String name, List<String> roles, List<String> permissions) {
		this.id = id;
		this.username = username;
		this.name = name;
		this.roles = roles;
		this.permissions = permissions;
	}
	public ShiroUser buildEmployeeOrg(EmployeeOrg empOrg) {
		Optional<EmployeeOrg> optional = Optional.ofNullable(empOrg);
		// 设置员工机构信息
		this.setOrgId(optional.map(EmployeeOrg::getOrg).map(MdniOrganization::getId).orElse(null));
		this.setOrgName(optional.map(EmployeeOrg::getOrg).map(MdniOrganization::getOrgName).orElse(null));
		this.setOrgFamilyCode(optional.map(EmployeeOrg::getOrg).map(MdniOrganization::getFamilyCode).orElse(null));
		// 设置员工部门信息
		this.setDepartmentId(optional.map(EmployeeOrg::getDepartment).map(MdniOrganization::getId).orElse(null));
		this.setDepartmentName(optional.map(EmployeeOrg::getDepartment).map(MdniOrganization::getOrgName).orElse(null));
		this.setDepartmentPrincipal(optional.map(EmployeeOrg::isDepartmentPrincipal).orElse(false));
		// 设置员工公司信息
		this.setCompanyId(optional.map(EmployeeOrg::getCompany).map(MdniOrganization::getId).orElse(null));
		this.setCompanyName(optional.map(EmployeeOrg::getCompany).map(MdniOrganization::getOrgName).orElse(null));
		this.setCompanyPrincipal(optional.map(EmployeeOrg::isCompanyPrincipal).orElse(false));
		return this;
	}
	@Override
	public String toString() {
		return username;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShiroUser other = (ShiroUser) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getRoles() {
		return roles;
	}
	public void setRoles(List<String> roles) {
		this.roles = roles;
	}
	public List<String> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<String> permissions) {
		this.permissions = permissions;
	}
	public Long getDepartmentId() {
		return departmentId;
	}
	public void setDepartmentId(Long departmentId) {
		this.departmentId = departmentId;
	}
	public Long getCompanyId() {
		return companyId;
	}
	public void setCompanyId(Long companyId) {
		this.companyId = companyId;
	}
	public boolean isDepartmentPrincipal() {
		return departmentPrincipal;
	}
	public void setDepartmentPrincipal(boolean departmentPrincipal) {
		this.departmentPrincipal = departmentPrincipal;
	}
	public boolean isCompanyPrincipal() {
		return companyPrincipal;
	}
	public void setCompanyPrincipal(boolean companyPrincipal) {
		this.companyPrincipal = companyPrincipal;
	}
	public String getDepartmentName() {
		return departmentName;
	}
	public void setDepartmentName(String departmentName) {
		this.departmentName = departmentName;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getPosition() {
		return position;
	}
	public void setPosition(String position) {
		this.position = position;
	}
	public Long getOrgId() {
		return orgId;
	}
	public void setOrgId(Long orgId) {
		this.orgId = orgId;
	}
	public String getOrgName() {
		return orgName;
	}
	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}
	public String getOrgFamilyCode() {
		return orgFamilyCode;
	}
	public void setOrgFamilyCode(String orgFamilyCode) {
		this.orgFamilyCode = orgFamilyCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public List<Long> getSubjectPers() {
		return subjectPers;
	}
	public void setSubjectPers(List<Long> subjectPers) {
		this.subjectPers = subjectPers;
	}
	public String getJobNum() {
		return jobNum;
	}
	public void setJobNum(String jobNum) {
		this.jobNum = jobNum;
	}
}