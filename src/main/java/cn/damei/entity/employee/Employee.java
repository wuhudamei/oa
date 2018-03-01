package cn.damei.entity.employee;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.annotation.Transient;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.google.common.collect.Lists;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.Status;
import cn.damei.enumeration.employee.AccountType;
import cn.damei.enumeration.employee.CensusNature;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.enumeration.employee.EmployeeType;
import cn.damei.enumeration.employee.EnglishLevel;
import cn.damei.enumeration.employee.Gender;
import cn.damei.enumeration.employee.MaritalStatus;
public class Employee extends IdEntity {
    public Employee() {
    }
    public Employee(Long id) {
        this.id = id;
    }
    private String name;
    private String jobNum;
    private String depCode;
    private String orgCode;
    private String position;
    private String username;
    @Transient
    private EmployeeOrg org;
    @Transient
    private Long orgId;
    @Transient
    private Long parentId;
    private String principal;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private String salt;
    private Gender gender;
    private String nativePlace;
    private String idNum;
    private String censusAddress;
    private CensusNature censusNature;
    private String nation;
    private String politicsStatus;
    private MaritalStatus maritalStatus;
    private String education;
    private EnglishLevel englishLevel;
    private String title;
    private String familyAddress;
    private String presentAddress;
    private String type;
    @Transient
    private List<EmployeeType> types;
    private String email;
    private String mobile;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+08:00")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Date entryDate;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dimissionDate;
    private EmployeeStatus employeeStatus;
    private Status accountStatus;
    private AccountType accountType;
    private String accountSource;
    private String linkman1;
    private String linkphone1;
    private String linkman2;
    private String linkphone2;
    private Boolean origProve;
    private Boolean retireProve;
    private Boolean noProve;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private String photo;
    private Long createUser;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;
    private Long updateUser;
    private Boolean deleted;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;
    @Transient
    private List<EmployeeEdu> edus = new ArrayList<>();
    @Transient
    private List<EmployeeWork> works = new ArrayList<>();
    @Transient
    private String openid;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getJobNum() {
        return jobNum;
    }
    public void setJobNum(String jobNum) {
        this.jobNum = jobNum;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public Long getOrgId() {
        return orgId;
    }
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getSalt() {
        return salt;
    }
    public void setSalt(String salt) {
        this.salt = salt;
    }
    public Gender getGender() {
        return gender;
    }
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    public String getNativePlace() {
        return nativePlace;
    }
    public void setNativePlace(String nativePlace) {
        this.nativePlace = nativePlace;
    }
    public String getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    public String getCensusAddress() {
        return censusAddress;
    }
    public void setCensusAddress(String censusAddress) {
        this.censusAddress = censusAddress;
    }
    public CensusNature getCensusNature() {
        return censusNature;
    }
    public void setCensusNature(CensusNature censusNature) {
        this.censusNature = censusNature;
    }
    public String getNation() {
        return nation;
    }
    public void setNation(String nation) {
        this.nation = nation;
    }
    public String getPoliticsStatus() {
        return politicsStatus;
    }
    public void setPoliticsStatus(String politicsStatus) {
        this.politicsStatus = politicsStatus;
    }
    public MaritalStatus getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(MaritalStatus maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public String getEducation() {
        return education;
    }
    public void setEducation(String education) {
        this.education = education;
    }
    public EnglishLevel getEnglishLevel() {
        return englishLevel;
    }
    public void setEnglishLevel(EnglishLevel englishLevel) {
        this.englishLevel = englishLevel;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getFamilyAddress() {
        return familyAddress;
    }
    public void setFamilyAddress(String familyAddress) {
        this.familyAddress = familyAddress;
    }
    public String getPresentAddress() {
        return presentAddress;
    }
    public void setPresentAddress(String presentAddress) {
        this.presentAddress = presentAddress;
    }
    public String getType() {
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(types)) {
            int size = types.size();
            for (int i = 0; i < size; i++) {
                EmployeeType t = types.get(i);
                if (i != size - 1) {
                    sb.append(t.toString()).append(",");
                } else {
                    sb.append(t.toString());
                }
            }
        }
        return sb.toString();
    }
    public void setType(String type) {
        this.type = type;
    }
    public List<EmployeeType> getTypes() {
        List<EmployeeType> list = Lists.newArrayList();
        if (StringUtils.isNotBlank(type)) {
            String[] ts = type.split(",");
            for (String t : ts) {
                list.add(EmployeeType.valueOf(t));
            }
        }
        return list;
    }
    public void setTypes(List<EmployeeType> types) {
        this.types = types;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Date getEntryDate() {
        return entryDate;
    }
    public void setEntryDate(Date entryDate) {
        this.entryDate = entryDate;
    }
    public EmployeeStatus getEmployeeStatus() {
        return employeeStatus;
    }
    public void setEmployeeStatus(EmployeeStatus employeeStatus) {
        this.employeeStatus = employeeStatus;
    }
    public Status getAccountStatus() {
        return accountStatus;
    }
    public void setAccountStatus(Status accountStatus) {
        this.accountStatus = accountStatus;
    }
    public AccountType getAccountType() {
        return accountType;
    }
    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
    public String getLinkman1() {
        return linkman1;
    }
    public void setLinkman1(String linkman1) {
        this.linkman1 = linkman1;
    }
    public String getLinkphone1() {
        return linkphone1;
    }
    public void setLinkphone1(String linkphone1) {
        this.linkphone1 = linkphone1;
    }
    public String getLinkman2() {
        return linkman2;
    }
    public void setLinkman2(String linkman2) {
        this.linkman2 = linkman2;
    }
    public String getLinkphone2() {
        return linkphone2;
    }
    public void setLinkphone2(String linkphone2) {
        this.linkphone2 = linkphone2;
    }
    public Boolean isOrigProve() {
        return origProve;
    }
    public void setOrigProve(Boolean origProve) {
        this.origProve = origProve;
    }
    public Boolean isRetireProve() {
        return retireProve;
    }
    public void setRetireProve(Boolean retireProve) {
        this.retireProve = retireProve;
    }
    public Boolean isNoProve() {
        return noProve;
    }
    public void setNoProve(Boolean noProve) {
        this.noProve = noProve;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public String getPhoto() {
        return photo;
    }
    public void setPhoto(String photo) {
        this.photo = photo;
    }
    public Long getCreateUser() {
        return createUser;
    }
    public void setCreateUser(Long createUser) {
        this.createUser = createUser;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Long getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public List<EmployeeEdu> getEdus() {
        return edus;
    }
    public void setEdus(List<EmployeeEdu> edus) {
        this.edus = edus;
    }
    public List<EmployeeWork> getWorks() {
        return works;
    }
    public void setWorks(List<EmployeeWork> works) {
        this.works = works;
    }
    public Date getDimissionDate() {
        return dimissionDate;
    }
    public void setDimissionDate(Date dimissionDate) {
        this.dimissionDate = dimissionDate;
    }
    public Boolean isDeleted() {
        return deleted;
    }
    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }
    public Long getParentId() {
        return parentId;
    }
    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }
    public EmployeeOrg getOrg() {
        return org;
    }
    public void setOrg(EmployeeOrg org) {
        this.org = org;
    }
    public String getPrincipal() {
        return principal;
    }
    public void setPrincipal(String principal) {
        this.principal = principal;
    }
    public String getOpenid() {
        return openid;
    }
    public void setOpenid(String openid) {
        this.openid = openid;
    }
	public String getAccountSource() {
		return accountSource;
	}
	public void setAccountSource(String accountSource) {
		this.accountSource = accountSource;
	}
	public String getDepCode() {
		return depCode;
	}
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
}