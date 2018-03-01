package cn.damei.entity.standBook;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.utils.DateUtil;
import java.math.BigDecimal;
import java.util.Date;
public class SelectMaterialStandBook extends IdEntity{
    private String flagName;//商品类目
    private String principalMaterialflagName;//主材类目
    private String auxiliaryMaterialflagName;//辅材类目
    private String pdBrandName;//商品品牌
    private String pdModel;//商品型号
    private String pdSkuSalesattribute;//商品属性
    private String doMain;//位置
    private BigDecimal count;//数量
    private String pdMeasureunit;//单位/计价方式
    private BigDecimal upgradeTotal;//升级价
    private BigDecimal addTotal;//增项价/合价
    private BigDecimal lessenTotal;//减项价
    private String pdName;//基装定额定额名称
    private BigDecimal addCount;//基装定额数量
    private BigDecimal addUnitprice;//基装定额单价或占比
    private String remarkContent;//基装定额设计备注
    private Short itemType;//其他金额增减类型
    private Short tax;//是否税后减项
    private BigDecimal account;//额度
    private String submitUserName;//批准人
    private String info;//基装定额介绍
    private String dosage;//用量
    private BigDecimal measurehousearea;//房屋面积
    private BigDecimal unitprice;//面积单价
    private String pdCategoryName;//定额分类
    private String name;//名称
    private BigDecimal pdPrice;//材料成本单价
    private BigDecimal lessenCount;//减项基装定额数量
    private BigDecimal lessenUnitprice;//减项基装定额单价或占比
    private BigDecimal upgradeUnitprice;//
    private BigDecimal smStoreprice;
    private BigDecimal consumptiondosage;
    private BigDecimal quotaScale;
    private String pdSkuname;
    private String pdSupplier;
    @JsonFormat(pattern = DateUtil.DATE_TIME_PATTERN)
    private Date acceptanceTime;
    private String dosages;//辅材用量
    private BigDecimal workerPrice;
    private BigDecimal itemPrice;
    private BigDecimal itemTotal;
    private String unitconversion;
    private BigDecimal squareCount;
    public BigDecimal getQuotaScale() {
        return quotaScale;
    }
    public void setQuotaScale(BigDecimal quotaScale) {
        this.quotaScale = quotaScale;
    }
    public BigDecimal getConsumptiondosage() {
        return consumptiondosage;
    }
    public void setConsumptiondosage(BigDecimal consumptiondosage) {
        this.consumptiondosage = consumptiondosage;
    }
    public BigDecimal getSmStoreprice() {
        return smStoreprice;
    }
    public void setSmStoreprice(BigDecimal smStoreprice) {
        this.smStoreprice = smStoreprice;
    }
    public BigDecimal getUpgradeUnitprice() {
        return upgradeUnitprice;
    }
    public void setUpgradeUnitprice(BigDecimal upgradeUnitprice) {
        this.upgradeUnitprice = upgradeUnitprice;
    }
    public BigDecimal getLessenCount() {
        return lessenCount;
    }
    public void setLessenCount(BigDecimal lessenCount) {
        this.lessenCount = lessenCount;
    }
    public BigDecimal getLessenUnitprice() {
        return lessenUnitprice;
    }
    public void setLessenUnitprice(BigDecimal lessenUnitprice) {
        this.lessenUnitprice = lessenUnitprice;
    }
    public String getAuxiliaryMaterialflagName() {
        return auxiliaryMaterialflagName;
    }
    public void setAuxiliaryMaterialflagName(String auxiliaryMaterialflagName) {
        this.auxiliaryMaterialflagName = auxiliaryMaterialflagName;
    }
    public String getPrincipalMaterialflagName() {
        return principalMaterialflagName;
    }
    public void setPrincipalMaterialflagName(String principalMaterialflagName) {
        this.principalMaterialflagName = principalMaterialflagName;
    }
    public BigDecimal getPdPrice() {
        return pdPrice;
    }
    public void setPdPrice(BigDecimal pdPrice) {
        this.pdPrice = pdPrice;
    }
    public String getPdCategoryName() {
        return pdCategoryName;
    }
    public void setPdCategoryName(String pdCategoryName) {
        this.pdCategoryName = pdCategoryName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public BigDecimal getMeasurehousearea() {
        return measurehousearea;
    }
    public void setMeasurehousearea(BigDecimal measurehousearea) {
        this.measurehousearea = measurehousearea;
    }
    public BigDecimal getUnitprice() {
        return unitprice;
    }
    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }
    public String getDosage() {
      return String.valueOf(this.consumptiondosage+this.pdMeasureunit) ;
    }
    public void setDosage(String dosage) {
        this.dosage = dosage;
    }
    public String getDosages() {
        return String.valueOf(this.count+this.pdMeasureunit) ;
    }
    public void setDosages(String dosages) {
        this.dosages = dosages;
    }
    public String getFlagName() {
        return flagName;
    }
    public void setFlagName(String flagName) {
        this.flagName = flagName;
    }
    public String getPdBrandName() {
        return pdBrandName;
    }
    public void setPdBrandName(String pdBrandName) {
        this.pdBrandName = pdBrandName;
    }
    public String getPdModel() {
        return pdModel;
    }
    public void setPdModel(String pdModel) {
        this.pdModel = pdModel;
    }
    public String getPdSkuSalesattribute() {
        return pdSkuSalesattribute;
    }
    public void setPdSkuSalesattribute(String pdSkuSalesattribute) {
        this.pdSkuSalesattribute = pdSkuSalesattribute;
    }
    public String getDoMain() {
        return doMain;
    }
    public void setDoMain(String doMain) {
        this.doMain = doMain;
    }
    public BigDecimal getCount() {
        return count;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }
    public String getPdMeasureunit() {
        return pdMeasureunit;
    }
    public void setPdMeasureunit(String pdMeasureunit) {
        this.pdMeasureunit = pdMeasureunit;
    }
    public BigDecimal getUpgradeTotal() {
        return upgradeTotal;
    }
    public void setUpgradeTotal(BigDecimal upgradeTotal) {
        this.upgradeTotal = upgradeTotal;
    }
    public BigDecimal getAddTotal() {
        return addTotal;
    }
    public void setAddTotal(BigDecimal addTotal) {
        this.addTotal = addTotal;
    }
    public BigDecimal getLessenTotal() {
        return lessenTotal;
    }
    public void setLessenTotal(BigDecimal lessenTotal) {
        this.lessenTotal = lessenTotal;
    }
    public String getPdName() {
        return pdName;
    }
    public void setPdName(String pdName) {
        this.pdName = pdName;
    }
    public BigDecimal getAddCount() {
        return addCount;
    }
    public void setAddCount(BigDecimal addCount) {
        this.addCount = addCount;
    }
    public BigDecimal getAddUnitprice() {
        return addUnitprice;
    }
    public void setAddUnitprice(BigDecimal addUnitprice) {
        this.addUnitprice = addUnitprice;
    }
    public String getRemarkContent() {
        return remarkContent;
    }
    public void setRemarkContent(String remarkContent) {
        this.remarkContent = remarkContent;
    }
    public Short getItemType() {
        return itemType;
    }
    public void setItemType(Short itemType) {
        this.itemType = itemType;
    }
    public Short getTax() {
        return tax;
    }
    public void setTax(Short tax) {
        this.tax = tax;
    }
    public BigDecimal getAccount() {
        return account;
    }
    public void setAccount(BigDecimal account) {
        this.account = account;
    }
    public String getSubmitUserName() {
        return submitUserName;
    }
    public void setSubmitUserName(String submitUserName) {
        this.submitUserName = submitUserName;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getPdSkuname() {
        return pdSkuname;
    }
    public void setPdSkuname(String pdSkuname) {
        this.pdSkuname = pdSkuname;
    }
    public String getPdSupplier() {
        return pdSupplier;
    }
    public void setPdSupplier(String pdSupplier) {
        this.pdSupplier = pdSupplier;
    }
    public Date getAcceptanceTime() {
        return acceptanceTime;
    }
    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }
    public BigDecimal getWorkerPrice() {
        return workerPrice;
    }
    public void setWorkerPrice(BigDecimal workerPrice) {
        this.workerPrice = workerPrice;
    }
    public BigDecimal getItemPrice() {
        return itemPrice;
    }
    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }
    public BigDecimal getItemTotal() {
        return itemTotal;
    }
    public void setItemTotal(BigDecimal itemTotal) {
        this.itemTotal = itemTotal;
    }
    public String getUnitconversion() {
        return unitconversion;
    }
    public void setUnitconversion(String unitconversion) {
        this.unitconversion = unitconversion;
    }
    public BigDecimal getSquareCount() {
        return squareCount;
    }
    public void setSquareCount(BigDecimal squareCount) {
        this.squareCount = squareCount;
    }
}
