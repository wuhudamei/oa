package cn.damei.entity.standBook;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class DesignTime extends IdEntity {
    private String orderNo;
    private Date signFinishTime;
    private Date firstAmountTime;
    private Date createTime;
    private Date measureFinishTime;
    private Date blueprintFinishTime;
    private Date StylistAllotTime;
    private String StylistMobile;
    private String StylistName;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Date getSignFinishTime() {
        return signFinishTime;
    }
    public void setSignFinishTime(Date signFinishTime) {
        this.signFinishTime = signFinishTime;
    }
    public Date getFirstAmountTime() {
        return firstAmountTime;
    }
    public void setFirstAmountTime(Date firstAmountTime) {
        this.firstAmountTime = firstAmountTime;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getMeasureFinishTime() {
        return measureFinishTime;
    }
    public void setMeasureFinishTime(Date measureFinishTime) {
        this.measureFinishTime = measureFinishTime;
    }
    public Date getBlueprintFinishTime() {
        return blueprintFinishTime;
    }
    public void setBlueprintFinishTime(Date blueprintFinishTime) {
        this.blueprintFinishTime = blueprintFinishTime;
    }
    public Date getStylistAllotTime() {
        return StylistAllotTime;
    }
    public void setStylistAllotTime(Date stylistAllotTime) {
        StylistAllotTime = stylistAllotTime;
    }
    public String getStylistMobile() {
        return StylistMobile;
    }
    public void setStylistMobile(String stylistMobile) {
        StylistMobile = stylistMobile;
    }
    public String getStylistName() {
        return StylistName;
    }
    public void setStylistName(String stylistName) {
        StylistName = stylistName;
    }
}
