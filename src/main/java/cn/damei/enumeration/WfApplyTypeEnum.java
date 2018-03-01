package cn.damei.enumeration;
public enum WfApplyTypeEnum {
    BUSINESS("出差"), LEAVE("请假"), BUDGET("预算"),YEARBUDGET("年度预算"), EXPENSE("报销"), PURCHASE("采购"),SIGNATURE("费用"),COMMON("通用");
    private String label;
    WfApplyTypeEnum(String label) {
        this.label = label;
    }
    public String getLabel() {
        return label;
    }
}
