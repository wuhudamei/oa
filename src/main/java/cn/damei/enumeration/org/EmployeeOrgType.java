package cn.damei.enumeration.org;
public enum EmployeeOrgType {
    DIRECTLY("直属部门"), PART_TIME("兼职部门");
    private String label;
    EmployeeOrgType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return this.label;
    }
}
