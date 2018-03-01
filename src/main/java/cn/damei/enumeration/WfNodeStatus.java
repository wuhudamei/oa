package cn.damei.enumeration;
public enum WfNodeStatus {
    INIT("未开始"), PENDING("待审批"), COMPLETE("已审批"), INVALIDATE("无效"),  RESET("撤回"),  ADD("添加"), TURN("转派");
    private String label;
    WfNodeStatus(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
