package cn.damei.enumeration;
public enum SignType {
    NORMAL("正常"), BELATE("迟到"), LEAVEEARLY("早退"),ABSENTEEISM("旷工"),BELATEANDLEAVEEARLY("迟到并早退");
    private String label;
    SignType(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
