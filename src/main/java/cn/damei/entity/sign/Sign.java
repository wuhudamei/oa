package cn.damei.entity.sign;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.SignType;
import java.util.Date;
public class Sign extends IdEntity {
    private Long empId;
    private String empName;
    private String orgCode;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date signTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
    private Date signOutTime;
    private String tmpSignTime;
    private String tmpSignOutTime;
    private String longitude;//经度
    private String latitude;
    private String address;
    private Long signSiteId;
    private Long company;
    private String companyName;
    private Long department;
    private Long punchCardType;//打卡类型，1是内勤，2是外勤
    private String outAddress;//签退地址
    private String outLatitude;//签退纬度
    private String outLongitude;//签退经度
    private Long outPunchCardType;//签退的打卡类型
    private SignType signType;//考勤类型
    private String creator;//创建人
    private Date creatTime;//创建时间
    private Date insertSigntypeTime;//插入未签到员工id时的时间
    private String depName;//部门名字
    private String BELATENUM;//迟到次数
    private String NORMALNUM;//正常次数
    private String LEAVEEARLYNUM;//早退次数
    private String ABSENTEEISMNUM;//旷工次数
    private String BELATEANDLEAVEEARLYNUM;//迟到并早退次数
    private String SIGNDAYSNUM;//出勤次数
    private String PUNCHCARDTYPENUM;//签到外勤人数
    private String OUTPUNCHCARDTYPENUM;//签退外勤人数
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+08:00")
    private Date depSignTime;//部门签到时间
    @JsonFormat(pattern = "HH:mm", timezone = "GMT+08:00")
    private Date depSignOutTime;//部门签退时间
    private Double shouldWorkDays;
    private Double practicalWorkDays;
    private String workType;//打卡类型（上班，给打卡记录表使用）
    private String outWorkType;//打卡类型（下班，给打卡记录表使用）
    public Double getPracticalWorkDays() {
        return practicalWorkDays;
    }
    public void setPracticalWorkDays(Double practicalWorkDays) {
        this.practicalWorkDays = practicalWorkDays;
    }
    public Double getShouldWorkDays() {
        return shouldWorkDays;
    }
    public void setShouldWorkDays(Double shouldWorkDays) {
        this.shouldWorkDays = shouldWorkDays;
    }
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public Date getSignTime() {
        return signTime;
    }
    public void setSignTime(Date signTime) {
        this.signTime = signTime;
    }
    public Date getSignOutTime() {
        return signOutTime;
    }
    public void setSignOutTime(Date signOutTime) {
        this.signOutTime = signOutTime;
    }
    public String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
    public String getLatitude() {
        return latitude;
    }
    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }
    public Long getSignSiteId() {
        return signSiteId;
    }
    public void setSignSiteId(Long signSiteId) {
        this.signSiteId = signSiteId;
    }
    public Long getCompany() {
        return company;
    }
    public void setCompany(Long company) {
        this.company = company;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public Long getDepartment() {
        return department;
    }
    public void setDepartment(Long department) {
        this.department = department;
    }
    public String getTmpSignTime() {
        return tmpSignTime;
    }
    public void setTmpSignTime(String tmpSignTime) {
        this.tmpSignTime = tmpSignTime;
    }
    public String getTmpSignOutTime() {
        return tmpSignOutTime;
    }
    public void setTmpSignOutTime(String tmpSignOutTime) {
        this.tmpSignOutTime = tmpSignOutTime;
    }
    public Long getPunchCardType() {
        return punchCardType;
    }
    public void setPunchCardType(Long punchCardType) {
        this.punchCardType = punchCardType;
    }
    public String getOutAddress() {
        return outAddress;
    }
    public void setOutAddress(String outAddress) {
        this.outAddress = outAddress;
    }
    public String getOutLatitude() {
        return outLatitude;
    }
    public void setOutLatitude(String outLatitude) {
        this.outLatitude = outLatitude;
    }
    public String getOutLongitude() {
        return outLongitude;
    }
    public void setOutLongitude(String outLongitude) {
        this.outLongitude = outLongitude;
    }
    public Long getOutPunchCardType() {
        return outPunchCardType;
    }
    public void setOutPunchCardType(Long outPunchCardType) {
        this.outPunchCardType = outPunchCardType;
    }
    public SignType getSignType() {
        return signType;
    }
    public void setSignType(SignType signType) {
        this.signType = signType;
    }
    public String getCreator() {
        return creator;
    }
    public void setCreator(String creator) {
        this.creator = creator;
    }
    public Date getCreatTime() {
        return creatTime;
    }
    public void setCreatTime(Date creatTime) {
        this.creatTime = creatTime;
    }
    public Date getInsertSigntypeTime() {
        return insertSigntypeTime;
    }
    public void setInsertSigntypeTime(Date insertSigntypeTime) {
        this.insertSigntypeTime = insertSigntypeTime;
    }
    public String getDepName() {
        return depName;
    }
    public void setDepName(String depName) {
        this.depName = depName;
    }
    public String getBELATENUM() {
        return BELATENUM;
    }
    public void setBELATENUM(String BELATENUM) {
        this.BELATENUM = BELATENUM;
    }
    public String getNORMALNUM() {
        return NORMALNUM;
    }
    public void setNORMALNUM(String NORMALNUM) {
        this.NORMALNUM = NORMALNUM;
    }
    public String getLEAVEEARLYNUM() {
        return LEAVEEARLYNUM;
    }
    public void setLEAVEEARLYNUM(String LEAVEEARLYNUM) {
        this.LEAVEEARLYNUM = LEAVEEARLYNUM;
    }
    public String getABSENTEEISMNUM() {
        return ABSENTEEISMNUM;
    }
    public void setABSENTEEISMNUM(String ABSENTEEISMNUM) {
        this.ABSENTEEISMNUM = ABSENTEEISMNUM;
    }
    public String getBELATEANDLEAVEEARLYNUM() {
        return BELATEANDLEAVEEARLYNUM;
    }
    public void setBELATEANDLEAVEEARLYNUM(String BELATEANDLEAVEEARLYNUM) {
        this.BELATEANDLEAVEEARLYNUM = BELATEANDLEAVEEARLYNUM;
    }
    public String getSIGNDAYSNUM() {
        return SIGNDAYSNUM;
    }
    public void setSIGNDAYSNUM(String SIGNDAYSNUM) {
        this.SIGNDAYSNUM = SIGNDAYSNUM;
    }
    public String getPUNCHCARDTYPENUM() {
        return PUNCHCARDTYPENUM;
    }
    public void setPUNCHCARDTYPENUM(String PUNCHCARDTYPENUM) {
        this.PUNCHCARDTYPENUM = PUNCHCARDTYPENUM;
    }
    public String getOUTPUNCHCARDTYPENUM() {
        return OUTPUNCHCARDTYPENUM;
    }
    public void setOUTPUNCHCARDTYPENUM(String OUTPUNCHCARDTYPENUM) {
        this.OUTPUNCHCARDTYPENUM = OUTPUNCHCARDTYPENUM;
    }
    public Date getDepSignTime() {
        return depSignTime;
    }
    public void setDepSignTime(Date depSignTime) {
        this.depSignTime = depSignTime;
    }
    public Date getDepSignOutTime() {
        return depSignOutTime;
    }
    public void setDepSignOutTime(Date depSignOutTime) {
        this.depSignOutTime = depSignOutTime;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getWorkType() {
        return workType;
    }
    public void setWorkType(String workType) {
        this.workType = workType;
    }
    public String getOutWorkType() {
        return outWorkType;
    }
    public void setOutWorkType(String outWorkType) {
        this.outWorkType = outWorkType;
    }
}
