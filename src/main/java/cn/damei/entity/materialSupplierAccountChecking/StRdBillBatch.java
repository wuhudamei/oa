package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StRdBillBatch extends IdEntity {
    private static final long serialVersionUID = 1L;
    private String billBatchId;
    private String supplier;
    private BigDecimal billAmountMoney;
    private String billBatch;
    private String batchExplain;
    private String settlementPeriod;
    private String operator;
    private Date operateTime;
    private String updateName;
    private Date updateTime;
    private String remarks;
    public String getBillBatchId() {
        return billBatchId;
    }
    public void setBillBatchId(String billBatchId) {
        this.billBatchId = billBatchId;
    }
    public String getSupplier() {
        return supplier;
    }
    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }
    public BigDecimal getBillAmountMoney() {
        return billAmountMoney;
    }
    public void setBillAmountMoney(BigDecimal billAmountMoney) {
        this.billAmountMoney = billAmountMoney;
    }
    public String getBillBatch() {
        return billBatch;
    }
    public void setBillBatch(String billBatch) {
        this.billBatch = billBatch;
    }
    public String getBatchExplain() {
        return batchExplain;
    }
    public void setBatchExplain(String batchExplain) {
        this.batchExplain = batchExplain;
    }
    public String getSettlementPeriod() {
        return settlementPeriod;
    }
    public void setSettlementPeriod(String settlementPeriod) {
        this.settlementPeriod = settlementPeriod;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public Date getOperateTime() {
        return operateTime;
    }
    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }
    public String getUpdateName() {
        return updateName;
    }
    public void setUpdateName(String updateName) {
        this.updateName = updateName;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
