package cn.damei.dto.budget;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
public class SignatureDto implements Serializable {
    private static final long serialVersionUID = -8692912127226798050L;
    private Long id;
    private String applyTitle;
    private String applyCode;
    private String company;
    private String signatureDate;
    private String applyName;
    @JsonFormat(pattern = "yyyy年MM月dd日 hh:mm:ss")
    private Date applyDate;
    private Double totalMoney;
    private String attachment;
    private String remark;
    private List<SignatureCostItemDto> costItems;
    public void pushCostItem(SignatureCostItemDto costItem) {
        if (this.costItems == null) {
            this.costItems = new ArrayList<>();
        }
        this.costItems.add(costItem);
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
    public String getSignatureDate() {
        return signatureDate;
    }
    public void setSignatureDate(String signatureDate) {
        this.signatureDate = signatureDate;
    }
    public String getApplyName() {
        return applyName;
    }
    public String getAttachment() {
        return attachment;
    }
    public void setAttachment(String attachment) {
        this.attachment = attachment;
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
    public String getRemark() {
        return remark;
    }
    public void setRemark(String remark) {
        this.remark = remark;
    }
    public List<SignatureCostItemDto> getCostItems() {
        return costItems;
    }
    public void setCostItems(List<SignatureCostItemDto> costItems) {
        this.costItems = costItems;
    }
}
