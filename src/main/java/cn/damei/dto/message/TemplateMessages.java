package cn.damei.dto.message;
import cn.damei.common.PropertyHolder;
public final class TemplateMessages {
    private static final String PAYMENT_APPROVE_ID = "-eLXMYrdkbrGfauNszevmAWSfAm3AoI5ypzH8ctLa1o";
    private static final String PAYMENT_APPLICANT_ID = "WctiwR7QqTdcIL6vcZrowOSgYaDleHt7RKuBr_eAlTQ";
      private static final String COMMONLY_APPROVE_ID = PropertyHolder.getCommonlyApproveId();
      private static final String COMMONLY_APPLICANT_ID = PropertyHolder.getCommonlyApplicantId();
      private static final String CRM_TASK_TEMPLATE_MESSAGE = PropertyHolder.getCrmTaskTemplateMessage();
      private static final String CRM_COMMON_TEMPLATE_MESSAGE = PropertyHolder.getCrmCommonTemplateMessage();
    private static final String OA_WORK_ORDER_STAGE_TEMPLATE_MESSAGE = PropertyHolder.getOaWorkOrderStageTemplateMessage();
    private static final String RE_CALL_TEMPLATE_MESSAGE = PropertyHolder.getReCallTemplateMessage();
    private static final String ATTENDANCE_RECORDS_TEMPLATE_MESSAGE = PropertyHolder.getAttendanceRecordsTemplateMessage();
    private static final String COPY_REMIND_TEMPLATE_MESSAGE = PropertyHolder.getCopyRemindTemplateMessage();
    private static final String NOTICE_OF_LEAVE_TEMPLATE_MESSAGE = PropertyHolder.getNoticeOfLeaveTemplateMessage();
    private static final String APPROVE_TAIL = "请点击详情进行审批";
    private static final String APPLICANT_TAIL = "请点击查看详情";
    public static TemplateMessage buildPaymentApplicantMessage(String head, String url, String keyword1, String keyword2, String keyword3, String keyword4, String keyword5) {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setTemplateId(PAYMENT_APPLICANT_ID)
                .setUrl(url)
                .pushHead(head)
                .pushTail(APPLICANT_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushItem("keyword4", keyword4)
                .pushItem("keyword5", keyword5);
        return templateMessage;
    }
    public static TemplateMessage buildPaymentApproverMessage(String url, String head, String keyword1, String keyword2, String keyword3) {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setTemplateId(PAYMENT_APPROVE_ID)
                .setUrl(url)
                .pushHead(head)
                .pushTail(APPROVE_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3);
        return templateMessage;
    }
    public static TemplateMessage buildCommonlyApproveMessage(String url, String head,
                      String keyword1, String keyword2, String keyword3, String keyword4) {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(COMMONLY_APPROVE_ID)
                .pushHead(head)
                .pushTail(APPROVE_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushItem("keyword4", keyword4);
        return templateMessage;
    }
    public static TemplateMessage buildCommonlyApplicantMessage(String url, String head,
                    String keyword1, String keyword2, String keyword3, String keyword4) {
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(COMMONLY_APPLICANT_ID)
                .pushHead(head)
                .pushTail(APPLICANT_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushItem("keyword4", keyword4);
        return templateMessage;
    }
    public static TemplateMessage buildCrmTaskTemplateMessage(String url, String head,String openId, String keyword1, String keyword2, String keyword3, String keyword4,String keyword5){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(CRM_TASK_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushTail(APPLICANT_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushItem("keyword4", keyword4)
        		.pushItem("keyword5", keyword5);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildCrmScheduleTemplateMessage(String url, String head,String openId, String keyword1, String keyword2){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(CRM_COMMON_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushTail(APPLICANT_TAIL)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildWorkOrderStageTemplateMessage(String url, String head,
                 String openId, String keyword1, String keyword2, String keyword3, String remark){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(OA_WORK_ORDER_STAGE_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushTail(remark);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildNotificationMessage(String content) {
        return null;
    }
    public static TemplateMessage buildReCallMessage(String url, String openId, String head,
                     String keyword1, String keyword2, String keyword3, String remark){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(RE_CALL_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushTail(remark);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildAttendanceRecordsMessage(String url, String openId, String head,
                                                     String keyword1, String keyword2, String keyword3, String remark){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(ATTENDANCE_RECORDS_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushTail(remark);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildCopyRemindMessage(String url, String openId, String head,
                                                                String keyword1, String keyword2, String keyword3, String remark){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(COPY_REMIND_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushTail(remark);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
    public static TemplateMessage buildNoticeOfLeaveTemplateMsessage(String url, String openId, String head,
                                 String keyword1, String keyword2, String keyword3, String keyword4, String remark){
        TemplateMessage templateMessage = new TemplateMessage();
        templateMessage.setUrl(url)
                .setTemplateId(NOTICE_OF_LEAVE_TEMPLATE_MESSAGE)
                .pushHead(head)
                .pushItem("keyword1", keyword1)
                .pushItem("keyword2", keyword2)
                .pushItem("keyword3", keyword3)
                .pushItem("keyword4", keyword4)
                .pushTail(remark);
        templateMessage.setToUser(openId);
        return templateMessage;
    }
}
