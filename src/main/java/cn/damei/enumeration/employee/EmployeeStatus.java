package cn.damei.enumeration.employee;
public enum EmployeeStatus {
    ON_JOB("在职"), DIMISSION("离职");
    EmployeeStatus(String label) {
        this.label = label;
    }
    private String label;
    public String getLabel() {
        return label;
    }
}
