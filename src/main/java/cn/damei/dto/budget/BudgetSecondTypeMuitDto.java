package cn.damei.dto.budget;
import java.io.Serializable;
import cn.damei.entity.budget.BudgetResultMap;
public class BudgetSecondTypeMuitDto extends BaseSecondTypeDto implements Serializable {
    private static final long serialVersionUID = 6733529796852329384L;
    private Long id;
    private String name;
    private double lastMonthBudget;
    private double lastMonthPayment;
    private double lastMonthPurchase;
    private double currentSinceBudget;
    private double currentMonthBudget;
    private double currentYearBudget;
    private double currentYearPayment;
    private double currentYearPurchase;
    private String remark;
    public BudgetSecondTypeMuitDto() {
    }
    public BudgetSecondTypeMuitDto(BudgetResultMap budgetResultMap) {
        this.lastMonthBudget = budgetResultMap.getLastMonthBudget();
        this.lastMonthPayment = budgetResultMap.getLastMonthPayment();
        this.lastMonthPurchase = budgetResultMap.getLastMonthPurchase();
        this.currentSinceBudget = budgetResultMap.getCurrentSinceBudget();
        this.currentMonthBudget = budgetResultMap.getCurrentMonthBudget();
        this.currentYearBudget = budgetResultMap.getCurrentYearBudget();
        this.currentYearPayment = budgetResultMap.getCurrentYearPayment();
        this.currentYearPurchase = budgetResultMap.getCurrentYearPurchase();
        this.remark = budgetResultMap.getRemark();
    }
    public double getLastMonthExecuter() {
        return lastMonthPayment + lastMonthPurchase;
    }
    public double getCurrentYearExecuter() {
        return currentYearPayment + currentYearPurchase;
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
