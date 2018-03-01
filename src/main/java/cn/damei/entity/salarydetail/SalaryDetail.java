package cn.damei.entity.salarydetail;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import org.apache.commons.lang3.StringUtils;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Date;
public class SalaryDetail extends IdEntity {
    private Long oaEmployeeOrgId;//员工与部门关联表id
    private String creditCardNumbers;//银行卡号
    private String bank;//银行
    private String bankOfDeposit;//开户行
    private String salaryBasic;//基本工资
    private String salaryBasicDn;//解密基本工资
    private String salaryPerformance;//绩效工资
    private String salaryPerformanceDn;//解密绩效工资
    private String salaryBasicTotal;//基本工资合计
    private String salaryBasicTotalDn;//解密基本工资合计
    private String deductionWage;//提成金额
    private String deductionWageDn;//解密提成金额
    private String salaryTotal;//工资合计
    private String salaryTotalDn;//解密工资合计
    private Double shouldWorkDays;//应出勤天数
    private Double practicalWorkDays;//实际出勤天数
    private Double workCoefficient;//出勤系数
    private String sickLeaveSalary;//病假工资
    private String sickLeaveSalaryDn;//解密病假工资
    private String mealSubsidy;//餐补
    private String mealSubsidyDn;//解密餐补
    private String otherSubsidy;//其他补助
    private String otherSubsidyDn;//解密其他补助
    private String trafficSubsidy;//交通补助
    private String trafficSubsidyDn;//解密交通补助
    private String compensation;//补偿金
    private String compensationDn;//解密补偿金
    private String shouldBasicSalary;//应发基本工资
    private String shouldBasicSalaryDn;//解密应发基本工资
    private String shouldSalaryTotal;//应发工资总额
    private String shouldSalaryTotalDn;//解密应发工资总额
    private String practicalSalaryTotal;//实发工资总额
    private String practicalSalaryTotalDn;//解密实发工资总额
    private String salaryTaxable;//应税工资
    private String salaryTaxableDn;//解密应税工资
    private String endowmentInsurance;//养老保险
    private String endowmentInsuranceDn;//解密养老保险
    private String employmentInjuryInsurance;//工伤保险
    private String employmentInjuryInsuranceDn;//解密工伤保险
    private String medicare;//医疗保险
    private String medicareDn;//解密医疗保险
    private String unemploymentInsurance;//失业保险
    private String unemploymentInsuranceDn;//解密失业保险
    private String birthInsurance;//生育保险
    private String birthInsuranceDn;//解密生育保险
    private String housingFund;//住房公积金
    private String housingFundDn;//解密住房公积金
    private String socialSecurityPersonal;//社保个人扣款
    private String socialSecurityPersonalDn;//解密社保个人扣款
    private String deductMoney;//扣款
    private String deductMoneyDn;//解密扣款
    private String individualIncomeTax;//个税
    private String individualIncomeTaxDn;//解密个税
    private String socialSecurityCompany;//社保公司扣款合计
    private String socialSecurityCompanyDn;//解密社保公司扣款合计
    private Date effectiveDate;//工资生效日期
    private Date expiryDate;//工资终止日期
    private Long updateUser;//更改人
    private Date updateDate;//更改时间
    @JsonFormat(pattern = "yyyy-MM", timezone="GMT+8")
    private Date monthSalary;//按月份查询工资
    private String empName;//姓名
    private String orgCode;//工号
    private String orgName;//部门
    private String position;//岗位
    private String censusNature;//户口性质
    public String getOrgName() {
        return orgName;
    }
    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getCensusNature() {
        return censusNature;
    }
    public void setCensusNature(String censusNature) {
        this.censusNature = censusNature;
    }
    public String getIdNum() {
        return idNum;
    }
    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }
    private String orgDepartmentName;//部门名称
    private String orgCompanyName;//公司名称
    private String idNum;//身份证号
    private Long empId;//员工id
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    public Long getOaEmployeeOrgId() {
        return oaEmployeeOrgId;
    }
    public void setOaEmployeeOrgId(Long oaEmployeeOrgId) {
        this.oaEmployeeOrgId = oaEmployeeOrgId;
    }
    public String getCreditCardNumbers() {
        return creditCardNumbers;
    }
    public void setCreditCardNumbers(String creditCardNumbers) {
        this.creditCardNumbers = creditCardNumbers;
    }
    public String getBank() {
        return bank;
    }
    public void setBank(String bank) {
        this.bank = bank;
    }
    public String getBankOfDeposit() {
        return bankOfDeposit;
    }
    public void setBankOfDeposit(String bankOfDeposit) {
        this.bankOfDeposit = bankOfDeposit;
    }
    public Double getShouldWorkDays() {
        return shouldWorkDays;
    }
    public void setShouldWorkDays(Double shouldWorkDays) {
        this.shouldWorkDays = shouldWorkDays;
    }
    public Double getPracticalWorkDays() {
        return practicalWorkDays;
    }
    public void setPracticalWorkDays(Double practicalWorkDays) {
        this.practicalWorkDays = practicalWorkDays;
    }
    public Double getWorkCoefficient() {
        return workCoefficient;
    }
    public void setWorkCoefficient(Double workCoefficient) {
        this.workCoefficient = workCoefficient;
    }
    public Date getEffectiveDate() {
        return effectiveDate;
    }
    public void setEffectiveDate(Date effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    public Date getExpiryDate() {
        return expiryDate;
    }
    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }
    public Long getUpdateUser() {
        return updateUser;
    }
    public void setUpdateUser(Long updateUser) {
        this.updateUser = updateUser;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    public Date getMonthSalary() {
        return monthSalary;
    }
    public void setMonthSalary(Date monthSalary) {
        this.monthSalary = monthSalary;
    }
    public String getEmpName() {
        return empName;
    }
    public void setEmpName(String empName) {
        this.empName = empName;
    }
    public String getOrgCode() {
        return orgCode;
    }
    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }
    public String getSalaryBasic() {
        return salaryBasic;
    }
    public void setSalaryBasic(String salaryBasic) {
        this.salaryBasic = AESEncode("MDNISALARY",salaryBasic);
    }
    public String getSalaryBasicDn() {
        return salaryBasicDn;
    }
    public void setSalaryBasicDn(String salaryBasicDn) {
        this.salaryBasicDn = AESDncode("MDNISALARY",salaryBasicDn);
    }
    public String getSalaryPerformance() {
        return salaryPerformance;
    }
    public void setSalaryPerformance(String salaryPerformance) {
        this.salaryPerformance = AESEncode("MDNISALARY",salaryPerformance);
    }
    public String getSalaryPerformanceDn() {
        return salaryPerformanceDn;
    }
    public void setSalaryPerformanceDn(String salaryPerformanceDn) {
        this.salaryPerformanceDn = AESDncode("MDNISALARY",salaryPerformanceDn);
    }
    public String getSalaryBasicTotal() {
        return salaryBasicTotal;
    }
    public void setSalaryBasicTotal(String salaryBasicTotal) {
        this.salaryBasicTotal = AESEncode("MDNISALARY",salaryBasicTotal);
    }
    public String getSalaryBasicTotalDn() {
        return salaryBasicTotalDn;
    }
    public void setSalaryBasicTotalDn(String salaryBasicTotalDn) {
        this.salaryBasicTotalDn = AESDncode("MDNISALARY",salaryBasicTotalDn);
    }
    public String getDeductionWage() {
        return deductionWage;
    }
    public void setDeductionWage(String deductionWage) {
        this.deductionWage = AESEncode("MDNISALARY",deductionWage);
    }
    public String getDeductionWageDn() {
        return deductionWageDn;
    }
    public void setDeductionWageDn(String deductionWageDn) {
        this.deductionWageDn = AESDncode("MDNISALARY",deductionWageDn);
    }
    public String getSalaryTotal() {
        return salaryTotal;
    }
    public void setSalaryTotal(String salaryTotal) {
        this.salaryTotal = AESEncode("MDNISALARY",salaryTotal);
    }
    public String getSalaryTotalDn() {
        return salaryTotalDn;
    }
    public void setSalaryTotalDn(String salaryTotalDn) {
        this.salaryTotalDn = AESDncode("MDNISALARY",salaryTotalDn);
    }
    public String getSickLeaveSalary() {
        return sickLeaveSalary;
    }
    public void setSickLeaveSalary(String sickLeaveSalary) {
        this.sickLeaveSalary = AESEncode("MDNISALARY",sickLeaveSalary);
    }
    public String getSickLeaveSalaryDn() {
        return sickLeaveSalaryDn;
    }
    public void setSickLeaveSalaryDn(String sickLeaveSalaryDn) {
        this.sickLeaveSalaryDn = AESDncode("MDNISALARY",sickLeaveSalaryDn);
    }
    public String getMealSubsidy() {
        return mealSubsidy;
    }
    public void setMealSubsidy(String mealSubsidy) {
        this.mealSubsidy = AESEncode("MDNISALARY",mealSubsidy);
    }
    public String getMealSubsidyDn() {
        return mealSubsidyDn;
    }
    public void setMealSubsidyDn(String mealSubsidyDn) {
        this.mealSubsidyDn = AESDncode("MDNISALARY",mealSubsidyDn);
    }
    public String getOtherSubsidy() {
        return otherSubsidy;
    }
    public void setOtherSubsidy(String otherSubsidy) {
        this.otherSubsidy = AESEncode("MDNISALARY",otherSubsidy);
    }
    public String getOtherSubsidyDn() {
        return otherSubsidyDn;
    }
    public void setOtherSubsidyDn(String otherSubsidyDn) {
        this.otherSubsidyDn = AESDncode("MDNISALARY",otherSubsidyDn);
    }
    public String getTrafficSubsidy() {
        return trafficSubsidy;
    }
    public void setTrafficSubsidy(String trafficSubsidy) {
        this.trafficSubsidy = AESEncode("MDNISALARY",trafficSubsidy);
    }
    public String getTrafficSubsidyDn() {
        return trafficSubsidyDn;
    }
    public void setTrafficSubsidyDn(String trafficSubsidyDn) {
        this.trafficSubsidyDn =  AESDncode("MDNISALARY",trafficSubsidyDn);
    }
    public String getCompensation() {
        return compensation;
    }
    public void setCompensation(String compensation) {
        this.compensation = AESEncode("MDNISALARY",compensation);
    }
    public String getCompensationDn() {
        return compensationDn;
    }
    public void setCompensationDn(String compensationDn) {
        this.compensationDn = AESDncode("MDNISALARY",compensationDn);
    }
    public String getShouldBasicSalary() {
        return shouldBasicSalary;
    }
    public void setShouldBasicSalary(String shouldBasicSalary) {
        this.shouldBasicSalary = AESEncode("MDNISALARY",shouldBasicSalary);
    }
    public String getShouldBasicSalaryDn() {
        return shouldBasicSalaryDn;
    }
    public void setShouldBasicSalaryDn(String shouldBasicSalaryDn) {
        this.shouldBasicSalaryDn =  AESDncode("MDNISALARY",shouldBasicSalaryDn);
    }
    public String getShouldSalaryTotal() {
        return shouldSalaryTotal;
    }
    public void setShouldSalaryTotal(String shouldSalaryTotal) {
        this.shouldSalaryTotal = AESEncode("MDNISALARY",shouldSalaryTotal);
    }
    public String getShouldSalaryTotalDn() {
        return shouldSalaryTotalDn;
    }
    public void setShouldSalaryTotalDn(String shouldSalaryTotalDn) {
        this.shouldSalaryTotalDn = AESDncode("MDNISALARY",shouldSalaryTotalDn);
    }
    public String getPracticalSalaryTotal() {
        return practicalSalaryTotal;
    }
    public void setPracticalSalaryTotal(String practicalSalaryTotal) {
        this.practicalSalaryTotal = AESEncode("MDNISALARY",practicalSalaryTotal);
    }
    public String getPracticalSalaryTotalDn() {
        return practicalSalaryTotalDn;
    }
    public void setPracticalSalaryTotalDn(String practicalSalaryTotalDn) {
        this.practicalSalaryTotalDn = AESDncode("MDNISALARY",practicalSalaryTotalDn);
    }
    public String getSalaryTaxable() {
        return salaryTaxable;
    }
    public void setSalaryTaxable(String salaryTaxable) {
        this.salaryTaxable = AESEncode("MDNISALARY",salaryTaxable);
    }
    public String getSalaryTaxableDn() {
        return salaryTaxableDn;
    }
    public void setSalaryTaxableDn(String salaryTaxableDn) {
        this.salaryTaxableDn = AESDncode("MDNISALARY",salaryTaxableDn);
    }
    public String getEndowmentInsurance() {
        return endowmentInsurance;
    }
    public void setEndowmentInsurance(String endowmentInsurance) {
        this.endowmentInsurance = AESEncode("MDNISALARY",endowmentInsurance);
    }
    public String getEndowmentInsuranceDn() {
        return endowmentInsuranceDn;
    }
    public void setEndowmentInsuranceDn(String endowmentInsuranceDn) {
        this.endowmentInsuranceDn = AESDncode("MDNISALARY",endowmentInsuranceDn);
    }
    public String getEmploymentInjuryInsurance() {
        return employmentInjuryInsurance;
    }
    public void setEmploymentInjuryInsurance(String employmentInjuryInsurance) {
        this.employmentInjuryInsurance = AESEncode("MDNISALARY",employmentInjuryInsurance);
    }
    public String getEmploymentInjuryInsuranceDn() {
        return employmentInjuryInsuranceDn;
    }
    public void setEmploymentInjuryInsuranceDn(String employmentInjuryInsuranceDn) {
        this.employmentInjuryInsuranceDn = AESDncode("MDNISALARY",employmentInjuryInsuranceDn);
    }
    public String getMedicare() {
        return medicare;
    }
    public void setMedicare(String medicare) {
        this.medicare = AESEncode("MDNISALARY",medicare);
    }
    public String getMedicareDn() {
        return medicareDn;
    }
    public void setMedicareDn(String medicareDn) {
        this.medicareDn = AESDncode("MDNISALARY",medicareDn);
    }
    public String getUnemploymentInsurance() {
        return unemploymentInsurance;
    }
    public void setUnemploymentInsurance(String unemploymentInsurance) {
        this.unemploymentInsurance = AESEncode("MDNISALARY",unemploymentInsurance);
    }
    public String getUnemploymentInsuranceDn() {
        return unemploymentInsuranceDn;
    }
    public void setUnemploymentInsuranceDn(String unemploymentInsuranceDn) {
        this.unemploymentInsuranceDn = AESDncode("MDNISALARY",unemploymentInsuranceDn);
    }
    public String getBirthInsurance() {
        return birthInsurance;
    }
    public void setBirthInsurance(String birthInsurance) {
        this.birthInsurance = AESEncode("MDNISALARY",birthInsurance);
    }
    public String getBirthInsuranceDn() {
        return birthInsuranceDn;
    }
    public void setBirthInsuranceDn(String birthInsuranceDn) {
        this.birthInsuranceDn = AESDncode("MDNISALARY",birthInsuranceDn);
    }
    public String getHousingFund() {
        return housingFund;
    }
    public void setHousingFund(String housingFund) {
        this.housingFund = AESEncode("MDNISALARY",housingFund);
    }
    public String getHousingFundDn() {
        return housingFundDn;
    }
    public void setHousingFundDn(String housingFundDn) {
        this.housingFundDn = AESDncode("MDNISALARY",housingFundDn);
    }
    public String getSocialSecurityPersonal() {
        return socialSecurityPersonal;
    }
    public void setSocialSecurityPersonal(String socialSecurityPersonal) {
        this.socialSecurityPersonal = AESEncode("MDNISALARY",socialSecurityPersonal);
    }
    public String getSocialSecurityPersonalDn() {
        return socialSecurityPersonalDn;
    }
    public void setSocialSecurityPersonalDn(String socialSecurityPersonalDn) {
        this.socialSecurityPersonalDn = AESDncode("MDNISALARY",socialSecurityPersonalDn);
    }
    public String getDeductMoney() {
        return deductMoney;
    }
    public void setDeductMoney(String deductMoney) {
        this.deductMoney = AESEncode("MDNISALARY",deductMoney);
    }
    public String getDeductMoneyDn() {
        return deductMoneyDn;
    }
    public void setDeductMoneyDn(String deductMoneyDn) {
        this.deductMoneyDn = AESDncode("MDNISALARY",deductMoneyDn);
    }
    public String getIndividualIncomeTax() {
        return individualIncomeTax;
    }
    public void setIndividualIncomeTax(String individualIncomeTax) {
        this.individualIncomeTax = AESEncode("MDNISALARY",individualIncomeTax);
    }
    public String getIndividualIncomeTaxDn() {
        return individualIncomeTaxDn;
    }
    public void setIndividualIncomeTaxDn(String individualIncomeTaxDn) {
        this.individualIncomeTaxDn = AESDncode("MDNISALARY",individualIncomeTaxDn);
    }
    public String getSocialSecurityCompany() {
        return socialSecurityCompany;
    }
    public void setSocialSecurityCompany(String socialSecurityCompany) {
        this.socialSecurityCompany = AESEncode("MDNISALARY",socialSecurityCompany);
    }
    public String getSocialSecurityCompanyDn() {
        return socialSecurityCompanyDn;
    }
    public void setSocialSecurityCompanyDn(String socialSecurityCompanyDn) {
        this.socialSecurityCompanyDn = AESDncode("MDNISALARY",socialSecurityCompanyDn);
    }
    public String getOrgDepartmentName() {
        return orgDepartmentName;
    }
    public void setOrgDepartmentName(String orgDepartmentName) {
        this.orgDepartmentName = orgDepartmentName;
    }
    public String getOrgCompanyName() {
        return orgCompanyName;
    }
    public void setOrgCompanyName(String orgCompanyName) {
        this.orgCompanyName = orgCompanyName;
    }
    public static String AESEncode(String encodeRules,String data){
        try {
            KeyGenerator keygen= KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte [] byte_encode = null;
            if (StringUtils.isNotBlank(data)) {
                byte_encode=data.getBytes("utf-8");
                byte [] byte_AES=cipher.doFinal(byte_encode);
                String AES_encode=new String(new BASE64Encoder().encode(byte_AES));
                return AES_encode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String AESDncode(String encodeRules,String data){
        try {
            KeyGenerator keygen=KeyGenerator.getInstance("AES");
            keygen.init(128, new SecureRandom(encodeRules.getBytes()));
            SecretKey original_key=keygen.generateKey();
            byte [] raw=original_key.getEncoded();
            SecretKey key=new SecretKeySpec(raw, "AES");
            Cipher cipher=Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte [] byte_content = null;
            if (StringUtils.isNotBlank(data)) {
                byte_content= new BASE64Decoder().decodeBuffer(data);
                byte [] byte_decode=cipher.doFinal(byte_content);
                String AES_decode=new String(byte_decode,"utf-8");
                return AES_decode;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
