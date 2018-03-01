package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.SpringContextUtil;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.message.TemplateMessageService;
public class MessageServiceTest extends SpringTestCase {
    @Autowired
    private TemplateMessageService templateMessageService;
    @Autowired
    private MessageManagerService messageManagerService;
//    @Test
    public void testSendTemplateMessage() {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setToUser("oYq6cwAIzJDJP10hTDB6Lc6MOKWM")
                .setTemplateId("i0ruYMjK6AXAvY4jA15UxvFBpo53j9Vnk3wDRrN_g_M")
                .setUrl("http://www.rocoinfo.com")
                .pushHead("你好，你提交的审批流程状态已更新。")
                .pushTail("感谢你的使用。")
                .pushItem("keyword1", "人工湖建设合同")
                .pushItem("keyword2", "通过")
                .pushItem("keyword3", "你发起的人工湖建设合同已通过张三审核，请您进行下一步相关处理。");
        templateMessageService.sendTemplateMessage(templateMessage);
    }
    @Test
    public void getSendMessage() throws InterruptedException {
        MessageManagerService.SendParams sendParams = new MessageManagerService.SendParams();
        sendParams.setUrl("http://www.baidu.com")
                .setOpinion("同意")
                .setIsApproverr(true)
                .setFromUserId(124L)
                .pushToUser(125L)
                .setWfType(WfApplyTypeEnum.BUDGET)
                .setFormId(65L);
        messageManagerService.sendMessage(sendParams);
        Thread.sleep(2000);
    }
    @Test
    public void testSpringContextUtil() {
        MessageManagerService messageManagerService = SpringContextUtil.getBean(MessageManagerService.class);
        messageManagerService.getClass();
    }
    @Test
    public void testSendNoticeMessage() throws InterruptedException {
        String content = "this is template message!";
        messageManagerService.sendMessage(content, null);
        Thread.sleep(3000);
    }
}
