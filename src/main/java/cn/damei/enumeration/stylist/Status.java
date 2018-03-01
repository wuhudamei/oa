package cn.damei.enumeration.stylist;
public enum Status {
    FIRST_STAGE("一期"), SECOND_STAGE("二期"), THREE_STAGE("三期"), FOURTH_STAGE("四期");
    private String label;
    Status(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}