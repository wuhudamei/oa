package cn.damei.entity.standBook;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.utils.DateUtil;
import java.math.BigDecimal;
import java.util.Date;
public class Sale extends IdEntity {
    private String orderNo;
    private String serviceName;
    private String orgCode;
    private String serviceMobile;
    private String origin;
    private BigDecimal budgetAmount;
    private Date reMeasureCreateTime;
    private Integer planDecorateMonth;
    private String planDecorateYearMouth;
    private BigDecimal imprestAmount;
    private Integer isImprestAmount;
    private Boolean isHaveNoBackTag;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getServiceMobile() {
        return serviceMobile;
    }
    public void setServiceMobile(String serviceMobile) {
        this.serviceMobile = serviceMobile;
    }
    public String getOrigin() {
        return origin;
    }
    public void setOrigin(String origin) {
        this.origin = origin;
    }
    public BigDecimal getBudgetAmount() {
        return budgetAmount;
    }
    public void setBudgetAmount(BigDecimal budgetAmount) {
        this.budgetAmount = budgetAmount;
    }
    public Date getReMeasureCreateTime() {
        return reMeasureCreateTime;
    }
    public void setReMeasureCreateTime(Date reMeasureCreateTime) {
        this.reMeasureCreateTime = reMeasureCreateTime;
    }
    public Integer getPlanDecorateMonth() {
        return planDecorateMonth;
    }
    public void setPlanDecorateMonth(Integer planDecorateMonth) {
        this.planDecorateMonth = planDecorateMonth;
    }
    public BigDecimal getImprestAmount() {
        return imprestAmount;
    }
    public String getPlanDecorateYearMouth() {
        return planDecorateYearMouth;
    }
    public void setPlanDecorateYearMouth(String planDecorateYearMouth) {
        this.planDecorateYearMouth = planDecorateYearMouth;
    }
    public void setImprestAmount(BigDecimal imprestAmount) {
        this.imprestAmount = imprestAmount;
    }
    public Integer getIsImprestAmount() {
        return isImprestAmount;
    }
    public void setIsImprestAmount(Integer isImprestAmount) {
        this.isImprestAmount = isImprestAmount;
    }
    public Boolean getHaveNoBackTag() {
        return isHaveNoBackTag;
    }
    public void setHaveNoBackTag(Boolean haveNoBackTag) {
        isHaveNoBackTag = haveNoBackTag;
    }
}
