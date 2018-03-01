package cn.damei.enumeration;
public enum WfNatureEnum {
    APPROVAL("审批"), EXECUTE("执行");
    private String label;
    WfNatureEnum(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
