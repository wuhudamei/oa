package cn.damei.service.message.apply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.dto.message.TemplateMessages;
import cn.damei.entity.budget.Payment;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.employee.EmployeeOrg;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.message.messages.InsideMessageAndUser;
import cn.damei.service.message.messages.TemplateMessageAndUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MoneyUtils;
import java.util.ArrayList;
import java.util.List;
@Service
public class PaymentHandler extends BaseHandler{
    @Autowired
    private PaymentService paymentService;
    private Logger logger = LoggerFactory.getLogger(getClass());
    public BaseHandler.HandleInsideAndWechatMessage send2Approver(MessageManagerService.SendParams sendParams) {
        Payment payment = paymentService.getDetails(sendParams.getFormId());
        if (payment == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        Employee employee = employeeService.getById(sendParams.getFromUserId());
        if (sendParams.getToUsers() != null && sendParams.getToUsers().size() > 0) {
            List<WechatUser> userBinds = findUserOpenIdsByToUsers(sendParams.getToUsers());
            InsideMessageAndUser insideMessageAndUser = new InsideMessageAndUser();
            insideMessageAndUser.setInsideMessage(buildInsideMessage(payment.getApplyTitle(), String.format(APPROVE_DETAILS_TEMPLATE, employee.getName(), sendParams.getWfType().getLabel())));
            List<TemplateMessageAndUser> wecharMessageAndUsers = new ArrayList<>();
            for (MessageManagerService.ToUser touser : sendParams.getToUsers()) {
                insideMessageAndUser.pushUser(touser.getUserId());
                double money = payment.getTotalMoney();
                TemplateMessage templateMessage = TemplateMessages.buildPaymentApproverMessage(sendParams.getUrl(), String.format(APPROVE_DETAILS_TEMPLATE, employee.getName(), sendParams.getWfType().getLabel()),
                        DateUtils.format(payment.getCreateDate(), DateUtils.CHINESE_YYYY_MM_DD_HH_MM_SS), employee.getName(), MoneyUtils.format(money, MoneyUtils.YUAN_DOUBLE_DECIMAL));
                addTemplateMessage(wecharMessageAndUsers, templateMessage, getOpenIdsByUserId(userBinds, touser.getUserId()));
            }
            HandleInsideAndWechatMessage handleInsideAndWechatMessage = new HandleInsideAndWechatMessage();
            handleInsideAndWechatMessage.pushInsideMessageAndUser(insideMessageAndUser).setWechatMessagesAndUsers(wecharMessageAndUsers);
            return handleInsideAndWechatMessage;
        }
        return null;
    }
    public BaseHandler.HandleInsideAndWechatMessage send2Applicant(MessageManagerService.SendParams sendParams) {
        Payment payment = paymentService.getDetails(sendParams.getFormId());
        if (payment == null) {
            logger.warn(LOGGER_ENTITY_IS_NULL, sendParams.getFormId(), sendParams.getWfType().getLabel());
            return null;
        }
        Employee employee = employeeService.getById(sendParams.getFromUserId());
        if (sendParams.getToUsers() != null && sendParams.getToUsers().size() > 0) {
            List<WechatUser> userBinds = findUserOpenIdsByToUsers(sendParams.getToUsers());
            InsideMessageAndUser insideMessageAndUser = new InsideMessageAndUser();
            insideMessageAndUser.setInsideMessage(buildInsideMessage(payment.getApplyTitle(), String.format(APPLICANT_DETAILS_TEMPLATE, sendParams.getWfType().getLabel(), employee.getName())));
            List<TemplateMessageAndUser> wecharMessageAndUsers = new ArrayList<>();
            for (MessageManagerService.ToUser touser : sendParams.getToUsers()) {
                insideMessageAndUser.pushUser(touser.getUserId());
                EmployeeOrg employeeOrg = employeeOrgService.getDirectByEmpId(touser.getUserId());
                TemplateMessage templateMessage = TemplateMessages.buildPaymentApplicantMessage(String.format(PAYMENT_APPLICANT_TITLE, payment.getApplyCode(), employee.getName()),
                        sendParams.getUrl(), employeeOrg == null ? "" : employeeOrg.getCompany().getOrgName(), DateUtils.format(payment.getCreateDate(), DateUtils.CHINESE_YYYY_MM_DD_HH_MM_SS), "",
                        MoneyUtils.format(payment.getTotalMoney(), MoneyUtils.YUAN_DOUBLE_DECIMAL), sendParams.getWfType().getLabel());
                addTemplateMessage(wecharMessageAndUsers, templateMessage, getOpenIdsByUserId(userBinds, touser.getUserId()));
            }
            HandleInsideAndWechatMessage handleInsideAndWechatMessage = new HandleInsideAndWechatMessage();
            handleInsideAndWechatMessage.pushInsideMessageAndUser(insideMessageAndUser).setWechatMessagesAndUsers(wecharMessageAndUsers);
            return handleInsideAndWechatMessage;
        }
        return null;
    }
}
