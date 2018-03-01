package cn.damei.enumeration.employee;
public enum MaritalStatus {
    MARRIED("已婚"), UNMARRIED("未婚");
    private String label;
    MaritalStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
