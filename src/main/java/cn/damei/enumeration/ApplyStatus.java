package cn.damei.enumeration;
public enum ApplyStatus {
    APPROVALING("审核中"), ADOPT("通过"), REFUSE("拒绝"), DRAFT("草稿"), REIMBURSED("已报销"),  RESET("撤回");
    ApplyStatus(String label) {
        this.label = label;
    }
    private String label;
    public String getLabel() {
        return label;
    }
}
