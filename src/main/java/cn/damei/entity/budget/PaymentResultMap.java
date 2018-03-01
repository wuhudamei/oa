package cn.damei.entity.budget;
public class PaymentResultMap {
    private double lastMonthBudget;
    private double lastMonthPayment;
    private double lastMonthPurchase;
    private double currentSincePayment;
    private double currentMonthBudget;
    private double currentMonthPayment;
    private double currentMonthPurchase;
    private double currentYearBudget;
    private double currentYearPayment;
    private double currentYearPurchase;
    private String remark;
    public boolean totalIsZero() {
        double total = lastMonthBudget +
                lastMonthPayment +
                lastMonthPurchase +
                currentSincePayment +
                currentMonthBudget +
                currentMonthPayment +
                currentMonthPurchase +
                currentYearBudget +
                currentYearPayment +
                currentYearPurchase;
        return total == 0;
    }
    public double getLastMonthBudget() {
        return lastMonthBudget;
    }
    public void setLastMonthBudget(double lastMonthBudget) {
        this.lastMonthBudget = lastMonthBudget;
    }
    public double getLastMonthPayment() {
        return lastMonthPayment;
    }
    public void setLastMonthPayment(double lastMonthPayment) {
        this.lastMonthPayment = lastMonthPayment;
    }
    public double getLastMonthPurchase() {
        return lastMonthPurchase;
    }
    public void setLastMonthPurchase(double lastMonthPurchase) {
        this.lastMonthPurchase = lastMonthPurchase;
    }
    public double getCurrentSincePayment() {
        return currentSincePayment;
    }
    public void setCurrentSincePayment(double currentSincePayment) {
        this.currentSincePayment = currentSincePayment;
    }
    public double getCurrentMonthBudget() {
        return currentMonthBudget;
    }
    public void setCurrentMonthBudget(double currentMonthBudget) {
        this.currentMonthBudget = currentMonthBudget;
    }
    public double getCurrentMonthPayment() {
        return currentMonthPayment;
    }
    public void setCurrentMonthPayment(double currentMonthPayment) {
        this.currentMonthPayment = currentMonthPayment;
    }
    public double getCurrentMonthPurchase() {
        return currentMonthPurchase;
    }
    public void setCurrentMonthPurchase(double currentMonthPurchase) {
        this.currentMonthPurchase = currentMonthPurchase;
    }
    public double getCurrentYearBudget() {
        return currentYearBudget;
    }
    public void setCurrentYearBudget(double currentYearBudget) {
        this.currentYearBudget = currentYearBudget;
    }
    public double getCurrentYearPayment() {
        return currentYearPayment;
    }
    public void setCurrentYearPayment(double currentYearPayment) {
        this.currentYearPayment = currentYearPayment;
    }
    public double getCurrentYearPurchase() {
        return currentYearPurchase;
    }
    public void setCurrentYearPurchase(double currentYearPurchase) {
        this.currentYearPurchase = currentYearPurchase;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}
