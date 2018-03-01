package cn.damei.enumeration;
public enum Status {
    OPEN("启用"), LOCK("锁定"), DELETE("已删除");
    private String label;
    Status(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
