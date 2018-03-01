package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StRdBillBatchDetail extends IdEntity {
    private static final long serialVersionUID = 1L;
    private String billBatchDetailId;
    private String billBatchId;
    private String billItemId;
    private BigDecimal billNumber;
    private BigDecimal billAmountMoney;
    private String customerName;
    private String orderNo;
    private String pdSkuname;
    private String pdPrice;
    private Boolean settlement;
    private Date settlementTime;
    public Boolean getSettlement() {
        return settlement;
    }
    public void setSettlement(Boolean settlement) {
        this.settlement = settlement;
    }
    public Date getSettlementTime() {
        return settlementTime;
    }
    public void setSettlementTime(Date settlementTime) {
        this.settlementTime = settlementTime;
    }
    public String getBillBatchDetailId() {
        return billBatchDetailId;
    }
    public void setBillBatchDetailId(String billBatchDetailId) {
        this.billBatchDetailId = billBatchDetailId;
    }
    public String getBillBatchId() {
        return billBatchId;
    }
    public void setBillBatchId(String billBatchId) {
        this.billBatchId = billBatchId;
    }
    public String getBillItemId() {
        return billItemId;
    }
    public void setBillItemId(String billItemId) {
        this.billItemId = billItemId;
    }
    public BigDecimal getBillNumber() {
        return billNumber;
    }
    public void setBillNumber(BigDecimal billNumber) {
        this.billNumber = billNumber;
    }
    public BigDecimal getBillAmountMoney() {
        return billAmountMoney;
    }
    public void setBillAmountMoney(BigDecimal billAmountMoney) {
        this.billAmountMoney = billAmountMoney;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getPdSkuname() {
        return pdSkuname;
    }
    public void setPdSkuname(String pdSkuname) {
        this.pdSkuname = pdSkuname;
    }
    public String getPdPrice() {
        return pdPrice;
    }
    public void setPdPrice(String pdPrice) {
        this.pdPrice = pdPrice;
    }
}
