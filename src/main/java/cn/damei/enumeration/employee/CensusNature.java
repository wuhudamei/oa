package cn.damei.enumeration.employee;
public enum CensusNature {
    BJ_CITY("北京城镇"), BJ_COUNTRY("北京农村"), OTHER_CITY("外地城镇"), OTHER_COUNTRY("外地农村");
    private String label;
    CensusNature(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
