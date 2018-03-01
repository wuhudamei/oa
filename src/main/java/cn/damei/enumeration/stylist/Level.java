package cn.damei.enumeration.stylist;
public enum Level {
    A("A"), B("B"), C("C"), D("D");
    private String label;
    Level(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
