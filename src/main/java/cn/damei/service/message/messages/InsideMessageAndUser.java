package cn.damei.service.message.messages;
import java.util.ArrayList;
import java.util.List;
import cn.damei.entity.oa.InsideMessage;
public class InsideMessageAndUser {
    private InsideMessage insideMessage;
    List<Long> userIds;
    public InsideMessage getInsideMessage() {
        return insideMessage;
    }
    public InsideMessageAndUser setInsideMessage(InsideMessage insideMessage) {
        this.insideMessage = insideMessage;
        return this;
    }
    public List<Long> getUserIds() {
        return userIds;
    }
    public InsideMessageAndUser setUserIds(List<Long> userIds) {
        this.userIds = userIds;
        return this;
    }
    public InsideMessageAndUser pushUser(Long userId) {
        if (this.userIds == null) {
            this.userIds = new ArrayList<>();
        }
        this.userIds.add(userId);
        return this;
    }
}
