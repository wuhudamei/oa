package cn.damei.dto.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.budget.SignatureDetails;
import java.io.Serializable;
import java.util.Date;
public class SignatureCostDetailDto implements Serializable {
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
    public SignatureCostDetailDto setCostDetailId(Long costDetailId) {
        this.costDetailId = costDetailId;
        return this;
    }
    public Date getCostDate() {
        return costDate;
    }
    public SignatureCostDetailDto setCostDate(Date costDate) {
        this.costDate = costDate;
        return this;
    }
    public String getDetailName() {
        return detailName;
    }
    public SignatureCostDetailDto setDetailName(String detailName) {
        this.detailName = detailName;
        return this;
    }
    public String getRemark() {
        return remark;
    }
    public SignatureCostDetailDto setRemark(String remark) {
        this.remark = remark;
        return this;
    }
    public Integer getInvoiceNum() {
        return invoiceNum;
    }
    public SignatureCostDetailDto setInvoiceNum(Integer invoiceNum) {
        this.invoiceNum = invoiceNum;
        return this;
    }
    public Double getMoney() {
        return money;
    }
    public SignatureCostDetailDto setMoney(Double money) {
        this.money = money;
        return this;
    }
    public static SignatureCostDetailDto build(SignatureDetails costDetail) {
        return new SignatureCostDetailDto()
                .setCostDetailId(costDetail.getCostDetailId())
                .setDetailName(costDetail.getCostDetailName())
                .setMoney(costDetail.getMoney())
                .setRemark(costDetail.getRemark());
    }
}
