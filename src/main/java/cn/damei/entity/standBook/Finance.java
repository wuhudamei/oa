package cn.damei.entity.standBook;
import javafx.scene.layout.BackgroundImage;
import java.math.BigDecimal;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class Finance extends IdEntity {
    private Integer financeType;
    private String receiptNumber;
    private String payName;
    private String payMobile;
    private Date financeTime;
    private String payment;
    private BigDecimal received;
    public Integer getFinanceType() {
        return financeType;
    }
    public void setFinanceType(Integer financeType) {
        this.financeType = financeType;
    }
    public String getReceiptNumber() {
        return receiptNumber;
    }
    public void setReceiptNumber(String receiptNumber) {
        this.receiptNumber = receiptNumber;
    }
    public String getPayName() {
        return payName;
    }
    public void setPayName(String payName) {
        this.payName = payName;
    }
    public String getPayMobile() {
        return payMobile;
    }
    public void setPayMobile(String payMobile) {
        this.payMobile = payMobile;
    }
    public Date getFinanceTime() {
        return financeTime;
    }
    public void setFinanceTime(Date financeTime) {
        this.financeTime = financeTime;
    }
    public String getPayment() {
        return payment;
    }
    public void setPayment(String payment) {
        this.payment = payment;
    }
    public BigDecimal getReceived() {
        return received;
    }
    public void setReceived(BigDecimal received) {
        this.received = received;
    }
}
