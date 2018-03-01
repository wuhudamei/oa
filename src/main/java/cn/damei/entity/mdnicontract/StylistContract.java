package cn.damei.entity.mdnicontract;
import java.io.Serializable;
import java.util.Date;
public class StylistContract implements Serializable {
    public static final Integer PAYED = 1;
    private static final long serialVersionUID = 6149655784733814909L;
    private String stylistName;
    private String stylistMobile;
    private String orderId;
    private String orderNo;
    private String customerName;
    private String contractName;
    private String contractNo;
    private Double totalMoney;
    private Double taxesMoney;
    private Double managerMoney;
    private Date actualStartTime;
    private Integer firstAmountPayed;
    private Double firstAmount;
    private Date firstAmountTime;
    private Integer mediumAmountPayed;
    private Double mediumAmount;
    private Date mediumAmountTime;
    private Integer finalAmountPayed;
    private Double finalAmount;
    private Date finalAmountTime;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getOrderId() {
        return orderId;
    }
    public StylistContract setOrderId(String orderId) {
        this.orderId = orderId;
        return this;
    }
    public String getStylistName() {
        return stylistName;
    }
    public StylistContract setStylistName(String stylistName) {
        this.stylistName = stylistName;
        return this;
    }
    public String getStylistMobile() {
        return stylistMobile;
    }
    public StylistContract setStylistMobile(String stylistMobile) {
        this.stylistMobile = stylistMobile;
        return this;
    }
    public String getCustomerName() {
        return customerName;
    }
    public StylistContract setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }
    public String getContractName() {
        return contractName;
    }
    public StylistContract setContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }
    public String getContractNo() {
        return contractNo;
    }
    public StylistContract setContractNo(String contractNo) {
        this.contractNo = contractNo;
        return this;
    }
    public Double getTotalMoney() {
        return totalMoney;
    }
    public StylistContract setTotalMoney(Double totalMoney) {
        this.totalMoney = totalMoney;
        return this;
    }
    public Double getTaxesMoney() {
        return taxesMoney;
    }
    public StylistContract setTaxesMoney(Double taxesMoney) {
        this.taxesMoney = taxesMoney;
        return this;
    }
    public Double getManagerMoney() {
        return managerMoney;
    }
    public StylistContract setManagerMoney(Double managerMoney) {
        this.managerMoney = managerMoney;
        return this;
    }
    public Date getActualStartTime() {
        return actualStartTime;
    }
    public StylistContract setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
        return this;
    }
    public Integer getFirstAmountPayed() {
        return firstAmountPayed;
    }
    public StylistContract setFirstAmountPayed(Integer firstAmountPayed) {
        this.firstAmountPayed = firstAmountPayed;
        return this;
    }
    public Double getFirstAmount() {
        return firstAmount;
    }
    public StylistContract setFirstAmount(Double firstAmount) {
        this.firstAmount = firstAmount;
        return this;
    }
    public Date getFirstAmountTime() {
        return firstAmountTime;
    }
    public StylistContract setFirstAmountTime(Date firstAmountTime) {
        this.firstAmountTime = firstAmountTime;
        return this;
    }
    public Integer getMediumAmountPayed() {
        return mediumAmountPayed;
    }
    public StylistContract setMediumAmountPayed(Integer mediumAmountPayed) {
        this.mediumAmountPayed = mediumAmountPayed;
        return this;
    }
    public Double getMediumAmount() {
        return mediumAmount;
    }
    public StylistContract setMediumAmount(Double mediumAmount) {
        this.mediumAmount = mediumAmount;
        return this;
    }
    public Date getMediumAmountTime() {
        return mediumAmountTime;
    }
    public StylistContract setMediumAmountTime(Date mediumAmountTime) {
        this.mediumAmountTime = mediumAmountTime;
        return this;
    }
    public Integer getFinalAmountPayed() {
        return finalAmountPayed;
    }
    public StylistContract setFinalAmountPayed(Integer finalAmountPayed) {
        this.finalAmountPayed = finalAmountPayed;
        return this;
    }
    public Double getFinalAmount() {
        return finalAmount;
    }
    public StylistContract setFinalAmount(Double finalAmount) {
        this.finalAmount = finalAmount;
        return this;
    }
    public Date getFinalAmountTime() {
        return finalAmountTime;
    }
    public StylistContract setFinalAmountTime(Date finalAmountTime) {
        this.finalAmountTime = finalAmountTime;
        return this;
    }
}
