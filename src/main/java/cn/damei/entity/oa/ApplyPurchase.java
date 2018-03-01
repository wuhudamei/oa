package cn.damei.entity.oa;
import java.util.Date;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.FirstTypes;
public class ApplyPurchase extends IdEntity {
    private String applyTitle;
    private String applyCode;
    private Long applyCompany;
    private Long firstTypeId;
    private Long secondTypeId;
    private String purchaseMonth;
    private String goodName;
    private Integer goodNum;
    private Double goodPrice;
    private Double totalPrice;
    private String description;
    private Long applyUser;
    @Transient
    private String firstName;
    @Transient
    private String secondName;
    @Transient
    private Double totalBudget;
    @Transient
    private Double leftBudget;
    private ApplyStatus status;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    private String currentApproverName;
    private FirstTypes type;
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
    public Long getApplyCompany() {
        return applyCompany;
    }
    public void setApplyCompany(Long applyCompany) {
        this.applyCompany = applyCompany;
    }
    public Long getApplyUser() {
        return applyUser;
    }
    public void setApplyUser(Long applyUser) {
        this.applyUser = applyUser;
    }
    public Long getFirstTypeId() {
        return firstTypeId;
    }
    public void setFirstTypeId(Long firstTypeId) {
        this.firstTypeId = firstTypeId;
    }
    public Long getSecondTypeId() {
        return secondTypeId;
    }
    public void setSecondTypeId(Long secondTypeId) {
        this.secondTypeId = secondTypeId;
    }
    public String getGoodName() {
        return goodName;
    }
    public void setGoodName(String goodName) {
        this.goodName = goodName == null ? null : goodName.trim();
    }
    public Integer getGoodNum() {
        return goodNum;
    }
    public void setGoodNum(Integer goodNum) {
        this.goodNum = goodNum;
    }
    public Double getGoodPrice() {
        return goodPrice;
    }
    public void setGoodPrice(Double goodPrice) {
        this.goodPrice = goodPrice;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }
    public String getPurchaseMonth() {
        return purchaseMonth;
    }
    public void setPurchaseMonth(String purchaseMonth) {
        this.purchaseMonth = purchaseMonth;
    }
    public ApplyStatus getStatus() {
        return status;
    }
    public void setStatus(ApplyStatus status) {
        this.status = status;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Double getTotalPrice() {
        return totalPrice;
    }
    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }
    public String getCurrentApproverName() {
        return currentApproverName;
    }
    public void setCurrentApproverName(String currentApproverName) {
        this.currentApproverName = currentApproverName;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public Double getTotalBudget() {
        return totalBudget;
    }
    public void setTotalBudget(Double totalBudget) {
        this.totalBudget = totalBudget;
    }
    public Double getLeftBudget() {
        return leftBudget;
    }
    public void setLeftBudget(Double leftBudget) {
        this.leftBudget = leftBudget;
    }
	public FirstTypes getType() {
		return type;
	}
	public void setType(FirstTypes type) {
		this.type = type;
	}
}