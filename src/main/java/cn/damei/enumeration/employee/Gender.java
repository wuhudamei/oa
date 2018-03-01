package cn.damei.enumeration.employee;
public enum Gender {
    MALE("男"), FEMALE("女");
    private String label;
    Gender(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
