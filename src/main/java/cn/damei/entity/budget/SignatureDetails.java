package cn.damei.entity.budget;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
public class SignatureDetails extends IdEntity implements Serializable {
    private static final long serialVersionUID = -429126620013347154L;
    private Long signatureId;
    private Long costItemId;
    private String costItemName;
    private Long costDetailId;
    private String costDetailName;
    private Double money;
    private String remark;
    public Long getSignatureId() {
        return signatureId;
    }
    public void setSignatureId(Long signatureId) {
        this.signatureId = signatureId;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
}