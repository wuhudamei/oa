package cn.damei.entity.sign;
import java.io.Serializable;
import java.util.Date;
public class DepSignMessageLog implements Serializable,Comparable<DepSignMessageLog>{
    private Long orgId;
    private Date depSignTime;
    private Date depSignOutTime;
    private Boolean signMessage;
    private Boolean signOutMessage;
    private Date signSendMessageTime;
    private Date signOutMessageTime;
    public Long getOrgId() {
        return orgId;
    }
    public void setOrgId(Long orgId) {
        this.orgId = orgId;
    }
    public Date getDepSignTime() {
        return depSignTime;
    }
    public void setDepSignTime(Date depSignTime) {
        this.depSignTime = depSignTime;
    }
    public Date getDepSignOutTime() {
        return depSignOutTime;
    }
    public void setDepSignOutTime(Date depSignOutTime) {
        this.depSignOutTime = depSignOutTime;
    }
    public Boolean getSignMessage() {
        return signMessage;
    }
    public void setSignMessage(Boolean signMessage) {
        this.signMessage = signMessage;
    }
    public Boolean getSignOutMessage() {
        return signOutMessage;
    }
    public void setSignOutMessage(Boolean signOutMessage) {
        this.signOutMessage = signOutMessage;
    }
    public Date getSignSendMessageTime() {
        return signSendMessageTime;
    }
    public void setSignSendMessageTime(Date signSendMessageTime) {
        this.signSendMessageTime = signSendMessageTime;
    }
    public Date getSignOutMessageTime() {
        return signOutMessageTime;
    }
    public void setSignOutMessageTime(Date signOutMessageTime) {
        this.signOutMessageTime = signOutMessageTime;
    }
    @Override
    public int compareTo(DepSignMessageLog o) {
        return 0;
    }
}
