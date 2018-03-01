package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StRdBillItem extends IdEntity {
    private static final long serialVersionUID = 1L;
    private String billItemId;
    private String stRdBillMaterialId;
    private String orderId;
    private String orderNo;
    private String customerName;
    private String pdPkid;
    private String pdSkuid;
    private String pdSkuname;
    private String pdSkuSalesAttribute;
    private String pdBrandname;
    private String pdSupplier;
    private String pdModel;
    private String pdMeasureunit;
    private BigDecimal pdPrice;
    private BigDecimal pdCount;
    private BigDecimal pdChangeCount;
    private BigDecimal accumulateSettlementCount;
    private BigDecimal accumulateSettlementMoney;
    private String materialType;
    private String dataType;
    private String dataFrom;
    private Date acceptanceTime;
    private String acceptanceName;
    private String itemCategoryId;
    private String itemCategoryName;
    private String reconciliationStatus;
    private Boolean change;
    private Boolean wallfloorTiles;
    private String itemPkid;
    private Boolean delete;
    private String pdSupplierId;
    private Date materialCreateTime;
    private String rdMaterialId;
    private Date materialUpdateTime;
    private BigDecimal pdCurrentCount;
    private BigDecimal workerAcceptanceCount;
    private BigDecimal acceptance;
    private String billBatch;
    private String customerMobile;
    public String getIsChange() {
        return isChange;
    }
    public void setIsChange(String isChange) {
        this.isChange = isChange;
    }
    private String isChange;
    private BigDecimal count;
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    private  BigDecimal price;//导出使用（总金额）
    public BigDecimal getCount() {
        return count;
    }
    public void setCount(BigDecimal count) {
        this.count = count;
    }
    private String billNumber;
    private BigDecimal billAmountMoney;
    private String houseAddress;
    private Date operateTime;
    public Date getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    public String getHouseAddress() {
        return houseAddress;
    }
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
    public String getBillItemId() {
        return billItemId;
    }
    public void setBillItemId(String billItemId) {
        this.billItemId = billItemId;
    }
    public String getStRdBillMaterialId() {
        return stRdBillMaterialId;
    }
    public void setStRdBillMaterialId(String stRdBillMaterialId) {
        this.stRdBillMaterialId = stRdBillMaterialId;
    }
    public String getPdPkid() {
        return pdPkid;
    }
    public void setPdPkid(String pdPkid) {
        this.pdPkid = pdPkid;
    }
    public String getPdSkuid() {
        return pdSkuid;
    }
    public void setPdSkuid(String pdSkuid) {
        this.pdSkuid = pdSkuid;
    }
    public String getPdSkuname() {
        return pdSkuname;
    }
    public void setPdSkuname(String pdSkuname) {
        this.pdSkuname = pdSkuname;
    }
    public String getPdSkuSalesAttribute() {
        return pdSkuSalesAttribute;
    }
    public void setPdSkuSalesAttribute(String pdSkuSalesAttribute) {
        this.pdSkuSalesAttribute = pdSkuSalesAttribute;
    }
    public String getPdBrandname() {
        return pdBrandname;
    }
    public void setPdBrandname(String pdBrandname) {
        this.pdBrandname = pdBrandname;
    }
    public String getPdSupplier() {
        return pdSupplier;
    }
    public void setPdSupplier(String pdSupplier) {
        this.pdSupplier = pdSupplier;
    }
    public String getPdModel() {
        return pdModel;
    }
    public void setPdModel(String pdModel) {
        this.pdModel = pdModel;
    }
    public String getPdMeasureunit() {
        return pdMeasureunit;
    }
    public void setPdMeasureunit(String pdMeasureunit) {
        this.pdMeasureunit = pdMeasureunit;
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
    public BigDecimal getPdChangeCount() {
        return pdChangeCount;
    }
    public void setPdChangeCount(BigDecimal pdChangeCount) {
        this.pdChangeCount = pdChangeCount;
    }
    public BigDecimal getAccumulateSettlementCount() {
        return accumulateSettlementCount;
    }
    public void setAccumulateSettlementCount(BigDecimal accumulateSettlementCount) {
        this.accumulateSettlementCount = accumulateSettlementCount;
    }
    public BigDecimal getAccumulateSettlementMoney() {
        return accumulateSettlementMoney;
    }
    public void setAccumulateSettlementMoney(BigDecimal accumulateSettlementMoney) {
        this.accumulateSettlementMoney = accumulateSettlementMoney;
    }
    public String getMaterialType() {
        return materialType;
    }
    public void setMaterialType(String materialType) {
        this.materialType = materialType;
    }
    public String getDataType() {
        return dataType;
    }
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }
    public String getDataFrom() {
        return dataFrom;
    }
    public void setDataFrom(String dataFrom) {
        this.dataFrom = dataFrom;
    }
    public Date getAcceptanceTime() {
        return acceptanceTime;
    }
    public void setAcceptanceTime(Date acceptanceTime) {
        this.acceptanceTime = acceptanceTime;
    }
    public String getAcceptanceName() {
        return acceptanceName;
    }
    public void setAcceptanceName(String acceptanceName) {
        this.acceptanceName = acceptanceName;
    }
    public String getItemCategoryId() {
        return itemCategoryId;
    }
    public void setItemCategoryId(String itemCategoryId) {
        this.itemCategoryId = itemCategoryId;
    }
    public String getItemCategoryName() {
        return itemCategoryName;
    }
    public void setItemCategoryName(String itemCategoryName) {
        this.itemCategoryName = itemCategoryName;
    }
    public String getReconciliationStatus() {
        return reconciliationStatus;
    }
    public void setReconciliationStatus(String reconciliationStatus) {
        this.reconciliationStatus = reconciliationStatus;
    }
    public Boolean getChange() {
        return change;
    }
    public void setChange(Boolean change) {
        this.change = change;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getBillNumber() {
        return billNumber;
    }
    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }
    public BigDecimal getBillAmountMoney() {
        return billAmountMoney;
    }
    public void setBillAmountMoney(BigDecimal billAmountMoney) {
        this.billAmountMoney = billAmountMoney;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Boolean getWallfloorTiles() {
        return wallfloorTiles;
    }
    public void setWallfloorTiles(Boolean wallfloorTiles) {
        this.wallfloorTiles = wallfloorTiles;
    }
    public String getItemPkid() {
        return itemPkid;
    }
    public void setItemPkid(String itemPkid) {
        this.itemPkid = itemPkid;
    }
    public Boolean getDelete() {
        return delete;
    }
    public void setDelete(Boolean delete) {
        this.delete = delete;
    }
    public String getPdSupplierId() {
        return pdSupplierId;
    }
    public void setPdSupplierId(String pdSupplierId) {
        this.pdSupplierId = pdSupplierId;
    }
    public Date getMaterialCreateTime() {
        return materialCreateTime;
    }
    public void setMaterialCreateTime(Date materialCreateTime) {
        this.materialCreateTime = materialCreateTime;
    }
    public String getRdMaterialId() {
        return rdMaterialId;
    }
    public void setRdMaterialId(String rdMaterialId) {
        this.rdMaterialId = rdMaterialId;
    }
    public Date getMaterialUpdateTime() {
        return materialUpdateTime;
    }
    public void setMaterialUpdateTime(Date materialUpdateTime) {
        this.materialUpdateTime = materialUpdateTime;
    }
    public BigDecimal getPdCurrentCount() {
        return pdCurrentCount;
    }
    public void setPdCurrentCount(BigDecimal pdCurrentCount) {
        this.pdCurrentCount = pdCurrentCount;
    }
    public BigDecimal getWorkerAcceptanceCount() {
        return workerAcceptanceCount;
    }
    public void setWorkerAcceptanceCount(BigDecimal workerAcceptanceCount) {
        this.workerAcceptanceCount = workerAcceptanceCount;
    }
    public BigDecimal getAcceptance() {
        return acceptance;
    }
    public void setAcceptance(BigDecimal acceptance) {
        this.acceptance = acceptance;
    }
    public String getBillBatch() {
        return billBatch;
    }
    public void setBillBatch(String billBatch) {
        this.billBatch = billBatch;
    }
    public String getCustomerMobile() {
        return customerMobile;
    }
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
}
