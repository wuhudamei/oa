package cn.damei.enumeration.employee;
public enum EmployeeType {
    PRACTICE("实习学生"), SOLDIER("在役军人"), DISABLED("残疾人"), RETIRE("退休"), FOREIGN("外籍及港澳台人员"),
    OVERAGE("超龄人员"), INSURANCE_IN_ORIGIN("保险关系在原单位"), INSURANCE_IN_TALENT("保险关系在人才或职介"), OTHER("其他");
    private String label;
    EmployeeType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
