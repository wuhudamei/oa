package cn.damei.entity.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import java.io.Serializable;
import java.util.Date;
public class PaymentDetails extends IdEntity implements Serializable {
    private static final long serialVersionUID = 3773419049736271447L;
    private Long paymentId;
    @JsonFormat(pattern="yyyy-MM-dd",timezone = "GMT+08:00")
    private Date costDate;
    private Long costItemId;
    private String costItemName;
    private Long costDetailId;
    private String costDetailName;
    private Double money;
    private Integer invoiceNum;
    private String remark;
    public Date getCostDate() {
        return costDate;
    }
    public void setCostDate(Date costDate) {
        this.costDate = costDate;
    }
    public Long getPaymentId() {
        return paymentId;
    }
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }
    public Long getCostItemId() {
        return costItemId;
    }
    public void setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
    }
    public String getCostItemName() {
        return costItemName;
    }
    public void setCostItemName(String costItemName) {
        this.costItemName = costItemName;
    }
    public Long getCostDetailId() {
        return costDetailId;
    }
    public void setCostDetailId(Long costDetailId) {
        this.costDetailId = costDetailId;
    }
    public String getCostDetailName() {
        return costDetailName;
    }
    public void setCostDetailName(String costDetailName) {
        this.costDetailName = costDetailName;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public Integer getInvoiceNum() {
        return invoiceNum;
    }
    public void setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
    }
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}