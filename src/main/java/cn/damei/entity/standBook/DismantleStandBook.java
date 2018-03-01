package cn.damei.entity.standBook;
import java.math.BigDecimal;
import cn.damei.entity.IdEntity;
public class DismantleStandBook extends IdEntity {
    private String orderNo;
    private String pdCategoryName;
    private String pdName;
    private String pdMeasureunit;
    private BigDecimal addCount;
    private Integer quotaWay;
    private BigDecimal quotaTotal;
    private BigDecimal quotaScale;
    private BigDecimal addTotal;
    private String description;
    private Integer addItemtype;
    private BigDecimal addUnitprice;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getPdCategoryName() {
        return pdCategoryName;
    }
    public void setPdCategoryName(String pdCategoryName) {
        this.pdCategoryName = pdCategoryName;
    }
    public String getPdName() {
        return pdName;
    }
    public void setPdName(String pdName) {
        this.pdName = pdName;
    }
    public String getPdMeasureunit() {
        return pdMeasureunit;
    }
    public void setPdMeasureunit(String pdMeasureunit) {
        this.pdMeasureunit = pdMeasureunit;
    }
    public BigDecimal getAddCount() {
        return addCount;
    }
    public void setAddCount(BigDecimal addCount) {
        this.addCount = addCount;
    }
    public Integer getQuotaWay() {
        return quotaWay;
    }
    public void setQuotaWay(Integer quotaWay) {
        this.quotaWay = quotaWay;
    }
    public BigDecimal getQuotaTotal() {
        return quotaTotal;
    }
    public void setQuotaTotal(BigDecimal quotaTotal) {
        this.quotaTotal = quotaTotal;
    }
    public BigDecimal getQuotaScale() {
        return quotaScale;
    }
    public void setQuotaScale(BigDecimal quotaScale) {
        this.quotaScale = quotaScale;
    }
    public BigDecimal getAddTotal() {
        return addTotal;
    }
    public void setAddTotal(BigDecimal addTotal) {
        this.addTotal = addTotal;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getAddItemtype() {
        return addItemtype;
    }
    public void setAddItemtype(Integer addItemtype) {
        this.addItemtype = addItemtype;
    }
    public BigDecimal getAddUnitprice() {
        return addUnitprice;
    }
    public void setAddUnitprice(BigDecimal addUnitprice) {
        this.addUnitprice = addUnitprice;
    }
}
