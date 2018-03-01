package cn.damei.entity.employee;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import java.util.Date;
public class EmployeeWork extends IdEntity {
    private Long empId;
    @JsonFormat(pattern = "yyyy-MM")
    private Date startDate;
    @JsonFormat(pattern = "yyyy-MM")
    private Date endDate;
    private String companyName;
    private String position;
    private String duty;
    private String certifierName;
    private String certifierPhone;
    private static final long serialVersionUID = 1L;
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    public Date getStartDate() {
        return startDate;
    }
    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }
    public Date getEndDate() {
        return endDate;
    }
    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getPosition() {
        return position;
    }
    public void setPosition(String position) {
        this.position = position;
    }
    public String getDuty() {
        return duty;
    }
    public void setDuty(String duty) {
        this.duty = duty;
    }
    public String getCertifierName() {
        return certifierName;
    }
    public void setCertifierName(String certifierName) {
        this.certifierName = certifierName;
    }
    public String getCertifierPhone() {
        return certifierPhone;
    }
    public void setCertifierPhone(String certifierPhone) {
        this.certifierPhone = certifierPhone;
    }
}