package cn.damei.service.message.messages;
import java.util.ArrayList;
import java.util.List;
import cn.damei.dto.message.TemplateMessage;
public class TemplateMessageAndUser {
    private TemplateMessage templateMessage;
    private List<String> openIds;
    public TemplateMessageAndUser pushOpenId(String openId) {
        if (this.openIds == null) {
            this.openIds = new ArrayList<>();
        }
        this.openIds.add(openId);
        return this;
    }
    public List<TemplateMessage> getMessages() {
        List<TemplateMessage> templateMessages = new ArrayList<>();
        if (this.templateMessage != null && this.openIds != null && this.openIds.size() > 0) {
            for (String openId : this.openIds) {
                TemplateMessage templateMessage = this.templateMessage.clone();
                templateMessage.setToUser(openId);
                templateMessages.add(templateMessage);
            }
        }
        return templateMessages;
    }
    public TemplateMessage getTemplateMessage() {
        return templateMessage;
    }
    public TemplateMessageAndUser setTemplateMessage(TemplateMessage templateMessage) {
        this.templateMessage = templateMessage;
        return this;
    }
    public List<String> getOpenIds() {
        return openIds;
    }
    public TemplateMessageAndUser setOpenIds(List<String> openIds) {
        this.openIds = openIds;
        return this;
    }
}