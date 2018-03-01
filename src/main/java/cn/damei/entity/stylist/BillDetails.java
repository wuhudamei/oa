package cn.damei.entity.stylist;
import java.io.Serializable;
import cn.damei.entity.IdEntity;
import cn.damei.enumeration.stylist.Status;
public class BillDetails extends IdEntity implements Serializable {
    private static final long serialVersionUID = -3108970565087346695L;
    private Long billId;
    private String customerName;
    private String contractName;
    private Double projectMoney;
    private Status contractStatus;
    private Double taxesMoney;
    private Double managerMoney;
    private Double designMoney;
    private Double remoteMoney;
    private Double othersMoney;
    private Double privilegeMoney;
    private Double billMoney;
    public Long getBillId() {
        return billId;
    }
    public BillDetails setBillId(Long billId) {
        this.billId = billId;
        return this;
    }
    public String getCustomerName() {
        return customerName;
    }
    public BillDetails setCustomerName(String customerName) {
        this.customerName = customerName;
        return this;
    }
    public String getContractName() {
        return contractName;
    }
    public BillDetails setContractName(String contractName) {
        this.contractName = contractName;
        return this;
    }
    public Double getProjectMoney() {
        return projectMoney;
    }
    public BillDetails setProjectMoney(Double projectMoney) {
        this.projectMoney = projectMoney;
        return this;
    }
    public Status getContractStatus() {
        return contractStatus;
    }
    public BillDetails setContractStatus(Status contractStatus) {
        this.contractStatus = contractStatus;
        return this;
    }
    public Double getTaxesMoney() {
        return taxesMoney;
    }
    public BillDetails setTaxesMoney(Double taxesMoney) {
        this.taxesMoney = taxesMoney;
        return this;
    }
    public Double getManagerMoney() {
        return managerMoney;
    }
    public BillDetails setManagerMoney(Double managerMoney) {
        this.managerMoney = managerMoney;
        return this;
    }
    public Double getDesignMoney() {
        return designMoney;
    }
    public BillDetails setDesignMoney(Double designMoney) {
        this.designMoney = designMoney;
        return this;
    }
    public Double getRemoteMoney() {
        return remoteMoney;
    }
    public BillDetails setRemoteMoney(Double remoteMoney) {
        this.remoteMoney = remoteMoney;
        return this;
    }
    public Double getOthersMoney() {
        return othersMoney;
    }
    public BillDetails setOthersMoney(Double othersMoney) {
        this.othersMoney = othersMoney;
        return this;
    }
    public Double getPrivilegeMoney() {
        return privilegeMoney;
    }
    public BillDetails setPrivilegeMoney(Double privilegeMoney) {
        this.privilegeMoney = privilegeMoney;
        return this;
    }
    public Double getBillMoney() {
        return billMoney;
    }
    public BillDetails setBillMoney(Double billMoney) {
        this.billMoney = billMoney;
        return this;
    }
}