package cn.damei.entity.budget;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class BudgetDetails extends IdEntity implements Serializable {
    private static final long serialVersionUID = 1774888247663392947L;
    private Long budgetId;
    private Long costItemId;
    private String costItemName;
    private String costDetailNames;
    private Double money;
    private String remark;
    public Long getBudgetId() {
        return budgetId;
    }
    public void setBudgetId(Long budgetId) {
        this.budgetId = budgetId;
    }
    public Long getCostItemId() {
        return costItemId;
    }
    public void setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
    }
    public String getCostItemName() {
        return costItemName;
    }
    public void setCostItemName(String costItemName) {
        this.costItemName = costItemName;
    }
    public String getCostDetailNames() {
        return costDetailNames;
    }
    public void setCostDetailNames(String costDetailNames) {
        this.costDetailNames = costDetailNames;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}