package cn.damei.entity.budget;
public class BudgetMoney {
    private Long subId;
    private String date;
    private Double money;
    public Long getSubId() {
        return subId;
    }
    public void setSubId(Long subId) {
        this.subId = subId;
    }
    public Double getMoney() {
        if (money == null)
            return 0d;
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
}
