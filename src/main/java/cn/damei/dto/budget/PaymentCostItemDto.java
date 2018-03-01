package cn.damei.dto.budget;
import org.apache.commons.collections.CollectionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class PaymentCostItemDto implements Serializable {
    private static final long serialVersionUID = -3303183634559708781L;
    private Long costItemId;
    private String costItemName;
    private Double costRemain;
    private Double yearRemain;
    private List<PaymentCostDetailDto> costDetails;
    public Double getItemMoney() {
        Double paymentMoney = 0d;
        if (CollectionUtils.isNotEmpty(costDetails)) {
            for (PaymentCostDetailDto detailDto : costDetails) {
                paymentMoney += detailDto.getMoney();
            }
        }
        return paymentMoney;
    }
    public void pushCostDetail(PaymentCostDetailDto costDetail) {
        if (this.costDetails == null) {
            this.costDetails = new ArrayList<>();
        }
        this.costDetails.add(costDetail);
    }
    public Long getCostItemId() {
        return costItemId;
    }
    public PaymentCostItemDto setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
        return this;
    }
    public String getCostItemName() {
        return costItemName;
    }
    public PaymentCostItemDto setCostItemName(String costItemName) {
        this.costItemName = costItemName;
        return this;
    }
    public Double getCostRemain() {
        return costRemain;
    }
    public PaymentCostItemDto setCostRemain(Double costRemain) {
        this.costRemain = costRemain;
        return this;
    }
    public Double getYearRemain() {
        return yearRemain;
    }
    public PaymentCostItemDto setYearRemain(Double yearRemain) {
        this.yearRemain = yearRemain;
        return this;
    }
    public List<PaymentCostDetailDto> getCostDetails() {
        return costDetails;
    }
    public PaymentCostItemDto setCostDetails(List<PaymentCostDetailDto> costDetails) {
        this.costDetails = costDetails;
        return this;
    }
}
