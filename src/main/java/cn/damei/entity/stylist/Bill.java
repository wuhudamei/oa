package cn.damei.entity.stylist;
import org.springframework.data.annotation.Transient;
import cn.damei.entity.IdEntity;
import cn.damei.entity.stylist.MonthBill.Status;
import java.io.Serializable;
import java.util.List;
public class Bill extends IdEntity implements Serializable {
    private static final long serialVersionUID = -7418570669420274035L;
    private Long monthBillId;
    private String name;
    private String jobNo;
    private String mobile;
    private String billMonth;
    private Double totalMoney;
    private Status status;
    @Transient
    private List<BillDetails> billDetails;
    public Bill() {
    }
    public Bill(String name, String jobNo, String mobile, String billMonth, Double totalMoney, Status status) {
        this.name = name;
        this.jobNo = jobNo;
        this.mobile = mobile;
        this.billMonth = billMonth;
        this.totalMoney = totalMoney;
        this.status = status;
    }
    public List<BillDetails> getBillDetails() {
        return billDetails;
    }
    public void setBillDetails(List<BillDetails> billDetails) {
        this.billDetails = billDetails;
    }
    public Long getMonthBillId() {
        return monthBillId;
    }
    public void setMonthBillId(Long monthBillId) {
        this.monthBillId = monthBillId;
    }
    public String getName() {
        return name;
    }
    public Bill setName(String name) {
        this.name = name;
        return this;
    }
    public String getJobNo() {
        return jobNo;
    }
    public Bill setJobNo(String jobNo) {
        this.jobNo = jobNo;
        return this;
    }
    public String getMobile() {
        return mobile;
    }
    public Bill setMobile(String mobile) {
        this.mobile = mobile;
        return this;
    }
    public String getBillMonth() {
        return billMonth;
    }
    public Bill setBillMonth(String billMonth) {
        this.billMonth = billMonth;
        return this;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public Bill setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }
    public Status getStatus() {
        return status;
    }
    public Bill setStatus(Status status) {
        this.status = status;
        return this;
    }
}