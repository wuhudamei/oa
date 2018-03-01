package cn.damei.entity.standBook;
import java.math.BigDecimal;
import cn.damei.entity.IdEntity;
public class PaymentDetail extends IdEntity {
    private BigDecimal imprestAmount;
    private BigDecimal firstAmount;
    private BigDecimal mediumAmount;
    private BigDecimal finalAmount;
    private BigDecimal finishChangeAmount;
    private Boolean imprest;
    private BigDecimal modifyCost;
    private BigDecimal firstPayment;
    private BigDecimal mediumPayment;
    private BigDecimal EndPayment;
    private Boolean imfirst;
    private Boolean immedium;
    private Boolean imfinal;
    private BigDecimal totalImprestAmountDeductible;
    private String status;
    public Boolean getImprest() {
        return imprest;
    }
    public void setImprest(Boolean imprest) {
        this.imprest = imprest;
    }
    public BigDecimal getImprestAmount() {
        return imprestAmount;
    }
    public void setImprestAmount(BigDecimal imprestAmount) {
        this.imprestAmount = imprestAmount;
    }
    public BigDecimal getFirstAmount() {
        return firstAmount;
    }
    public void setFirstAmount(BigDecimal firstAmount) {
        this.firstAmount = firstAmount;
    }
    public BigDecimal getMediumAmount() {
        return mediumAmount;
    }
    public void setMediumAmount(BigDecimal mediumAmount) {
        this.mediumAmount = mediumAmount;
    }
    public BigDecimal getFinalAmount() {
        return finalAmount;
    }
    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }
    public BigDecimal getFinishChangeAmount() {
        return finishChangeAmount;
    }
    public void setFinishChangeAmount(BigDecimal finishChangeAmount) {
        this.finishChangeAmount = finishChangeAmount;
    }
    public BigDecimal getModifyCost() {
        return modifyCost;
    }
    public void setModifyCost(BigDecimal modifyCost) {
        this.modifyCost = modifyCost;
    }
    public BigDecimal getFirstPayment() {
        return firstPayment;
    }
    public void setFirstPayment(BigDecimal firstPayment) {
        this.firstPayment = firstPayment;
    }
    public BigDecimal getMediumPayment() {
        return mediumPayment;
    }
    public void setMediumPayment(BigDecimal mediumPayment) {
        this.mediumPayment = mediumPayment;
    }
    public BigDecimal getEndPayment() {
        return EndPayment;
    }
    public void setEndPayment(BigDecimal endPayment) {
        EndPayment = endPayment;
    }
    public Boolean getImfirst() {
        return imfirst;
    }
    public void setImfirst(Boolean imfirst) {
        this.imfirst = imfirst;
    }
    public Boolean getImmedium() {
        return immedium;
    }
    public void setImmedium(Boolean immedium) {
        this.immedium = immedium;
    }
    public Boolean getImfinal() {
        return imfinal;
    }
    public void setImfinal(Boolean imfinal) {
        this.imfinal = imfinal;
    }
    public BigDecimal getTotalImprestAmountDeductible() {
        return totalImprestAmountDeductible;
    }
    public void setTotalImprestAmountDeductible(BigDecimal totalImprestAmountDeductible) {
        this.totalImprestAmountDeductible = totalImprestAmountDeductible;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
}
