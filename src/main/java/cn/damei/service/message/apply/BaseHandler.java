package cn.damei.service.message.apply;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.dto.message.TemplateMessages;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.oa.InsideMessage;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.message.messages.InsideMessageAndUser;
import cn.damei.service.message.messages.TemplateMessageAndUser;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.utils.DateUtils;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
@SuppressWarnings("all")
public abstract class BaseHandler {
    protected final String LOGGER_ENTITY_IS_NULL = "select %s is null, id is : %s";
    protected final String APPROVE_DETAILS_TEMPLATE = "您好，您有一条来自%s的%s审批申请";
    protected final String PAYMENT_APPLICANT_TITLE = "您的报销单（编号%s）已被%s审核。详细信息如下：";
    protected final String APPLICANT_DETAILS_TEMPLATE = "您好，您的%s申请已被%s审批";
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    protected EmployeeService employeeService;
    @Autowired
    protected EmployeeOrgService employeeOrgService;
    public abstract  HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams);
    public abstract HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams);
    protected BaseHandler.HandleInsideAndWechatMessage commonlySend2Applicant(MessageManagerService.SendParams sendParams, String title) {
        if (sendParams.getToUsers() != null && sendParams.getToUsers().size() > 0) {
            Employee employee = employeeService.getById(sendParams.getFromUserId());
            List<WechatUser> userBinds = findUserOpenIdsByToUsers(sendParams.getToUsers());
            InsideMessageAndUser insideMessageAndUser = new InsideMessageAndUser();
            insideMessageAndUser.setInsideMessage(buildInsideMessage(title, String.format(APPLICANT_DETAILS_TEMPLATE, sendParams.getWfType().getLabel(), employee.getName())));
            List<TemplateMessageAndUser> wecharMessageAndUsers = new ArrayList<>();
            for (MessageManagerService.ToUser touser : sendParams.getToUsers()) {
                insideMessageAndUser.pushUser(touser.getUserId());
                TemplateMessage templateMessage = TemplateMessages.buildCommonlyApplicantMessage(sendParams.getUrl(), String.format(APPLICANT_DETAILS_TEMPLATE, sendParams.getWfType().getLabel(), employee.getName()),
                        sendParams.getWfType().getLabel(), employee.getName(), sendParams.getOpinion(), sendParams.getOpinion());
                addTemplateMessage(wecharMessageAndUsers, templateMessage, getOpenIdsByUserId(userBinds, touser.getUserId()));
            }
            HandleInsideAndWechatMessage handleInsideAndWechatMessage = new HandleInsideAndWechatMessage();
            handleInsideAndWechatMessage.pushInsideMessageAndUser(insideMessageAndUser).setWechatMessagesAndUsers(wecharMessageAndUsers);
            return handleInsideAndWechatMessage;
        }
        return null;
    }
    protected BaseHandler.HandleInsideAndWechatMessage commonlySend2Approver(MessageManagerService.SendParams sendParams, String title, String reason, Date applyDate) {
        if (sendParams.getToUsers() != null && sendParams.getToUsers().size() > 0) {
            Employee employee = employeeService.getById(sendParams.getFromUserId());
            EmployeeOrg employeeOrg = employeeOrgService.getDirectByEmpId(sendParams.getFromUserId());
            List<WechatUser> userBinds = findUserOpenIdsByToUsers(sendParams.getToUsers());
            InsideMessageAndUser insideMessageAndUser = new InsideMessageAndUser();
            insideMessageAndUser.setInsideMessage(buildInsideMessage(title, String.format(APPROVE_DETAILS_TEMPLATE, employee.getName(), sendParams.getWfType().getLabel())));
            List<TemplateMessageAndUser> wecharMessageAndUsers = new ArrayList<>();
            for (MessageManagerService.ToUser touser : sendParams.getToUsers()) {
                insideMessageAndUser.pushUser(touser.getUserId());
                TemplateMessage templateMessage = TemplateMessages.buildCommonlyApproveMessage(sendParams.getUrl(), String.format(APPROVE_DETAILS_TEMPLATE, employee.getName(), sendParams.getWfType().getLabel()),
                        employee.getName(), employeeOrg == null ? "" : employeeOrg.getDepartment().getOrgName(), reason, DateUtils.format(applyDate, DateUtils.CHINESE_YYYY_MM_DD_HH_MM_SS));
                addTemplateMessage(wecharMessageAndUsers, templateMessage, getOpenIdsByUserId(userBinds, touser.getUserId()));
            }
            HandleInsideAndWechatMessage handleInsideAndWechatMessage = new HandleInsideAndWechatMessage();
            handleInsideAndWechatMessage.pushInsideMessageAndUser(insideMessageAndUser).setWechatMessagesAndUsers(wecharMessageAndUsers);
            return handleInsideAndWechatMessage;
        }
        return null;
    }
    protected void addTemplateMessage(List<TemplateMessageAndUser> wecharMessageAndUsers, TemplateMessage templateMessage, List<String> openIds) {
        TemplateMessageAndUser templateMessageAndUser = new TemplateMessageAndUser();
        templateMessageAndUser.setTemplateMessage(templateMessage).setOpenIds(openIds);
        wecharMessageAndUsers.add(templateMessageAndUser);
    }
    protected InsideMessage buildInsideMessage(String title, String content) {
        return new InsideMessage(title,content,InsideMessage.WECHAT_TYPE);
    }
    protected List<String> getOpenIdsByUserId(List<WechatUser> userBinds, Long userId) {
        List<String> openIds = new ArrayList<>();
        if (userBinds != null && userBinds.size() > 0 && userId != null) {
            for (WechatUser userBind : userBinds) {
                if (userId.equals(userBind.getUserId())) {
                    openIds.add(userBind.getOpenid());
                }
            }
        }
        return openIds;
    }
    protected List<WechatUser> findUserOpenIdsByToUsers(List<MessageManagerService.ToUser> toUsers) {
        if (toUsers != null && toUsers.size() > 0) {
            List<Long> userIds = new ArrayList<>();
            for (MessageManagerService.ToUser toUser : toUsers) {
                if (toUser.getUserId() != null)
                    userIds.add(toUser.getUserId());
            }
            return wechatUserService.getByUserIds(userIds);
        }
        return Collections.emptyList();
    }
    public static class HandleInsideAndWechatMessage {
        List<TemplateMessageAndUser> wechatMessagesAndUsers;
        List<InsideMessageAndUser> insideMessageAndUsers;
        HandleInsideAndWechatMessage() {
        }
        public List<TemplateMessage> getTemplateMessages() {
            List<TemplateMessage> templateMessages = new ArrayList<>();
            if (this.wechatMessagesAndUsers != null && wechatMessagesAndUsers.size() > 0) {
                for (TemplateMessageAndUser templateMessageAndUser : wechatMessagesAndUsers) {
                    templateMessages.addAll(templateMessageAndUser.getMessages());
                }
            }
            return templateMessages;
        }
        public HandleInsideAndWechatMessage pushWechatMessageAndUser(TemplateMessageAndUser templateMessageAndUser) {
            if (this.wechatMessagesAndUsers == null) {
                this.wechatMessagesAndUsers = new ArrayList<>();
            }
            this.wechatMessagesAndUsers.add(templateMessageAndUser);
            return this;
        }
        HandleInsideAndWechatMessage pushInsideMessageAndUser(InsideMessageAndUser insideMessageAndUser) {
            if (this.insideMessageAndUsers == null) {
                this.insideMessageAndUsers = new ArrayList<>();
            }
            this.insideMessageAndUsers.add(insideMessageAndUser);
            return this;
        }
        public List<TemplateMessageAndUser> getWechatMessagesAndUsers() {
            return wechatMessagesAndUsers;
        }
        HandleInsideAndWechatMessage setWechatMessagesAndUsers(List<TemplateMessageAndUser> wechatMessagesAndUsers) {
            this.wechatMessagesAndUsers = wechatMessagesAndUsers;
            return this;
        }
        public List<InsideMessageAndUser> getInsideMessageAndUsers() {
            return insideMessageAndUsers;
        }
        public HandleInsideAndWechatMessage setInsideMessageAndUsers(List<InsideMessageAndUser> insideMessageAndUsers) {
            this.insideMessageAndUsers = insideMessageAndUsers;
            return this;
        }
    }
}
