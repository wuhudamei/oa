package cn.damei.entity.oa;
import cn.damei.entity.IdEntity;
public class InsideMessageTarget extends IdEntity{
	private static final long serialVersionUID = 1L;
    private Integer messageId;
    private Integer userId;
    private Integer status;
    public Integer getMessageId() {
        return messageId;
    }
    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }
    public Integer getUserId() {
        return userId;
    }
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getStatus() {
        return status;
    }
    public void setStatus(Integer status) {
        this.status = status;
    }
}
