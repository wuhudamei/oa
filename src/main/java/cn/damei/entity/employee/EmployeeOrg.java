package cn.damei.entity.employee;
import cn.damei.entity.IdEntity;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.enumeration.org.EmployeeOrgType;
public class EmployeeOrg extends IdEntity {
    private static final long serialVersionUID = 1L;
    private Employee employee;
    private MdniOrganization department;
    private MdniOrganization company;
    private MdniOrganization org;
    private EmployeeOrgType type;
    private boolean departmentPrincipal;
    private boolean companyPrincipal;
    public Employee getEmployee() {
        return employee;
    }
    public EmployeeOrg setEmployee(Employee employee) {
        this.employee = employee;
        return this;
    }
    public MdniOrganization getDepartment() {
        return department;
    }
    public EmployeeOrg setDepartment(MdniOrganization department) {
        this.department = department;
        return this;
    }
    public MdniOrganization getCompany() {
        return company;
    }
    public EmployeeOrg setCompany(MdniOrganization company) {
        this.company = company;
        return this;
    }
    public MdniOrganization getOrg() {
        return org;
    }
    public EmployeeOrg setOrg(MdniOrganization org) {
        this.org = org;
        return this;
    }
    public EmployeeOrgType getType() {
        return type;
    }
    public EmployeeOrg setType(EmployeeOrgType type) {
        this.type = type;
        return this;
    }
    public boolean isDepartmentPrincipal() {
        return departmentPrincipal;
    }
    public EmployeeOrg setDepartmentPrincipal(boolean departmentPrincipal) {
        this.departmentPrincipal = departmentPrincipal;
        return this;
    }
    public boolean isCompanyPrincipal() {
        return companyPrincipal;
    }
    public EmployeeOrg setCompanyPrincipal(boolean companyPrincipal) {
        this.companyPrincipal = companyPrincipal;
        return this;
    }
}