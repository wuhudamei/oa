package cn.damei.entity.budget;
import java.io.Serializable;
public class BudgetResultMap implements Serializable {
    private static final long serialVersionUID = 5986568260990655509L;
    private double lastMonthBudget;
    private double lastMonthPayment;
    private double lastMonthPurchase;
    private double currentSinceBudget;
    private double currentMonthBudget;
    private double currentYearBudget;
    private double currentYearPayment;
    private double currentYearPurchase;
    private String remark;
    public boolean totalIsZero() {
        double total =
                lastMonthBudget +
                        lastMonthPayment +
                        lastMonthPurchase +
                        currentSinceBudget +
                        currentMonthBudget +
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
    public double getCurrentSinceBudget() {
        return currentSinceBudget;
    }
    public void setCurrentSinceBudget(double currentSinceBudget) {
        this.currentSinceBudget = currentSinceBudget;
    }
    public double getCurrentMonthBudget() {
        return currentMonthBudget;
    }
    public void setCurrentMonthBudget(double currentMonthBudget) {
        this.currentMonthBudget = currentMonthBudget;
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
