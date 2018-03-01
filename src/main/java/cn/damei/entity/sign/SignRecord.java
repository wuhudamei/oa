package cn.damei.entity.sign;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import java.util.Date;
public class SignRecord extends IdEntity{
    private Long employeeId;//员工id
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date signDate;//打卡时间
    private Float longitude;//经度
    private Float latitude;//纬度
    private String address;//打卡地址
    private String punchCardType;//考勤类型
    private String signType;//打卡类型（上班、下班）
    public Long getEmployeeId() {
        return employeeId;
    }
    public void setEmployeeId(Long employeeId) {
        this.employeeId = employeeId;
    }
    public Date getSignDate() {
        return signDate;
    }
    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }
    public Float getLongitude() {
        return longitude;
    }
    public void setLongitude(Float longitude) {
        this.longitude = longitude;
    }
    public Float getLatitude() {
        return latitude;
    }
    public void setLatitude(Float latitude) {
        this.latitude = latitude;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public String getPunchCardType() {
        return punchCardType;
    }
    public void setPunchCardType(String punchCardType) {
        this.punchCardType = punchCardType;
    }
    public String getSignType() {
        return signType;
    }
    public void setSignType(String signType) {
        this.signType = signType;
    }
}
