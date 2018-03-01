package cn.damei.enumeration.employee;
public enum EnglishLevel {
    BASE("基本"), GENERAL("一般"), PROFICIENCY("熟练"), MASTER("精通");
    private String label;
    EnglishLevel(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
