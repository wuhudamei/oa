package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StRdBillMaterial extends IdEntity {
    private static final long serialVersionUID = 1L;
    private String billMaterialId;
    private String orderId;
    private String orderNo;
    private String customerName;
    private String customerMobile;
    private String projectManagerName;
    private String projectManagerMobile;
    private String materialPkId;
    private String houseAddress;
    private Boolean wholeBill;
    private BigDecimal finalAillAmount;
    private Date createTime;
    private String remarks;
    public String getBillMaterialId() {
        return billMaterialId;
    }
    public void setBillMaterialId(String billMaterialId) {
        this.billMaterialId = billMaterialId;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerMobile() {
        return customerMobile;
    }
    public void setCustomerMobile(String customerMobile) {
        this.customerMobile = customerMobile;
    }
    public String getProjectManagerName() {
        return projectManagerName;
    }
    public void setProjectManagerName(String projectManagerName) {
        this.projectManagerName = projectManagerName;
    }
    public String getProjectManagerMobile() {
        return projectManagerMobile;
    }
    public void setProjectManagerMobile(String projectManagerMobile) {
        this.projectManagerMobile = projectManagerMobile;
    }
    public String getMaterialPkId() {
        return materialPkId;
    }
    public void setMaterialPkId(String materialPkId) {
        this.materialPkId = materialPkId;
    }
    public String getHouseAddress() {
        return houseAddress;
    }
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
    public Boolean getWholeBill() {
        return wholeBill;
    }
    public void setWholeBill(Boolean wholeBill) {
        this.wholeBill = wholeBill;
    }
    public BigDecimal getFinalAillAmount() {
        return finalAillAmount;
    }
    public void setFinalAillAmount(BigDecimal finalAillAmount) {
        this.finalAillAmount = finalAillAmount;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public String getRemarks() {
        return remarks;
    }
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
}
