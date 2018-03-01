package cn.damei.service.message;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.dto.message.TemplateMessages;
import cn.damei.service.message.messages.TemplateMessageAndUser;
import cn.damei.service.wechat.WechatUserService;
import java.util.List;
@Service
public class NotificationHandler {
    @Autowired
    private WechatUserService wechatUserService;
    public TemplateMessageAndUser buildWechatMessages(String content, List<Long> orgIds) {
        if (StringUtils.isBlank(content)) {
            return null;
        }
        TemplateMessage message = TemplateMessages.buildNotificationMessage(content);
        List<String> openIds = wechatUserService.getByParentOrgIds(orgIds);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(openIds))
            return new TemplateMessageAndUser().setTemplateMessage(message)
                    .setOpenIds(openIds);
        return null;
    }
}
