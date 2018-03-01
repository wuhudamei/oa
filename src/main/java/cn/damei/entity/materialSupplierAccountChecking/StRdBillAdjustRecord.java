package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StRdBillAdjustRecord extends IdEntity {
    private static final long serialVersionUID = 1L;
    private String billAdjustRecordId;
    private String billItemId;
    private BigDecimal pdChangeCountBefore;
    private BigDecimal pdChangeCountEnd;
    private String changeAdjust;
    private Date changeTime;
    private String operator;
    private String operateType;
    public String getBillAdjustRecordId() {
        return billAdjustRecordId;
    }
    public void setBillAdjustRecordId(String billAdjustRecordId) {
        this.billAdjustRecordId = billAdjustRecordId;
    }
    public String getBillItemId() {
        return billItemId;
    }
    public void setBillItemId(String billItemId) {
        this.billItemId = billItemId;
    }
    public BigDecimal getPdChangeCountBefore() {
        return pdChangeCountBefore;
    }
    public void setPdChangeCountBefore(BigDecimal pdChangeCountBefore) {
        this.pdChangeCountBefore = pdChangeCountBefore;
    }
    public BigDecimal getPdChangeCountEnd() {
        return pdChangeCountEnd;
    }
    public void setPdChangeCountEnd(BigDecimal pdChangeCountEnd) {
        this.pdChangeCountEnd = pdChangeCountEnd;
    }
    public String getChangeAdjust() {
        return changeAdjust;
    }
    public void setChangeAdjust(String changeAdjust) {
        this.changeAdjust = changeAdjust;
    }
    public Date getChangeTime() {
        return changeTime;
    }
    public void setChangeTime(Date changeTime) {
        this.changeTime = changeTime;
    }
    public String getOperator() {
        return operator;
    }
    public void setOperator(String operator) {
        this.operator = operator;
    }
    public String getOperateType() {
        return operateType;
    }
    public void setOperateType(String operateType) {
        this.operateType = operateType;
    }
}
