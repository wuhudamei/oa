package cn.damei.enumeration;
public enum FirstTypes {
    MATTERS(1L, "人事类"), MARKETING(2L, "营销类"), ADMINISTRATOR(3L, "行政类"), FINANCE(4L, "财务类"), CUSTOMER(5L,
            "客管类"), OTHERS(6L, "其他类");
    FirstTypes(Long id, String label) {
        this.id = id;
        this.label = label;
    }
    private Long id;
    private String label;
    public Long getId() {
        return id;
    }
    public String getLabel() {
        return label;
    }
    public static FirstTypes getLabelForId(Long id) {
        FirstTypes[] types = FirstTypes.values();
        for (FirstTypes type : types) {
            if (type.getId().equals(id)) {
                return type;
            }
        }
        return null;
    }
}
