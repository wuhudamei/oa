package cn.damei.enumeration;
public enum PaymentStatus {
    GRANT("待授权"), TOREIMBURSED("待报销"), REIMBURSED("已报销");
    private String label;
    PaymentStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
