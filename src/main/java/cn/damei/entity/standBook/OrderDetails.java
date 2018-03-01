package cn.damei.entity.standBook;
import java.math.BigDecimal;
import cn.damei.entity.IdEntity;
public class OrderDetails extends IdEntity {
    private String orderNo;
    private String customerName;
    private String customerId;
    private String mobile;
    private Integer allotState;
    private String comboType;
    private BigDecimal floorArea;
    private String houseAddress;
    private Boolean lift;
    private Boolean newHouse;
    private String secondLinkman;
    private String secondLinkmanMobile;
    private String housetype;
    private BigDecimal measurehousearea;
    private String contractStartTime;
    private String contractCompleteTime;
    private BigDecimal totalMoney;
    private BigDecimal changeMoney;
    private String allotGroupId;
    private String serviceName;
    private String stylistName;
    private BigDecimal unitprice;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getCustomerId() {
        return customerId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public Integer getAllotState() {
        return allotState;
    }
    public void setAllotState(Integer allotState) {
        this.allotState = allotState;
    }
    public String getComboType() {
        return comboType;
    }
    public void setComboType(String comboType) {
        this.comboType = comboType;
    }
    public BigDecimal getFloorArea() {
        return floorArea;
    }
    public void setFloorArea(BigDecimal floorArea) {
        this.floorArea = floorArea;
    }
    public String getHouseAddress() {
        return houseAddress;
    }
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
    public Boolean getLift() {
        return lift;
    }
    public void setLift(Boolean lift) {
        this.lift = lift;
    }
    public Boolean getNewHouse() {
        return newHouse;
    }
    public void setNewHouse(Boolean newHouse) {
        this.newHouse = newHouse;
    }
    public String getSecondLinkman() {
        return secondLinkman;
    }
    public void setSecondLinkman(String secondLinkman) {
        this.secondLinkman = secondLinkman;
    }
    public String getSecondLinkmanMobile() {
        return secondLinkmanMobile;
    }
    public void setSecondLinkmanMobile(String secondLinkmanMobile) {
        this.secondLinkmanMobile = secondLinkmanMobile;
    }
    public String getHousetype() {
        return housetype;
    }
    public void setHousetype(String housetype) {
        this.housetype = housetype;
    }
    public BigDecimal getMeasurehousearea() {
        return measurehousearea;
    }
    public void setMeasurehousearea(BigDecimal measurehousearea) {
        this.measurehousearea = measurehousearea;
    }
    public String getContractStartTime() {
        return contractStartTime;
    }
    public void setContractStartTime(String contractStartTime) {
        this.contractStartTime = contractStartTime;
    }
    public String getContractCompleteTime() {
        return contractCompleteTime;
    }
    public void setContractCompleteTime(String contractCompleteTime) {
        this.contractCompleteTime = contractCompleteTime;
    }
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }
    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    public BigDecimal getChangeMoney() {
        return changeMoney;
    }
    public void setChangeMoney(BigDecimal changeMoney) {
        this.changeMoney = changeMoney;
    }
    public String getAllotGroupId() {
        return allotGroupId;
    }
    public void setAllotGroupId(String allotGroupId) {
        this.allotGroupId = allotGroupId;
    }
    public String getServiceName() {
        return serviceName;
    }
    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }
    public String getStylistName() {
        return stylistName;
    }
    public void setStylistName(String stylistName) {
        this.stylistName = stylistName;
    }
    public BigDecimal getUnitprice() {
        return unitprice;
    }
    public void setUnitprice(BigDecimal unitprice) {
        this.unitprice = unitprice;
    }
}
