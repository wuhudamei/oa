package cn.damei.dto.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.budget.PaymentDetails;
import java.io.Serializable;
import java.util.Date;
public class PaymentCostDetailDto implements Serializable {
    private static final long serialVersionUID = 9136316554683485280L;
    private Long costDetailId;
    @JsonFormat(pattern = "yyyy年MM月dd")
    private Date costDate;
    private String detailName;
    private String remark;
    private Integer invoiceNum;
    private Double money;
    public Long getCostDetailId() {
        return costDetailId;
    }
    public PaymentCostDetailDto setCostDetailId(Long costDetailId) {
        this.costDetailId = costDetailId;
        return this;
    }
    public Date getCostDate() {
        return costDate;
    }
    public PaymentCostDetailDto setCostDate(Date costDate) {
        this.costDate = costDate;
        return this;
    }
    public String getDetailName() {
        return detailName;
    }
    public PaymentCostDetailDto setDetailName(String detailName) {
        this.detailName = detailName;
        return this;
    }
    public String getRemark() {
        return remark;
    }
    public PaymentCostDetailDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getInvoiceNum() {
        return invoiceNum;
    }
    public PaymentCostDetailDto setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }
    public Double getMoney() {
        return money;
    }
    public PaymentCostDetailDto setMoney(Double money) {
        this.money = money;
        return this;
    }
    public static PaymentCostDetailDto build(PaymentDetails costDetail) {
        return new PaymentCostDetailDto()
                .setCostDetailId(costDetail.getCostDetailId())
                .setDetailName(costDetail.getCostDetailName())
                .setCostDate(costDetail.getCostDate())
                .setInvoiceNum(costDetail.getInvoiceNum())
                .setMoney(costDetail.getMoney())
                .setRemark(costDetail.getRemark());
    }
}
