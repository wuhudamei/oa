package cn.damei.dto.budget;
import org.apache.commons.collections.CollectionUtils;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
public class SignatureCostItemDto implements Serializable {
    private static final long serialVersionUID = -3303183634559708781L;
    private Long costItemId;
    private String costItemName;
    private Double costRemain;
    private Double yearRemain;
    private List<SignatureCostDetailDto> costDetails;
    public Double getItemMoney() {
        Double paymentMoney = 0d;
        if (CollectionUtils.isNotEmpty(costDetails)) {
            for (SignatureCostDetailDto detailDto : costDetails) {
                paymentMoney += detailDto.getMoney();
            }
        }
        return paymentMoney;
    }
    public void pushCostDetail(SignatureCostDetailDto costDetail) {
        if (this.costDetails == null) {
            this.costDetails = new ArrayList<>();
        }
        this.costDetails.add(costDetail);
    }
    public Long getCostItemId() {
        return costItemId;
    }
    public SignatureCostItemDto setCostItemId(Long costItemId) {
        this.costItemId = costItemId;
        return this;
    }
    public String getCostItemName() {
        return costItemName;
    }
    public SignatureCostItemDto setCostItemName(String costItemName) {
        this.costItemName = costItemName;
        return this;
    }
    public Double getCostRemain() {
        return costRemain;
    }
    public SignatureCostItemDto setCostRemain(Double costRemain) {
        this.costRemain = costRemain;
        return this;
    }
    public Double getYearRemain() {
        return yearRemain;
    }
    public SignatureCostItemDto setYearRemain(Double yearRemain) {
        this.yearRemain = yearRemain;
        return this;
    }
    public List<SignatureCostDetailDto> getCostDetails() {
        return costDetails;
    }
    public SignatureCostItemDto setCostDetails(List<SignatureCostDetailDto> costDetails) {
        this.costDetails = costDetails;
        return this;
    }
}
