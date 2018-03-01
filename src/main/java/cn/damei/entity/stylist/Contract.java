package cn.damei.entity.stylist;
import org.springframework.data.annotation.Transient;
import cn.damei.entity.IdEntity;
import java.io.Serializable;
public class Contract extends IdEntity implements Serializable {
    private static final long serialVersionUID = -7033903083845050262L;
    private Long empId;
    private String orderId;
    private String orderNo;
    @Transient
    private String mobile;
    private String customerName;
    private String name;
    private String contractNo;
    private Double money;
    private Double taxesMoney;
    private Double managerMoney;
    private Double designMoney;
    private Double remoteMoney;
    private Double othersMoney;
    private Double privilegeMoney;
    private Double modifyMoney;
    private ContractStatus status;
    public Contract(){}
    public Contract(Long empId, String customerName, String name, String contractNo, Double money, Double taxesMoney, Double managerMoney,ContractStatus status) {
        this.empId = empId;
        this.customerName = customerName;
        this.name = name;
        this.contractNo = contractNo;
        this.money = money;
        this.taxesMoney = taxesMoney;
        this.managerMoney = managerMoney;
        this.status = status;
    }
    public String getOrderId() {
        return orderId;
    }
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Long getEmpId() {
        return empId;
    }
    public void setEmpId(Long empId) {
        this.empId = empId;
    }
    public ContractStatus getStatus() {
        return status;
    }
    public void setStatus(ContractStatus status) {
        this.status = status;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getContractNo() {
        return contractNo;
    }
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }
    public Double getMoney() {
        return money;
    }
    public void setMoney(Double money) {
        this.money = money;
    }
    public Double getTaxesMoney() {
        return taxesMoney;
    }
    public void setTaxesMoney(Double taxesMoney) {
        this.taxesMoney = taxesMoney;
    }
    public Double getManagerMoney() {
        return managerMoney;
    }
    public void setManagerMoney(Double managerMoney) {
        this.managerMoney = managerMoney;
    }
    public Double getDesignMoney() {
        return designMoney;
    }
    public void setDesignMoney(Double designMoney) {
        this.designMoney = designMoney;
    }
    public Double getRemoteMoney() {
        return remoteMoney;
    }
    public void setRemoteMoney(Double remoteMoney) {
        this.remoteMoney = remoteMoney;
    }
    public Double getOthersMoney() {
        return othersMoney;
    }
    public void setOthersMoney(Double othersMoney) {
        this.othersMoney = othersMoney;
    }
    public Double getPrivilegeMoney() {
        return privilegeMoney;
    }
    public void setPrivilegeMoney(Double privilegeMoney) {
        this.privilegeMoney = privilegeMoney;
    }
    public Double getModifyMoney() {
        return modifyMoney;
    }
    public void setModifyMoney(Double modifyMoney) {
        this.modifyMoney = modifyMoney;
    }
}