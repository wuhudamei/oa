package cn.damei.entity.oa;
import com.fasterxml.jackson.annotation.JsonFormat;
import cn.damei.entity.IdEntity;
import cn.damei.utils.DateUtils;
import java.util.Date;
public class InsideMessage extends IdEntity {
	private static final long serialVersionUID = 1L;
	public static final int DEFAULT_OVERDUE_DAYS = 1;
    public static final int DEFAULT_LEVEL = 1;
    public static final String WECHAT_TYPE = "WeChat";
    private String messageTitle;
    private String messageContent;
    private String messageType;
    private Integer messageLevel;
    private String creater;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date expirationTime;
    private String remindMode;
    public InsideMessage(){}
    public InsideMessage(String title,String content,String type){
        this.messageTitle = title;
        this.messageContent = content;
        this.messageType = type;
        this.messageLevel = DEFAULT_LEVEL;
        this.createTime = new Date();
        this.expirationTime =DateUtils.getDateBeforOrAfterDate(new Date(), InsideMessage.DEFAULT_OVERDUE_DAYS);
        this.remindMode = type;
    }
    public String getMessageTitle() {
        return messageTitle;
    }
    public void setMessageTitle(String messageTitle) {
        this.messageTitle = messageTitle;
    }
    public String getMessageContent() {
        return messageContent;
    }
    public void setMessageContent(String messageContent) {
        this.messageContent = messageContent;
    }
    public String getMessageType() {
        return messageType;
    }
    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }
    public Integer getMessageLevel() {
        return messageLevel;
    }
    public void setMessageLevel(Integer messageLevel) {
        this.messageLevel = messageLevel;
    }
    public String getCreater() {
        return creater;
    }
    public void setCreater(String creater) {
        this.creater = creater;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getExpirationTime() {
        return expirationTime;
    }
    public void setExpirationTime(Date expirationTime) {
        this.expirationTime = expirationTime;
    }
    public String getRemindMode() {
        return remindMode;
    }
    public void setRemindMode(String remindMode) {
        this.remindMode = remindMode;
    }
}
