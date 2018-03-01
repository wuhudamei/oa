package cn.damei.enumeration;
public enum WfApprovalTypeEnum {
    JOINTLY_SIGN("会签"), ORDINARY("普通");
    private String label;
    WfApprovalTypeEnum(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
