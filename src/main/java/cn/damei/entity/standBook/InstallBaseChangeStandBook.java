package cn.damei.entity.standBook;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class InstallBaseChangeStandBook extends IdEntity {
    private Integer changeType;
    private String constructionChangeNo;
    private String changeProjectName;
    private String unit;
    private BigDecimal amount;
    private BigDecimal totalUnitPrice;
    private BigDecimal unitProjectTotalPrice;
    private String explain;
    private BigDecimal loss;
    private BigDecimal laborCosts;
    private Date changeApplyDate;
    public Integer getChangeType() {
        return changeType;
    }
    public void setChangeType(Integer changeType) {
        this.changeType = changeType;
    }
    public String getConstructionChangeNo() {
        return constructionChangeNo;
    }
    public void setConstructionChangeNo(String constructionChangeNo) {
        this.constructionChangeNo = constructionChangeNo;
    }
    public String getChangeProjectName() {
        return changeProjectName;
    }
    public void setChangeProjectName(String changeProjectName) {
        this.changeProjectName = changeProjectName;
    }
    public String getUnit() {
        return unit;
    }
    public void setUnit(String unit) {
        this.unit = unit;
    }
    public BigDecimal getAmount() {
        return amount;
    }
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    public BigDecimal getTotalUnitPrice() {
        return totalUnitPrice;
    }
    public void setTotalUnitPrice(BigDecimal totalUnitPrice) {
        this.totalUnitPrice = totalUnitPrice;
    }
    public BigDecimal getUnitProjectTotalPrice() {
        return unitProjectTotalPrice;
    }
    public void setUnitProjectTotalPrice(BigDecimal unitProjectTotalPrice) {
        this.unitProjectTotalPrice = unitProjectTotalPrice;
    }
    public String getExplain() {
        return explain;
    }
    public void setExplain(String explain) {
        this.explain = explain;
    }
    public BigDecimal getLoss() {
        return loss;
    }
    public void setLoss(BigDecimal loss) {
        this.loss = loss;
    }
    public BigDecimal getLaborCosts() {
        return laborCosts;
    }
    public void setLaborCosts(BigDecimal laborCosts) {
        this.laborCosts = laborCosts;
    }
    public Date getChangeApplyDate() {
        return changeApplyDate;
    }
    public void setChangeApplyDate(Date changeApplyDate) {
        this.changeApplyDate = changeApplyDate;
    }
}
