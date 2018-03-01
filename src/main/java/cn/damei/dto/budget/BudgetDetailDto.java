package cn.damei.dto.budget;
public class BudgetDetailDto {
    private String name;
    private Double lastMonthBudget;
    private Double lastMonthExecution;
    private Double thisMonthBudget;
    private String remark;
    private Double thisMonthBudgetTotal;
    private Double thisYearBudgetTotal;
    private Double thisYearExecution;
    public String getName() {
        return name;
    }
    public BudgetDetailDto setName(String name) {
        this.name = name;
        return this;
    }
    public Double getLastMonthBudget() {
        return lastMonthBudget;
    }
    public BudgetDetailDto setLastMonthBudget(Double lastMonthBudget) {
        this.lastMonthBudget = lastMonthBudget;
        return this;
    }
    public Double getLastMonthExecution() {
        return lastMonthExecution;
    }
    public BudgetDetailDto setLastMonthExecution(Double lastMonthExecution) {
        this.lastMonthExecution = lastMonthExecution;
        return this;
    }
    public Double getThisMonthBudget() {
        return thisMonthBudget;
    }
    public BudgetDetailDto setThisMonthBudget(Double thisMonthBudget) {
        this.thisMonthBudget = thisMonthBudget;
        return this;
    }
    public String getRemark() {
        return remark;
    }
    public BudgetDetailDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Double getThisMonthBudgetTotal() {
        return thisMonthBudgetTotal;
    }
    public BudgetDetailDto setThisMonthBudgetTotal(Double thisMonthBudgetTotal) {
        this.thisMonthBudgetTotal = thisMonthBudgetTotal;
        return this;
    }
    public Double getThisYearBudgetTotal() {
        return thisYearBudgetTotal;
    }
    public BudgetDetailDto setThisYearBudgetTotal(Double thisYearBudgetTotal) {
        this.thisYearBudgetTotal = thisYearBudgetTotal;
        return this;
    }
    public Double getThisYearExecution() {
        return thisYearExecution;
    }
    public BudgetDetailDto setThisYearExecution(Double thisYearExecution) {
        this.thisYearExecution = thisYearExecution;
        return this;
    }
}
