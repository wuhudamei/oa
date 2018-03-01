package cn.damei.entity.standBook;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class ChangeStandBook extends IdEntity {
    private Date createTime;
    private String realName;
    private BigDecimal nowPdCount;
    private BigDecimal pdPrice;
    private String flagname;
    private String pdSkuname;
    private String pdName;
    private String pdModel;
    private String pdBrandname;
    private String pdSkuSalesattribute;
    private String pdMeasureunit;
    private String domage;
    private BigDecimal pdCount;
    private BigDecimal pdTotal;
    private BigDecimal nowPdTotal;
    private String itemType;
    private String changeNo;
    private BigDecimal incrementItem;
    private Integer quotaWay;
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRealName() {
        return realName;
    }
    public void setRealName(String realName) {
        this.realName = realName;
    }
    public BigDecimal getNowPdCount() {
        return nowPdCount;
    }
    public void setNowPdCount(BigDecimal nowPdCount) {
        this.nowPdCount = nowPdCount;
    }
    public BigDecimal getPdPrice() {
        return pdPrice;
    }
    public void setPdPrice(BigDecimal pdPrice) {
        this.pdPrice = pdPrice;
    }
    public String getFlagname() {
        return flagname;
    }
    public void setFlagname(String flagname) {
        this.flagname = flagname;
    }
    public String getPdSkuname() {
        return pdSkuname;
    }
    public void setPdSkuname(String pdSkuname) {
        this.pdSkuname = pdSkuname;
    }
    public String getPdName() {
        return pdName;
    }
    public void setPdName(String pdName) {
        this.pdName = pdName;
    }
    public String getPdModel() {
        return pdModel;
    }
    public void setPdModel(String pdModel) {
        this.pdModel = pdModel;
    }
    public String getPdBrandname() {
        return pdBrandname;
    }
    public void setPdBrandname(String pdBrandname) {
        this.pdBrandname = pdBrandname;
    }
    public String getPdSkuSalesattribute() {
        return pdSkuSalesattribute;
    }
    public void setPdSkuSalesattribute(String pdSkuSalesattribute) {
        this.pdSkuSalesattribute = pdSkuSalesattribute;
    }
    public String getPdMeasureunit() {
        return pdMeasureunit;
    }
    public void setPdMeasureunit(String pdMeasureunit) {
        this.pdMeasureunit = pdMeasureunit;
    }
    public String getDomage() {
        return domage;
    }
    public void setDomage(String domage) {
        this.domage = domage;
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
    public BigDecimal getNowPdTotal() {
        return nowPdTotal;
    }
    public void setNowPdTotal(BigDecimal nowPdTotal) {
        this.nowPdTotal = nowPdTotal;
    }
    public String getItemType() {
        return itemType;
    }
    public void setItemType(String itemType) {
        this.itemType = itemType;
    }
    public String getChangeNo() {
        return changeNo;
    }
    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo;
    }
    public BigDecimal getIncrementItem() {
        return incrementItem;
    }
    public void setIncrementItem(BigDecimal incrementItem) {
        this.incrementItem = incrementItem;
    }
    public Integer getQuotaWay() {
        return quotaWay;
    }
    public void setQuotaWay(Integer quotaWay) {
        this.quotaWay = quotaWay;
    }
}
