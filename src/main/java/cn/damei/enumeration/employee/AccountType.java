package cn.damei.enumeration.employee;
public enum AccountType {
    EMPLOYEE("内部员工"), VIRTUAL("虚拟账号"), OUTSUPPLIER("外部供货商");
    private String label;
    AccountType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
