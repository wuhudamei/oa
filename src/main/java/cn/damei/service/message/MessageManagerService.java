package cn.damei.service.message;
import com.google.common.collect.Lists;
import cn.damei.SpringContextUtil;
import cn.damei.dto.message.TemplateMessage;
import cn.damei.dto.message.TemplateMessages;
import cn.damei.dto.message.TemplateMessage.NameValue;
import cn.damei.entity.oa.InsideMessage;
import cn.damei.entity.oa.InsideMessageTarget;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.enumeration.FirstTypes;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.repository.oa.InsideMessageTargetDao;
import cn.damei.service.message.apply.*;
import cn.damei.service.message.messages.InsideMessageAndUser;
import cn.damei.service.message.messages.TemplateMessageAndUser;
import cn.damei.service.oa.InsideMessageService;
import cn.damei.service.wechat.WechatUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
@Service
public class MessageManagerService {
	private static Logger logger = LoggerFactory.getLogger(MessageManagerService.class);// 日志
	@Autowired
	private TemplateMessageService templateMessageService;
	@Autowired
	private InsideMessageService insideMessageService;
	@Autowired
	private InsideMessageTargetDao insideMessageTargetDao;
	@Autowired
	private NotificationHandler notificationHandler;
	@Autowired
	private WechatUserService wechatUserService;
	private final ExecutorService threadPool = Executors.newCachedThreadPool();
	public void sendMessage(final SendParams sendParams) {
		threadPool.execute(() -> {
			BaseHandler handler = getHandlerByType(sendParams.getWfType());
			if (handler == null) {
				logger.error(sendParams.getWfType().getLabel() + "类申请，没有对应的发送微信模板消息逻辑！");
			}
			BaseHandler.HandleInsideAndWechatMessage handleInsideAndWechatMessage = null;
			if (sendParams.getIsApproverr()) {
				handleInsideAndWechatMessage = handler.send2Approver(sendParams);
			} else {
				handleInsideAndWechatMessage = handler.send2Applicant(sendParams);
			}
			if (handleInsideAndWechatMessage != null) {
				// 发送模板消息
				List<TemplateMessage> templateMessages = handleInsideAndWechatMessage.getTemplateMessages();
				sendWechatMessage(templateMessages);
				// 发送内部消息
				List<InsideMessageAndUser> insideMessageAndUsers = handleInsideAndWechatMessage
						.getInsideMessageAndUsers();
				insertInsideMessage(insideMessageAndUsers);
			}
		});
	}
	public void sendMessage(final String content, List<Long> orgIds) {
		threadPool.execute(() -> {
			TemplateMessageAndUser templateMessageAndUser = notificationHandler.buildWechatMessages(content, orgIds);
			if (templateMessageAndUser != null) {
				List<TemplateMessage> templateMessages = templateMessageAndUser.getMessages();
				sendWechatMessage(templateMessages);
			}
		});
	}
	private BaseHandler getHandlerByType(WfApplyTypeEnum type) {
		// BUSINESS("出差"), LEAVE("请假"), BUDGET("预算"), EXPENSE("报销"),
		// PURCHASE("采购");
		// COMMON("通用审批");
		switch (type) {
		case BUSINESS:
			return SpringContextUtil.getBean(BusinessHandler.class);
		case LEAVE:
			return SpringContextUtil.getBean(LeaveHandler.class);
		case BUDGET:
			return SpringContextUtil.getBean(BudgetHandler.class);
		case EXPENSE:
			// return SpringContextUtil.getBean(PaymentHandler.class);
			return SpringContextUtil.getBean(ExpanseHandler.class);
		case PURCHASE:
			return SpringContextUtil.getBean(PurchaseHandler.class);
		case YEARBUDGET:
			return SpringContextUtil.getBean(YearBudgetHandler.class);
		case SIGNATURE:
			return SpringContextUtil.getBean(SignatureHandler.class);
		case COMMON:
			return SpringContextUtil.getBean(CommonHandler.class);
		default:
			return null;
		}
	}
	private void insertInsideMessage(final List<InsideMessageAndUser> insideMessageAndUsers) {
		if (insideMessageAndUsers != null && insideMessageAndUsers.size() > 0) {
			for (InsideMessageAndUser insideMessageAndUser : insideMessageAndUsers) {
				InsideMessage insideMessage = insideMessageAndUser.getInsideMessage();
				insideMessageService.insert(insideMessage);
				List<InsideMessageTarget> insideMessageTargets = new ArrayList<>();
				for (Long userId : insideMessageAndUser.getUserIds()) {
					InsideMessageTarget insideMessageTarget = new InsideMessageTarget();
					insideMessageTarget.setUserId(userId.intValue());
					insideMessageTarget.setMessageId(insideMessage.getId().intValue());
					insideMessageTarget.setStatus(0);
					insideMessageTargets.add(insideMessageTarget);
				}
				insideMessageTargetDao.batchInsert(insideMessageTargets);
			}
		}
	}
	private void sendWechatMessage(final List<TemplateMessage> messages) {
		if (messages != null && messages.size() > 0) {
			for (TemplateMessage templateMessage : messages) {
				templateMessageService.sendTemplateMessage(templateMessage);
			}
		}
	}
	public void sendCrmTemplateMessage(String url, String customerName, String customerMobile, String optDate,
			String ts) {
		threadPool.execute(() -> {
			String param1 = customerName + ",来电咨询";
			String param3 = "请及时回电";
			// 构建模板消息
			TemplateMessage templateMessage = TemplateMessages.buildCrmTaskTemplateMessage(url, "你好,你有一个系统派单任务", ts,
					customerMobile, "--", param1, optDate, param3);
			List<TemplateMessage> messages = new ArrayList<>();
			messages.add(templateMessage);
			sendWechatMessage(messages);
		});
	}
	public void sendCrmScheduleTemplateMessage(String url, String openId, String head, String param1, String param2) {
		threadPool.execute(() -> {
			// 构建模板消息
			TemplateMessage templateMessage = TemplateMessages.buildCrmScheduleTemplateMessage(url, head, openId,
					param1, param2);
			List<TemplateMessage> messages = new ArrayList<>();
			messages.add(templateMessage);
			sendWechatMessage(messages);
		});
	}
	public void sendWorkOrderStageTemplate(String url, String openId, String head, String keyword1, String keyword2,
			String keyword3, String remark) {
		threadPool.execute(() -> {
			// 构建模板消息
			TemplateMessage templateMessage = TemplateMessages.buildWorkOrderStageTemplateMessage(url, head, openId,
					keyword1, keyword2, keyword3, remark);
			List<TemplateMessage> messages = new ArrayList<>();
			messages.add(templateMessage);
			sendWechatMessage(messages);
		});
	}
	public void sendApprovalTemplate(String url, String openId, String head, String keyword1, String keyword2,
			String keyword3, String keyword4) {
		threadPool.execute(() -> {
			// 构建模板消息
			TemplateMessage templateMessage = TemplateMessages.buildCommonlyApproveMessage(url, head, keyword1,
					keyword2, keyword3, keyword4);
			templateMessage.setToUser(openId);
			List<TemplateMessage> messages = new ArrayList<>();
			messages.add(templateMessage);
			sendWechatMessage(messages);
		});
	}
	public void sendReCallTemplate(String url, String openId, String head, String keyword1, String keyword2,
			String keyword3, String remark) {
		threadPool.execute(() -> {
			// 构建模板消息
			TemplateMessage templateMessage = TemplateMessages.buildReCallMessage(url, openId, head, keyword1, keyword2,
					keyword3, remark);
			List<TemplateMessage> messages = new ArrayList<>();
			messages.add(templateMessage);
			sendWechatMessage(messages);
		});
	}
	public void sendAttendanceRecordsTemplate(String url, String jobNo, String head, String keyword1, String keyword2,
			String keyword3, String remark) {
		List<WechatUser> wechatUserList = wechatUserService.getByUserJobNum(Lists.newArrayList(jobNo));
		if (wechatUserList != null && wechatUserList.size() > 0) {
			threadPool.execute(() -> {
				// 构建模板消息
				TemplateMessage templateMessage = TemplateMessages.buildAttendanceRecordsMessage(url,
						wechatUserList.get(0).getOpenid(), head, keyword1, keyword2, keyword3, remark);
				List<TemplateMessage> messages = new ArrayList<>();
				messages.add(templateMessage);
				sendWechatMessage(messages);
			});
		}
	}
	public void sendCopyRemindTemplate(String url, String jobNo, String head, String keyword1, String keyword2,
			String keyword3, String remark) {
		List<WechatUser> wechatUserList = wechatUserService.getByUserJobNum(Lists.newArrayList(jobNo));
		if (wechatUserList != null && wechatUserList.size() > 0) {
			threadPool.execute(() -> {
				// 构建模板消息
				TemplateMessage templateMessage = TemplateMessages.buildCopyRemindMessage(url,
						wechatUserList.get(0).getOpenid(), head, keyword1, keyword2, keyword3, remark);
				List<TemplateMessage> messages = new ArrayList<>();
				messages.add(templateMessage);
				sendWechatMessage(messages);
			});
		}
	}
	public void sendNoticeOfLeaveTemplate(String url, String jobNo, String head, String keyword1, String keyword2,
			String keyword3, String keyword4, String remark) {
		List<WechatUser> wechatUserList = wechatUserService.getByUserJobNum(Lists.newArrayList(jobNo));
		if (wechatUserList != null && wechatUserList.size() > 0) {
			threadPool.execute(() -> {
				// 构建模板消息
				TemplateMessage templateMessage = TemplateMessages.buildNoticeOfLeaveTemplateMsessage(url,
						wechatUserList.get(0).getOpenid(), head, keyword1, keyword2, keyword3, keyword4, remark);
				List<TemplateMessage> messages = new ArrayList<>();
				messages.add(templateMessage);
				sendWechatMessage(messages);
			});
		}
	}
	public static class SendParams {
		private String url;
		private Long formId;
		private WfApplyTypeEnum wfType;
		private boolean isApproverr;
		private String opinion;
		private Long fromUserId;
		private List<ToUser> toUsers;
		public String getOpinion() {
			return opinion;
		}
		public SendParams setOpinion(String opinion) {
			this.opinion = opinion;
			return this;
		}
		public String getUrl() {
			return url;
		}
		public SendParams setUrl(String url) {
			this.url = url;
			return this;
		}
		public Long getFormId() {
			return formId;
		}
		public SendParams setFormId(Long formId) {
			this.formId = formId;
			return this;
		}
		public WfApplyTypeEnum getWfType() {
			return wfType;
		}
		public SendParams setWfType(WfApplyTypeEnum wfType) {
			this.wfType = wfType;
			return this;
		}
		public boolean getIsApproverr() {
			return this.isApproverr;
		}
		public SendParams setIsApproverr(boolean isApproverr) {
			this.isApproverr = isApproverr;
			return this;
		}
		public Long getFromUserId() {
			return fromUserId;
		}
		public SendParams setFromUserId(Long fromUserId) {
			this.fromUserId = fromUserId;
			return this;
		}
		public List<ToUser> getToUsers() {
			return toUsers;
		}
		public SendParams setToUsers(List<ToUser> toUsers) {
			this.toUsers = toUsers;
			return this;
		}
		public SendParams pushToUser(Long userId) {
			if (this.toUsers == null) {
				this.toUsers = new ArrayList<>();
			}
			this.toUsers.add(new ToUser(userId));
			return this;
		}
		public SendParams pushToUser(Long userId, FirstTypes firstType) {
			if (this.toUsers == null) {
				this.toUsers = new ArrayList<>();
			}
			this.toUsers.add(new ToUser(userId, firstType));
			return this;
		}
	}
	public static class ToUser {
		private Long userId;
		private FirstTypes firstTypes;
		public ToUser(Long userId) {
			this.userId = userId;
		}
		public ToUser(Long userId, FirstTypes firstType) {
			this.userId = userId;
			this.firstTypes = firstType;
		}
		public Long getUserId() {
			return userId;
		}
		public void setUserId(Long userId) {
			this.userId = userId;
		}
		public FirstTypes getFirstTypes() {
			return firstTypes;
		}
		public void setFirstTypes(FirstTypes firstTypes) {
			this.firstTypes = firstTypes;
		}
	}
}
