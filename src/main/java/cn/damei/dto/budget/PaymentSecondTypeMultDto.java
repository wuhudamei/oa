package cn.damei.dto.budget;
import java.io.Serializable;
import cn.damei.entity.budget.PaymentResultMap;
public class PaymentSecondTypeMultDto extends BaseSecondTypeDto implements Serializable {
    private static final long serialVersionUID = 7405375502370667608L;
    private Long id;
    private String name;
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
    public PaymentSecondTypeMultDto(PaymentResultMap paymentResultMap) {
        this.lastMonthBudget = paymentResultMap.getLastMonthBudget();
        this.lastMonthPayment = paymentResultMap.getLastMonthPayment();
        this.lastMonthPurchase = paymentResultMap.getLastMonthPurchase();
        this.currentSincePayment = paymentResultMap.getCurrentSincePayment();
        this.currentMonthBudget = paymentResultMap.getCurrentMonthBudget();
        this.currentMonthPayment = paymentResultMap.getCurrentMonthPayment();
        this.currentMonthPurchase = paymentResultMap.getCurrentMonthPurchase();
        this.currentYearBudget = paymentResultMap.getCurrentYearBudget();
        this.currentYearPayment = paymentResultMap.getCurrentYearPayment();
        this.currentYearPurchase = paymentResultMap.getCurrentYearPurchase();
        this.remark = paymentResultMap.getRemark();
    }
    public double getLastMonthExecuter() {
        return lastMonthPayment + lastMonthPurchase;
    }
    public double getCurrentYearExecuter() {
        return currentYearPayment + currentYearPurchase;
    }
    public double getCurrentMonthExecuter() {
        return currentMonthPayment + currentMonthPurchase;
    }
    public double getCurrentMonthSurplus() {
        return currentMonthBudget - getCurrentMonthExecuter();
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
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
