package cn.damei.dto.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class PaymentDto implements Serializable {
    private static final long serialVersionUID = -8692912127226798050L;
    private Long id;
    private String applyTitle;
    private String applyCode;
    private Long signatureId;
    private String signatureTitle;
    private String company;
    private String paymentDate;
    private String applyName;
    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm:ss")
    private Date applyDate;
    private Double totalMoney;
    private String attachment;
    private Integer invoiceNum;
    private String remark;
    private List<PaymentCostItemDto> costItems;
    public void pushCostItem(PaymentCostItemDto costItem) {
        if (this.costItems == null) {
            this.costItems = new ArrayList<>();
        }
        this.costItems.add(costItem);
    }
    public Long getSignatureId() {
        return signatureId;
    }
    public void setSignatureId(Long signatureId) {
        this.signatureId = signatureId;
    }
    public String getSignatureTitle() {
        return signatureTitle;
    }
    public void setSignatureTitle(String signatureTitle) {
        this.signatureTitle = signatureTitle;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getApplyTitle() {
        return applyTitle;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
    }
    public void setApplyTitle(String applyTitle) {
        this.applyTitle = applyTitle;
    }
    public String getApplyCode() {
        return applyCode;
    }
    public void setApplyCode(String applyCode) {
        this.applyCode = applyCode;
    }
    public String getCompany() {
        return company;
    }
    public void setCompany(String company) {
        this.company = company;
    }
    public String getPaymentDate() {
        return paymentDate;
    }
    public void setPaymentDate(String paymentDate) {
        this.paymentDate = paymentDate;
    }
    public String getApplyName() {
        return applyName;
    }
    public void setApplyName(String applyName) {
        this.applyName = applyName;
    }
    public Date getApplyDate() {
        return applyDate;
    }
    public void setApplyDate(Date applyDate) {
        this.applyDate = applyDate;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
    }
    public Integer getInvoiceNum() {
        return invoiceNum;
    }
    public PaymentDto setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public List<PaymentCostItemDto> getCostItems() {
        return costItems;
    }
    public void setCostItems(List<PaymentCostItemDto> costItems) {
        this.costItems = costItems;
    }
}
