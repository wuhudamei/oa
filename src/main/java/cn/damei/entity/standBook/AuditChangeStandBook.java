package cn.damei.entity.standBook;
import java.util.Date;
import cn.damei.entity.IdEntity;
public class AuditChangeStandBook extends IdEntity {
    private String orderNo;
    private Date submitAuditTime;
    private Date auditTime;
    private Date assignAuditTime;
    private Date submitOrderTime;
    private Date submitMaterialTime;
    private Date materialAuditTime;
    private String changeNo;
    private String auditorRealname;
    private Date changeSubmitAuditTime;
    private Date changeAuditTime;
    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
    public Date getSubmitAuditTime() {
        return submitAuditTime;
    }
    public void setSubmitAuditTime(Date submitAuditTime) {
        this.submitAuditTime = submitAuditTime;
    }
    public Date getAuditTime() {
        return auditTime;
    }
    public void setAuditTime(Date auditTime) {
        this.auditTime = auditTime;
    }
    public Date getAssignAuditTime() {
        return assignAuditTime;
    }
    public void setAssignAuditTime(Date assignAuditTime) {
        this.assignAuditTime = assignAuditTime;
    }
    public Date getSubmitOrderTime() {
        return submitOrderTime;
    }
    public void setSubmitOrderTime(Date submitOrderTime) {
        this.submitOrderTime = submitOrderTime;
    }
    public Date getSubmitMaterialTime() {
        return submitMaterialTime;
    }
    public void setSubmitMaterialTime(Date submitMaterialTime) {
        this.submitMaterialTime = submitMaterialTime;
    }
    public Date getMaterialAuditTime() {
        return materialAuditTime;
    }
    public void setMaterialAuditTime(Date materialAuditTime) {
        this.materialAuditTime = materialAuditTime;
    }
    public String getChangeNo() {
        return changeNo;
    }
    public void setChangeNo(String changeNo) {
        this.changeNo = changeNo;
    }
    public String getAuditorRealname() {
        return auditorRealname;
    }
    public void setAuditorRealname(String auditorRealname) {
        this.auditorRealname = auditorRealname;
    }
    public Date getChangeSubmitAuditTime() {
        return changeSubmitAuditTime;
    }
    public void setChangeSubmitAuditTime(Date changeSubmitAuditTime) {
        this.changeSubmitAuditTime = changeSubmitAuditTime;
    }
    public Date getChangeAuditTime() {
        return changeAuditTime;
    }
    public void setChangeAuditTime(Date changeAuditTime) {
        this.changeAuditTime = changeAuditTime;
    }
}
