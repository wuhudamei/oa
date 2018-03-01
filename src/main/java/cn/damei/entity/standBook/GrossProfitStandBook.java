package cn.damei.entity.standBook;
import java.math.BigDecimal;
import cn.damei.entity.IdEntity;
public class GrossProfitStandBook extends IdEntity {
    private BigDecimal storePrice;
    private BigDecimal marketPrice;
    private String itemType;
    private String pdName;
    private BigDecimal pdPrice;
    private BigDecimal pdCount;
    private BigDecimal pdTotal;
    private Integer quotaWay;
    private BigDecimal dosageArea;
    private BigDecimal changeMoney;
    public BigDecimal getStorePrice() {
        return storePrice;
    }
    public void setStorePrice(BigDecimal storePrice) {
        this.storePrice = storePrice;
    }
    public BigDecimal getMarketPrice() {
        return marketPrice;
    }
    public void setMarketPrice(BigDecimal marketPrice) {
        this.marketPrice = marketPrice;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public String getPdName() {
        return pdName;
    }
    public void setPdName(String pdName) {
        this.pdName = pdName;
    }
    public BigDecimal getPdPrice() {
        return pdPrice;
    }
    public void setPdPrice(BigDecimal pdPrice) {
        this.pdPrice = pdPrice;
    }
    public BigDecimal getPdCount() {
        return pdCount;
    }
    public void setPdCount(BigDecimal pdCount) {
        this.pdCount = pdCount;
    }
    public BigDecimal getPdTotal() {
        return pdTotal;
    }
    public void setPdTotal(BigDecimal pdTotal) {
        this.pdTotal = pdTotal;
    }
    public Integer getQuotaWay() {
        return quotaWay;
    }
    public void setQuotaWay(Integer quotaWay) {
        this.quotaWay = quotaWay;
    }
    public BigDecimal getDosageArea() {
        return dosageArea;
    }
    public void setDosageArea(BigDecimal dosageArea) {
        this.dosageArea = dosageArea;
    }
    public BigDecimal getChangeMoney() {
        return changeMoney;
    }
    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }
}
