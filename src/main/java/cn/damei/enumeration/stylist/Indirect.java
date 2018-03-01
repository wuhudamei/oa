package cn.damei.enumeration.stylist;
public enum Indirect {
    TAXES("税金"), MANAGER("管理费"), DESIGN("设计费"), REMOTE("远程费"), OTHERS("其他费用"), PRIVILEGE("优惠费");
    private String label;
    Indirect(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
