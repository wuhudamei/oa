package cn.damei.entity.materialSupplierAccountChecking;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class StOrdCapitalBudget extends IdEntity {
    private String orderNo;
    private String customerName;
    private String customerMoble;
    private String houseSpace;
    private String houseAddress;
    private Date predictStartTime;
    private Date predictEndTime;
    private BigDecimal contractTotalMoney;
    private BigDecimal dismantleAlterMoney;
    private BigDecimal downPaymentMoney;
    private BigDecimal theSecondPhaseMoney;
    private BigDecimal threeStagesMoney;
    private BigDecimal finalPaymentMoney;
    private BigDecimal predictReceiptMoney;
    private BigDecimal predictTheHighestCost;
    private BigDecimal dismantleAlterCost;
    private BigDecimal materialsExpenses;
    private BigDecimal constructionFee;
    private BigDecimal indirectCharges;
    private BigDecimal predictMinGrossProfitRate;
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
    public String getCustomerMoble() {
        return customerMoble;
    }
    public void setCustomerMoble(String customerMoble) {
        this.customerMoble = customerMoble;
    }
    public String getHouseSpace() {
        return houseSpace;
    }
    public void setHouseSpace(String houseSpace) {
        this.houseSpace = houseSpace;
    }
    public String getHouseAddress() {
        return houseAddress;
    }
    public void setHouseAddress(String houseAddress) {
        this.houseAddress = houseAddress;
    }
    public Date getPredictStartTime() {
        return predictStartTime;
    }
    public void setPredictStartTime(Date predictStartTime) {
        this.predictStartTime = predictStartTime;
    }
    public Date getPredictEndTime() {
        return predictEndTime;
    }
    public void setPredictEndTime(Date predictEndTime) {
        this.predictEndTime = predictEndTime;
    }
    public BigDecimal getContractTotalMoney() {
        return contractTotalMoney;
    }
    public void setContractTotalMoney(BigDecimal contractTotalMoney) {
        this.contractTotalMoney = contractTotalMoney;
    }
    public BigDecimal getDismantleAlterMoney() {
        return dismantleAlterMoney;
    }
    public void setDismantleAlterMoney(BigDecimal dismantleAlterMoney) {
        this.dismantleAlterMoney = dismantleAlterMoney;
    }
    public BigDecimal getDownPaymentMoney() {
        return downPaymentMoney;
    }
    public void setDownPaymentMoney(BigDecimal downPaymentMoney) {
        this.downPaymentMoney = downPaymentMoney;
    }
    public BigDecimal getTheSecondPhaseMoney() {
        return theSecondPhaseMoney;
    }
    public void setTheSecondPhaseMoney(BigDecimal theSecondPhaseMoney) {
        this.theSecondPhaseMoney = theSecondPhaseMoney;
    }
    public BigDecimal getThreeStagesMoney() {
        return threeStagesMoney;
    }
    public void setThreeStagesMoney(BigDecimal threeStagesMoney) {
        this.threeStagesMoney = threeStagesMoney;
    }
    public BigDecimal getFinalPaymentMoney() {
        return finalPaymentMoney;
    }
    public void setFinalPaymentMoney(BigDecimal finalPaymentMoney) {
        this.finalPaymentMoney = finalPaymentMoney;
    }
    public BigDecimal getPredictReceiptMoney() {
        return predictReceiptMoney;
    }
    public void setPredictReceiptMoney(BigDecimal predictReceiptMoney) {
        this.predictReceiptMoney = predictReceiptMoney;
    }
    public BigDecimal getPredictTheHighestCost() {
        return predictTheHighestCost;
    }
    public void setPredictTheHighestCost(BigDecimal predictTheHighestCost) {
        this.predictTheHighestCost = predictTheHighestCost;
    }
    public BigDecimal getDismantleAlterCost() {
        return dismantleAlterCost;
    }
    public void setDismantleAlterCost(BigDecimal dismantleAlterCost) {
        this.dismantleAlterCost = dismantleAlterCost;
    }
    public BigDecimal getMaterialsExpenses() {
        return materialsExpenses;
    }
    public void setMaterialsExpenses(BigDecimal materialsExpenses) {
        this.materialsExpenses = materialsExpenses;
    }
    public BigDecimal getConstructionFee() {
        return constructionFee;
    }
    public void setConstructionFee(BigDecimal constructionFee) {
        this.constructionFee = constructionFee;
    }
    public BigDecimal getIndirectCharges() {
        return indirectCharges;
    }
    public void setIndirectCharges(BigDecimal indirectCharges) {
        this.indirectCharges = indirectCharges;
    }
    public BigDecimal getPredictMinGrossProfitRate() {
        return predictMinGrossProfitRate;
    }
    public void setPredictMinGrossProfitRate(BigDecimal predictMinGrossProfitRate) {
        this.predictMinGrossProfitRate = predictMinGrossProfitRate;
    }
}
