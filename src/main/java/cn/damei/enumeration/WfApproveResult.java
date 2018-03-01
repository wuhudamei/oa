package cn.damei.enumeration;
public enum WfApproveResult {
    AGREE("同意"), REFUSE("拒绝"), TURN("转派"), ADDBEFORE("之前添加"), ADDAFTER("之后添加");
    private String label;
    WfApproveResult(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
