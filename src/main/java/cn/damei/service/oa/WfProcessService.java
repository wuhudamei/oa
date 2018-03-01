package cn.damei.service.oa;
import com.fasterxml.jackson.databind.JavaType;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.service.CrudService;
import cn.damei.entity.account.User;
import cn.damei.entity.budget.Payment;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.commonapply.ApplyCommon;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.financail.FinancailPayment;
import cn.damei.entity.oa.WfProcess;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.process.ProcessEntity;
import cn.damei.entity.subjectprocess.SubjectProcess;
import cn.damei.entity.system.SystemParam;
import cn.damei.entity.wechat.WechatMessageLog;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.enumeration.*;
import cn.damei.repository.budget.PaymentDao;
import cn.damei.repository.commonapply.ApplyCommonDao;
import cn.damei.repository.financail.FinancailPaymentDao;
import cn.damei.repository.oa.WfProcessDao;
import cn.damei.repository.process.ProcessDao;
import cn.damei.repository.system.SystemParamDao;
import cn.damei.service.account.UserService;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.commonapply.ApplyCommonService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.message.MessageManagerService.SendParams;
import cn.damei.service.message.MessageManagerService.ToUser;
import cn.damei.service.subjectprocess.SubjectProcessService;
import cn.damei.service.wechat.WechatMessageLogService;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.UUIDUtils;
import cn.damei.utils.WebUtils;
import cn.damei.utils.weixin.WXUtil;
import cn.mdni.commons.json.JsonUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.text.SimpleDateFormat;
import java.util.*;
@SuppressWarnings("unchecked")
@Service
public class WfProcessService extends CrudService<WfProcessDao, WfProcess> {
	private static final Integer NO_OVER_BUDGET = 0;
	private static final String CEO_AUTH_KEY = "CEOAUTH";
	@Autowired
	private PaymentService paymentService;
	@Autowired
	private WfProcessDao wfProcessDao;
	@Autowired
	private MessageManagerService messageManagerService;
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private WechatUserService wechatUserService;
	@Autowired
	private PaymentDao paymentDao;
	@Autowired
	private UserService userService;
	@Autowired
	private SignatureService signatureService;
	@Autowired
	private SystemParamDao systemParamDao;
	@Autowired
	private FinancailPaymentDao financailPaymentDao;
	@Autowired
	private SubjectProcessService subjectProcessService;
	@Autowired
	private ApplySequenceService applySequenceService;
	@Autowired
	private ApplyCommonService applyCommonService;
	@Autowired
	private ApplyCommonDao applyCommonDao;
	@Autowired
	private WechatMessageLogService wechatMessageLogService;
	@Autowired
	private EmployeeService employeeService;
	// 初始化审批表单表明
	private static final Map<String, String> updateFormData = new HashMap<String, String>();
	static {
		updateFormData.put(WfApplyTypeEnum.BUSINESS.name(), "oa_apply_business_away");// 出差
		updateFormData.put(WfApplyTypeEnum.LEAVE.name(), "oa_vacation");// 请假
		updateFormData.put(WfApplyTypeEnum.BUDGET.name(), "oa_budget");// 预算
		updateFormData.put(WfApplyTypeEnum.YEARBUDGET.name(), "oa_year_budget");// 年度预算
		updateFormData.put(WfApplyTypeEnum.EXPENSE.name(), "oa_payment");// 报销
		updateFormData.put(WfApplyTypeEnum.PURCHASE.name(), "oa_apply_purchase");// 采购
		updateFormData.put(WfApplyTypeEnum.SIGNATURE.name(), "oa_payment_signature");// 签报0
		updateFormData.put(WfApplyTypeEnum.COMMON.name(), "oa_apply_common");// 通用申批
	}
	public int createWf(WfProcessParam wfProcessParam) throws Exception {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("applyOrg", wfProcessParam.getOrgId());
		paramMap.put("wfType", wfProcessParam.getApplyType());
		List<ProcessEntity> templateList = new ArrayList<ProcessEntity>();
		if (WfApplyTypeEnum.EXPENSE.name().equals(wfProcessParam.getApplyType())
				|| WfApplyTypeEnum.SIGNATURE.name().equals(wfProcessParam.getApplyType())) {
			SubjectProcess subjectProcess = subjectProcessService.findWfByCondition(wfProcessParam.getApplyType(),
					wfProcessParam.getProcessTypeId(), wfProcessParam.getSubjectId());
			if (subjectProcess == null) {
				throw new RuntimeException("ApplyType" + wfProcessParam.getApplyType() + ", ProcessTypeId:"
						+ wfProcessParam.getProcessTypeId() + ", SubjectId:" + wfProcessParam.getSubjectId()
						+ ",未配置科目流程关系！");
			}
			paramMap.put("wfId", subjectProcess.getWfId());
			if (NO_OVER_BUDGET.intValue() == wfProcessParam.getOverBudget().intValue()) {
				templateList = findExecuteTemplateByCondition(paramMap);// 未超预算直接走执行流程
			} else {
				templateList = findTemplateByCondition(paramMap);// 超过预算,走全流程
			}
		} else {
			templateList = findTemplateByCondition(paramMap);
		}
		int retNum = 0;
		if (!templateList.isEmpty()) {
			retNum = createProcess(wfProcessParam, templateList);
		}
		return retNum;
	}
	public int generAllWfprocessTree(WfProcessParam wfProcessParam, List<ProcessEntity> templateList) {
		List<WfProcess> list = Lists.newArrayList();
		List<WfProcess> messageList = Lists.newArrayList();
		if (templateList.size() > 0) {
			// 找到审批树，上面排过序了，这个顺序的结果可以直接用
			// 根据审批人的数量和级次展开树，形成最终和wfprocess审批流程树
			String superNodeId = null;
			int currentIndex = 0;
			for (ProcessEntity process : templateList) {
				String nodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
				if (0 == currentIndex) {
					List<WfProcess> generTreeFirstLevel = generTreeLevel(process, wfProcessParam, nodeId, superNodeId);
					list.addAll(generTreeFirstLevel);
					messageList.addAll(generTreeFirstLevel);// 只有第一级的所有人会收到消息
					superNodeId = nodeId;
				} else {
					List<WfProcess> generTreeOtherLevel = generTreeLevel(process, wfProcessParam, nodeId, superNodeId);
					list.addAll(generTreeOtherLevel);
					superNodeId = nodeId;
				}
				currentIndex++;
			}
		}
		int retNum = 0;
		if (list.size() > 0) {
			// 保存数据
			retNum = insert(list);
			// 发送消息
			sendMessage(messageList, messageList.get(0), "A", null);
		}
		return retNum;
	}
	public List<ProcessEntity> findTargetWfTreeByApprovalTemplate(WfProcessParam wfProcessParam,
			List<ProcessEntity> processEntityList) {
		String wfType = wfProcessParam.getApplyType();
		List<ProcessEntity> templateList = new ArrayList<ProcessEntity>();
		for (ProcessEntity processEntity : processEntityList) {
			if (WfApplyTypeEnum.SIGNATURE.name().equals(wfType) || WfApplyTypeEnum.EXPENSE.name().equals(wfType)) {
				// 流程结点上的天数比实际审请的金额大，就直接结束
				templateList.add(processEntity);
				if (processEntity.getApprovalAmount().floatValue() >= wfProcessParam.getApplyAmount().floatValue()) {
					break;
				}
			} else if (WfApplyTypeEnum.LEAVE.name().equals(wfType)) {
				// 流程结点上的天数比实际审请的天数大，就直接结束
				templateList.add(processEntity);
				if (processEntity.getApprovalDayNum().longValue() >= wfProcessParam.getApplyDayNum().intValue()) {
					break;
				}
			}
		}
		return templateList;
	}
	public List<WfProcess> generTreeLevel(ProcessEntity process, WfProcessParam wfProcessParam, String nodeId,
			String superNodeId) {
		List<WfProcess> wfList = Lists.newArrayList();
		String applyPerson = process.getApprover();
		List<String> splitToList = Splitter.on(",").splitToList(applyPerson);
		for (String applyId : splitToList) {
			WfProcess wfProcess = new WfProcess();
			wfProcess.setNodeId(nodeId);
			if (null == superNodeId) {
				wfProcess.setStatus(WfNodeStatus.PENDING.name());
			} else {
				wfProcess.setStatus(WfNodeStatus.INIT.name());
			}
			wfProcess.setSuperNodeId(superNodeId);
			wfProcess.setNodeType(wfProcessParam.getApplyType());
			wfProcess.setWfNature(process.getWfNature());
			Long isSign = 0l;
			if (WfApprovalTypeEnum.JOINTLY_SIGN.name().equals(process.getApprovalType().name())) {
				isSign = 1l;
			}
			wfProcess.setIsSign(isSign);
			wfProcess.setType(wfProcessParam.getApplyType());
			Employee employee = new Employee(Long.parseLong(String.valueOf(applyId)));
			wfProcess.setApproverEmployee(employee);
			wfProcess.setCreateTime(new Date());
			// wfProcess.setApproveTime(approveTime);
			// wfProcess.setApproveResult(approveResult);
			// wfProcess.setRemark(remark);
			wfProcess.setFormId(wfProcessParam.getFormId());
			// 会签
			// wfProcess.setApplyCode(applySequenceService.getSequence(WfApplyTypeEnum.SIGNATURE));
			wfProcess.setApplyCode(wfProcessParam.getApplyCode());
			wfProcess.setApplyTitle(wfProcessParam.getApplyTitle());
			wfProcess.setApplyUserId(wfProcessParam.getApplyUserId());
			wfList.add(wfProcess);
		}
		return wfList;
	}
	public int updateReadTime(Long processId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", processId);
		paramMap.put("readTime", new Date());
		return wfProcessDao.updateReadTime(paramMap);
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean wfOperation(WfProcess wfProcess) {
		Long id = wfProcess.getId();
		String nodeId = wfProcess.getNodeId();
		Long isSign = wfProcess.getIsSign();
		Long formId = wfProcess.getFormId();
		String approveResult = wfProcess.getApproveResult();
		String remark = wfProcess.getRemark();
		String wfType = wfProcess.getType();
		String superNodeId = wfProcess.getSuperNodeId();
		List<WfProcess> wfProcessList = getNextNode(nodeId, formId);
		List<WfProcess> currentLeveIsEnd = getCurrentLeveIsEnd(id, nodeId, formId, WfNodeStatus.PENDING.name());
		if (WfApproveResult.AGREE.name().equals(approveResult)) {// 同意
			if (!wfProcessList.isEmpty()) {
				updateStatusAGREE(id, formId, WfNodeStatus.COMPLETE.name(), approveResult, remark);
				if (isSign == 0) {
					WfProcess updateCurrentNode = new WfProcess();
					updateCurrentNode.setId(id);
					updateCurrentNode.setNodeId(nodeId);
					updateCurrentNode.setFormId(formId);
					wfProcessDao.updateCurrentLev(updateCurrentNode);
					WfProcess updateNext = new WfProcess();
					updateNext.setNodeId(nodeId);
					updateNext.setStatus(WfNodeStatus.PENDING.name());// 将下一个节点更新成待审批
					updateNext.setFormId(formId);// 节点ID
					wfProcessDao.update(updateNext);
				} else if (isSign == 1) {
					if (currentLeveIsEnd.isEmpty() || currentLeveIsEnd.size() <= 0) {
						WfProcess updateNext = new WfProcess();
						updateNext.setNodeId(nodeId);
						updateNext.setStatus(WfNodeStatus.PENDING.name());// 将下一个节点更新成待审批
						updateNext.setFormId(formId);// 节点ID
						wfProcessDao.update(updateNext);
					}
				}
				sendMessage(wfProcessList, wfProcessList.get(0), "A", "N");// 发送给下级提醒有待审批
			} else {// 无下级节点
				updateStatusAGREE(id, formId, WfNodeStatus.COMPLETE.name(), approveResult, remark);
				if (isSign == 0) {
					WfProcess updateCurrentNode = new WfProcess();
					updateCurrentNode.setId(id);
					updateCurrentNode.setNodeId(nodeId);
					updateCurrentNode.setFormId(formId);
					updateFormStatus(wfType, formId, ApplyStatus.ADOPT);
					updateApplyCcUserStatus(wfType, formId);
					wfProcessDao.updateCurrentLev(updateCurrentNode);
				} else if (currentLeveIsEnd.isEmpty()) {
					updateFormStatus(wfType, formId, ApplyStatus.ADOPT);
					updateApplyCcUserStatus(wfType, formId);
					insertToFinancailPayment(wfType, id, formId);
				}
				sendMessageForCCperson(wfType, formId);
			}
			sendMessage(wfProcessList, wfProcess, "C", null);
		} else if (WfApproveResult.REFUSE.name().equals(approveResult)) {// 拒绝
			WfProcess operCurent = new WfProcess();
			operCurent.setId(id);
			operCurent.setStatus(WfNodeStatus.COMPLETE.name());
			operCurent.setApproveTime(new Date());
			operCurent.setApproveResult(approveResult);
			operCurent.setRemark(remark);
			operCurent.setFormId(formId);// 节点ID
			wfProcessDao.update(operCurent);// 根据ID将当前操作人的节点更新成已审批
			WfProcess updateRefuse = new WfProcess();
			updateRefuse.setId(id);// id
			updateRefuse.setFormId(formId);// 节点ID
			updateRefuse.setStatus(WfNodeStatus.INVALIDATE.name());
			wfProcessDao.updateRefuse(updateRefuse);// 将下边所有节点更新成无效
			updateFormStatus(wfType, formId, ApplyStatus.REFUSE);
			sendMessage(wfProcessList, wfProcess, "C", null);
		} else if (WfApproveResult.TURN.name().equals(approveResult)) {
			Employee approverEmployee = wfProcess.getApproverEmployee();
			updateStatusAGREE(id, formId, WfNodeStatus.TURN.name(), approveResult, remark);
			ApplyCommon applyCommon = this.applyCommonService.getById(formId);
			String applyPerson = applyCommon.getApplyPerson();
			String applyPersonName = applyCommon.getApplyPersonName();
//			String applyPersonInfo = applyCommon.getApplyPersonInfo();
//
//			// 和原来的审批人拼接在一起
			String applyPersonNameNew = applyPersonName + "," + approverEmployee.getName();
			String applyPersonNew = applyPerson + "," + approverEmployee.getId();
//			// 更新审批人名称和id
			updateApplycommApprovePersons(formId, applyPersonNameNew, applyPersonNew);
			//udpateSourceApproveList(applyCommon, approverEmployee, -1); 
			WfProcess wfProcessOne = new WfProcess();
			String currentNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			generApplyCommWfProcessOne(applyCommon, nodeId, 0, String.valueOf(approverEmployee.getId()), wfProcessOne,
					currentNodeId);
			wfProcessOne.setSuperNodeId(nodeId);
			this.wfProcessDao.insert(wfProcessOne);
			// 更新下级节点的上级节点为当前的节点
			if (wfProcessList.size() > 0) {// 不是最后一个节点
				WfProcess wf = wfProcessList.get(0);
				wf.setSuperNodeId(currentNodeId);
				Map<String, Object> updateSuperNodeIdParam = Maps.newHashMap();
				updateSuperNodeIdParam.put("id", wf.getId());
				updateSuperNodeIdParam.put("superNodeId", currentNodeId);
				this.wfProcessDao.updateWfProcessSupserNodeId(updateSuperNodeIdParam);
			}
			sendMessage(Arrays.asList(wfProcessOne), wfProcessOne, "A", null);
			// 之前添加
		} else if (WfApproveResult.ADDBEFORE.name().equals(approveResult)) {
			ApplyCommon applyCommon = this.applyCommonService.getById(formId);
			WfProcess wfProcessOne = new WfProcess();
			String currentNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			String logWfNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			generApplyCommWfProcessOne(applyCommon, nodeId, 0, String.valueOf(wfProcess.getTargetUserId()),
					wfProcessOne, currentNodeId);
			wfProcessOne.setNodeId(currentNodeId);
			wfProcessOne.setSuperNodeId(logWfNodeId);
			// 2
			this.wfProcessDao.insert(wfProcessOne);
			// 将当前节点的状态回退到初始状态INIT,并更新当前节点的父节点为新插入的节点
			// 原数据放在日志记录之后,父NODEID为日志记录的NODEID
			// 3
			updateStatusBefore(id, formId, currentNodeId, "");
			// 更新通用审批的审批人
			updateApplycommApprovePersons(formId, wfProcess.getCurrentApprove());
			// 新插入的日志结点也生成nodeId
			// 1
			wfProcess.setApproveResult(approveResult);
			wfProcess.setApproveTime(new Date());
			// 和新的数据产生关联
			wfProcess.setNodeId(logWfNodeId);
			wfProcess.setSuperNodeId(superNodeId);
			wfProcess.setStatus(WfNodeStatus.ADD.name());
			this.wfProcessDao.insert(wfProcess);
			sendMessage(Arrays.asList(wfProcessOne), wfProcessOne, "A", null);
			// 之后添加
		} else if (WfApproveResult.ADDAFTER.name().equals(approveResult)) {
			ApplyCommon applyCommon = this.applyCommonService.getById(formId);
			String currentNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			String logWfNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			// 一定要先更新,新插入的数据也满足更新条件,
			if (wfProcessList.size() > 0) {// 不是最后一个节点
				WfProcess wf = wfProcessList.get(0);
				wf.setSuperNodeId(currentNodeId);
				Map<String, Object> updateNextNodeIdParam = Maps.newHashMap();
				// 根据当前节点的nodeId为条件,更新原下级节点的父节点为当前新插入的节点nodeId
				updateNextNodeIdParam.put("currentNodeId", currentNodeId);
				updateNextNodeIdParam.put("superNodeId", nodeId);// 更新条件
				this.wfProcessDao.updateNextNodeSupserNodeId(updateNextNodeIdParam);
			}
			WfProcess wfProcessOne = new WfProcess();
			generApplyCommWfProcessOne(applyCommon, nodeId, 1, String.valueOf(wfProcess.getTargetUserId()),
					wfProcessOne, currentNodeId);
			wfProcessOne.setNodeId(currentNodeId);
			wfProcessOne.setSuperNodeId(nodeId);
			this.wfProcessDao.insert(wfProcessOne);
			// 更新通用审批的审批人
			updateApplycommApprovePersons(formId, wfProcess.getCurrentApprove());
			// 更新之前下级结点的父结点ID
			// 1
			wfProcess.setApproveResult(approveResult);
			wfProcess.setApproveTime(new Date());
			wfProcess.setNodeId(logWfNodeId);
			wfProcess.setSuperNodeId(superNodeId);
			wfProcess.setStatus(WfNodeStatus.ADD.name());
			this.wfProcessDao.insert(wfProcess);
			// 2
			//
			// wfProcess.setSuperNodeId(logWfNodeId);
			// this.wfProcessDao.update(wfProcess);
			Map<String, Object> updateSuperNodeIdParam = Maps.newHashMap();
			updateSuperNodeIdParam.put("id", id);
			updateSuperNodeIdParam.put("superNodeId", logWfNodeId);
			this.wfProcessDao.updateWfProcessSupserNodeId(updateSuperNodeIdParam);
			// 之后添加不用给后面的人发送消息
		}
		return true;
	}
	private void udpateSourceApproveList(ApplyCommon applyCommon, Employee approverEmployee, int position) {
		String applyPersonInfo = applyCommon.getApplyPersonInfo();
		String applyPerson = applyCommon.getApplyPerson();
		String applyPersonNew = insertAddList(applyPerson, String.valueOf(approverEmployee.getId()), position);
		String ApplyPersonNameNew = insertAddList(applyCommon.getApplyPersonName(), approverEmployee.getName(), position);
		JavaType javaType = JsonUtils.normalMapper.getMapper().getTypeFactory().constructParametricType(List.class,
				Employee.class);
		List<Employee> approveList = (List<Employee>) JsonUtils.fromJson(applyPersonInfo, javaType);
		String approveJson = "[]";
		if(null != approveList && approveList.size() > 0) {
			int targetIndex = targetIndex(applyPerson);
			approveList.add(targetIndex - position, approverEmployee);
			approveJson = JsonUtils.pojoToJson(approveList);
		}
		ApplyCommon ac = new ApplyCommon();
		ac.setId(applyCommon.getId());
		ac.setApplyPersonName(ApplyPersonNameNew);
		ac.setApplyPerson(applyPersonNew);
		ac.setApplyPersonInfo(approveJson);
		this.applyCommonDao.update(ac);
		// 更新审批人名称和id
	}
	private String insertAddList(String sourceStr, String insertTagetStr, int position) {
		int targetIndex = targetIndex(sourceStr);
		String[] split = sourceStr.split(",");
		ArrayList<String> sourceList = new ArrayList<String>();
		Collections.addAll(sourceList, split);
		sourceList.add(targetIndex - position, insertTagetStr);
		String targetStr = org.apache.commons.lang3.StringUtils.join(sourceList, ",");
		return targetStr;
	}
	private int targetIndex(String sourceStr) {
		ShiroUser user = WebUtils.getLoggedUser();
		Long currentUserId = user.getId();
		List<String> sourceList = Splitter.on(",").splitToList(sourceStr);
		int targetIndex = sourceList.indexOf(String.valueOf(currentUserId));
		return targetIndex;
	}
	private void updateStatusAGREE(Long id, Long formId, String status, String approveResult, String remark) {
		WfProcess operCurent = new WfProcess();
		operCurent.setId(id);
		operCurent.setStatus(status);
		operCurent.setApproveTime(new Date());
		operCurent.setApproveResult(approveResult);
		operCurent.setFormId(formId);// 节点ID
		operCurent.setRemark(remark);
		wfProcessDao.update(operCurent);// 根据ID将当前操作人的节点更新成已审批
	}
	// 当前审批人的状态回退到初始
	private void updateStatusBefore(Long id, Long formId, String superNodeId, String remark) {
		WfProcess operCurent = new WfProcess();
		operCurent.setId(id);
		operCurent.setStatus(WfNodeStatus.INIT.name());
		operCurent.setSuperNodeId(superNodeId);
		operCurent.setFormId(formId);// 节点ID
		operCurent.setRemark(remark);
		wfProcessDao.update(operCurent);// 根据ID将当前操作人的节点更新成已审批
	}
	// 当前审批人的状态回退到初始
	private void updateApplycommApprovePersons(Long formId, String currentApprove) {
		List<LinkedHashMap<String, Object>> employees = JsonUtils.fromJson(currentApprove, List.class);
		List<Object> ids = Lists.newArrayList();
		List<Object> names = Lists.newArrayList();
		if (employees != null && employees.size() > 0) {
			for (LinkedHashMap employee : employees) {
				Object name = employee.get("name");
				Object id = employee.get("id");
				names.add(name);
				ids.add(id);
			}
		}
		String applyPersonName = org.apache.commons.lang3.StringUtils.join(names, ",");
		String applyPerson = org.apache.commons.lang3.StringUtils.join(ids, ",");
		updateApplycommApprovePersons(formId, applyPersonName, applyPerson);
	}
	private void updateApplycommApprovePersons(Long formId, String applyPersonName, String applyPerson) {
		ApplyCommon applyCommon = new ApplyCommon();
		applyCommon.setId(formId);
		applyCommon.setApplyPersonName(applyPersonName);
		applyCommon.setApplyPerson(applyPerson);
		this.applyCommonDao.update(applyCommon);
	}
	private void getChild(Map<String, WfProcess> listMap, List<WfProcess> orderList, String startKey) {
		WfProcess wf = listMap.get(startKey);
		if (wf == null) {
			return;
		}
		orderList.add(wf);
		startKey = wf.getNodeId();
		getChild(listMap, orderList, startKey);
	}
	private List<WfProcess> orderApproveList(List<WfProcess> approveDetail) {
		List<WfProcess> orderList = Lists.newArrayList();
		Map<String, WfProcess> orderMap = Maps.newHashMap();
		for (WfProcess wf : approveDetail) {
			String superNodeId = wf.getSuperNodeId();
			String nodeId = wf.getNodeId();
			// Object superId = map.get("super_node_id");
			// Object nodeId = map.get("node_id");
			if (superNodeId == null || nodeId.equals(superNodeId)) {
				orderList.add(wf);
			} else {
				orderMap.put(superNodeId, wf);
			}
		}
		if (orderList.size() > 0) {
			getChild(orderMap, orderList, orderList.get(0).getNodeId());
		}
		return orderList;
	}
	public List<WfProcess> findApproveDetailByFormId(Long formId, String type, String showType) {
		Map<String, Object> params = Maps.newHashMap();
		params.put("formId", formId);
		params.put("type", type);
		// if ("personal".equals(showType)) {
		// params.put("status", "'COMPLETE','PENDING','RESET','INIT'");
		// } else if ("approver".equals(showType)) {
		// params.put("status", "'COMPLETE','PENDING','INIT','RESET'");
		// }
		List<WfProcess> approveDetail = wfProcessDao.findApproveDetailByFormId(params);
		List<WfProcess> orderApproveList = orderApproveList(approveDetail);
		filterByStatus(orderApproveList);
		return orderApproveList;
	}
	public void filterByStatus(List<WfProcess> orderApproveList) {
		Iterator<WfProcess> iterator = orderApproveList.iterator();
		while (iterator.hasNext()) {
			WfProcess wf = iterator.next();
			String status = wf.getStatus();
			if (status.equals(WfNodeStatus.INVALIDATE.name())) {
				iterator.remove();
			}
		}
	}
	private List<ProcessEntity> findTemplateByCondition(Map<String, Object> paramMap) {
		List<ProcessEntity> executeWfTemplateList = processDao.findTemplateByCondition(paramMap);
		Collections.sort(executeWfTemplateList, new Comparator<ProcessEntity>() {
			@Override
			public int compare(ProcessEntity o1, ProcessEntity o2) {
				if (o1.getId() > o2.getId()) {
					return -1;
				}
				return 1;
			}
		});
		List<ProcessEntity> templateList = new ArrayList<ProcessEntity>();
		String tmpNature = "";
		Long tmpPid = 0L;
		for (ProcessEntity processEntity : executeWfTemplateList) {
			if (StringUtils.isBlank(tmpNature)) {
				tmpNature = processEntity.getWfNature().name();
				tmpPid = processEntity.getPid();
				templateList.add(processEntity);
			} else {
				if (tmpNature.equals(processEntity.getWfNature().name())) {
					if (processEntity.getId().intValue() == tmpPid.intValue()) {
						tmpPid = processEntity.getId();
						templateList.add(processEntity);
					}
				} else {
					tmpNature = processEntity.getWfNature().name();
					tmpPid = processEntity.getPid();
					templateList.add(processEntity);
				}
			}
		}
		return templateList;
	}
	private List<ProcessEntity> findExecuteTemplateByCondition(Map<String, Object> paramMap) {
		List<ProcessEntity> executeWfTemplateList = processDao.findExecuteTemplateByCondition(paramMap);
		List<ProcessEntity> executeTemplateList = new ArrayList<ProcessEntity>();
		Long tmpPid = 0L;
		for (ProcessEntity processEntity : executeWfTemplateList) {
			if (tmpPid.intValue() == 0) {
				tmpPid = processEntity.getPid();
				executeTemplateList.add(processEntity);
			}
			if (processEntity.getId().intValue() == tmpPid.intValue()) {
				tmpPid = processEntity.getPid();
				executeTemplateList.add(processEntity);
			}
		}
		return executeTemplateList;
	}
	private void insertToFinancailPayment(String applyType, Long wfId, Long formId) {
		if (applyType.equals(WfApplyTypeEnum.EXPENSE.name())) {
			SystemParam systemParam = systemParamDao.getByKey(CEO_AUTH_KEY);
			FinancailPayment financailPayment = new FinancailPayment();
			Payment payment = paymentDao.getById(formId);
			financailPayment.setTotalPrice(payment.getTotalMoney());
			financailPayment.setApplyUser(new Employee(payment.getCreateUser().getId()));
			financailPayment.setCompany(new MdniOrganization(payment.getCompany().getId()));
			financailPayment.setApplyType(WfApplyTypeEnum.EXPENSE);
			financailPayment.setBudgetMonth(payment.getPaymentDate());
			financailPayment.setApplyDate(payment.getCreateDate());
			financailPayment.setInvoiceTotal(0);// 发票总张数
			financailPayment.setWfProcessId(wfId);
			financailPayment.setFormId(formId);
			financailPayment.setWfProcessTittle(payment.getApplyTitle());// 申请标题
			financailPayment.setCreateTime(new Date());
			if ("N".equals(systemParam.getParamValue())) {
				financailPayment.setStatus(PaymentStatus.TOREIMBURSED);
			} else {
				financailPayment.setStatus(PaymentStatus.GRANT);
			}
			financailPaymentDao.insert(financailPayment);
		}
	}
	private int updateFormStatus(String wfType, Long formId, ApplyStatus applyStatus) {
		Map<String, String> params = new HashMap<String, String>();
		String tableName = updateFormData.get(wfType);
		params.put("tableName", tableName);
		params.put("status", applyStatus.name());
		params.put("id", String.valueOf(formId));
		// 如果拒绝的是有签报单的费用报销单，签报单的状态由使用中1回退为可用0
		if (WfApplyTypeEnum.EXPENSE.name().equals(wfType) && WfApproveResult.REFUSE.name().equals(applyStatus.name())) {
			Payment payment = paymentService.getDetails(formId);
			if (null != payment && null != payment.getPaymentSignatureId()) {
				signatureService.updateUseStaus(payment.getPaymentSignatureId(), 0);
			}
		}
		return wfProcessDao.updateFormTable(params);
	}
	private int updateApplyCcUser(String wfType, String ccUser, Long formId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", updateFormData.get(wfType));
		params.put("ccUser", "," + ccUser + ",");
		params.put("id", String.valueOf(formId));
		return wfProcessDao.updateApplyCcUser(params);
	}
	private int updateApplyCcUserStatus(String wfType, Long formId) {
		Map<String, String> params = new HashMap<String, String>();
		params.put("tableName", updateFormData.get(wfType));
		params.put("id", String.valueOf(formId));
		return wfProcessDao.updateApplyCcUserStatus(params);
	}
	private List<WfProcess> getNextNode(String nodeId, Long formId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("nodeId", nodeId);
		paramMap.put("formId", formId);
		return wfProcessDao.getNextNode(paramMap);
	}
	private List<WfProcess> getCurrentLeveIsEnd(Long id, String nodeId, Long formId, String status) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("id", id);
		paramMap.put("nodeId", nodeId);
		paramMap.put("formId", formId);
		paramMap.put("status", status);
		return wfProcessDao.getCurrentLeveIsEnd(paramMap);
	}
	private int createProcess(WfProcessParam wfProcessParam, List<ProcessEntity> templateMap) throws Exception {
		List<WfProcess> processList = new ArrayList<WfProcess>();
		int insertRowNum = 0;
		String superNodeId = null;
		String ccUser = "";
		boolean isApprovalContinue = false;
		boolean isExecuteContinue = false;
		if (!templateMap.isEmpty()) {
			for (int i = 0; i < templateMap.size(); i++) {
				ProcessEntity processEntity = templateMap.get(i);
				if (i == 0) {
					ccUser = processEntity.getCcUser();
				}
				if (isApprovalContinue && processEntity.getWfNature() == WfNatureEnum.APPROVAL) {
					continue;
				} else if (isExecuteContinue && processEntity.getWfNature() == WfNatureEnum.EXECUTE) {
					continue;
				}
				if (wfProcessParam.getGenertaeWfProcessType().longValue() == 1) {// 依据天数生成节点
					if (processEntity.getApprovalDayNum().longValue() >= wfProcessParam.getApplyDayNum().longValue()) {
						if (processEntity.getWfNature() == WfNatureEnum.APPROVAL) {
							isApprovalContinue = true;
						} else if (processEntity.getWfNature() == WfNatureEnum.EXECUTE) {
							isExecuteContinue = true;
						}
					}
				} else if (wfProcessParam.getGenertaeWfProcessType().longValue() == 2) {// 依据金额生成节点
					if (processEntity.getApprovalAmount().doubleValue() >= wfProcessParam.getApplyAmount()
							.doubleValue()) {
						if (processEntity.getWfNature() == WfNatureEnum.APPROVAL) {
							isApprovalContinue = true;
						} else if (processEntity.getWfNature() == WfNatureEnum.EXECUTE) {
							isExecuteContinue = true;
						}
					}
				} else if (wfProcessParam.getGenertaeWfProcessType().longValue() == 3) {// 依据天数及金额生成节点
					if ((processEntity.getApprovalDayNum().longValue() >= wfProcessParam.getApplyDayNum().longValue())
							&& (processEntity.getApprovalAmount().doubleValue() >= wfProcessParam.getApplyAmount()
									.doubleValue())) {
						if (processEntity.getWfNature() == WfNatureEnum.APPROVAL) {
							isApprovalContinue = true;
						} else if (processEntity.getWfNature() == WfNatureEnum.EXECUTE) {
							isExecuteContinue = true;
						}
					}
				}
				String curNodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
				if (StringUtils.isBlank(superNodeId)) {
					superNodeId = curNodeId;
				}
				WfProcess wfProcess = null;
				if (processEntity.getApprover() != null && processEntity.getApprover().indexOf(",") != -1) {
					String[] approverIds = processEntity.getApprover().split(",");
					for (String approverId : approverIds) {
						wfProcess = generateWfProcess(wfProcessParam, processEntity, curNodeId, approverId, superNodeId,
								i);
						processList.add(wfProcess);
					}
				} else {
					wfProcess = generateWfProcess(wfProcessParam, processEntity, curNodeId, processEntity.getApprover(),
							superNodeId, i);
					processList.add(wfProcess);
				}
				if (StringUtils.isNotBlank(superNodeId)) {
					superNodeId = curNodeId;
				}
			}
			if (StringUtils.isNotBlank(ccUser)) {// 更新抄送人
				updateApplyCcUser(wfProcessParam.getApplyType(), ccUser, wfProcessParam.getFormId());
			}
			insertRowNum = insert(processList);// 创建流程
			sendMessage(processList, processList.get(0), "A", null);
		}
		return insertRowNum;
	}
	private WfProcess generateWfProcess(WfProcessParam wfProcessParam, ProcessEntity processEntity, String curNodeId,
			String approverId, String superNodeId, int nodeNum) {
		WfProcess wfProcess = new WfProcess();
		wfProcess.setNodeId(curNodeId);
		wfProcess.setNodeType(processEntity.getWfType().name());
		wfProcess.setWfNature(processEntity.getWfNature());
		if (processEntity.getApprovalType() == WfApprovalTypeEnum.JOINTLY_SIGN) {// 会签
			wfProcess.setIsSign(1L);
		} else {
			wfProcess.setIsSign(0L);
		}
		wfProcess.setType(wfProcessParam.getApplyType());// 当前流程类型(出差,请假等)
		wfProcess.setCreateTime(new Date());// 创建日期
		wfProcess.setFormId(wfProcessParam.getFormId());// 表单ID
		wfProcess.setApplyCode(wfProcessParam.getApplyCode());
		wfProcess.setApplyTitle(wfProcessParam.getApplyTitle());// 申请标题
		wfProcess.setApplyUserId(wfProcessParam.getApplyUserId());// 申请人
		wfProcess.setApproverEmployee(new Employee(Long.parseLong(approverId)));
		if (nodeNum > 0) {
			wfProcess.setStatus(WfNodeStatus.INIT.name());
			wfProcess.setSuperNodeId(superNodeId);// 上级节点
		} else {
			wfProcess.setStatus(WfNodeStatus.PENDING.name());
		}
		return wfProcess;
	}
	@Transactional
	private int insert(List<WfProcess> processList) {
		return wfProcessDao.batchInsert(processList);
	}
	public List<WfProcess> createCommonWfProcess(ApplyCommon applyCommon, ShiroUser user) {
		List<WfProcess> list = Lists.newArrayList();
		String applyPerson = applyCommon.getApplyPerson();
		List<String> splitToList = Splitter.on(",").splitToList(applyPerson);
		String superNodeId = null;
		// 说明是修改,会涉及新节点的第一个父NODEID的问题
		if (applyCommon.getId() != null) {
			List<WfProcess> approveDetailList = this.findApproveDetailByFormId(applyCommon.getId(),
					WfApplyTypeEnum.COMMON.name(), "");
			if (null != approveDetailList && approveDetailList.size() > 0) {
				WfProcess wfProcess = approveDetailList.get(approveDetailList.size() - 1);
				superNodeId = wfProcess.getNodeId();
			}
			// 排好序了,取最后一个节点的id做为当前节点第一个的父节点
		}
		int currentIndex = 0;
		for (String applyId : splitToList) {
			WfProcess wfProcess = new WfProcess();
			String nodeId = UUIDUtils.generateJobNum().substring(0, 20).replaceAll("-", "");// 生成节点ID
			superNodeId = generApplyCommWfProcessOne(applyCommon, superNodeId, currentIndex, applyId, wfProcess,
					nodeId);
			list.add(wfProcess);
			currentIndex++;
		}
		insert(list);
		sendMessage(list, list.get(0), "A", null);
		return list;
	}
	private String generApplyCommWfProcessOne(ApplyCommon applyCommon, String superNodeId, int currentIndex,
			String applyId, WfProcess wfProcess, String nodeId) {
		wfProcess.setNodeId(nodeId);
		if (0 == currentIndex) {
			wfProcess.setStatus(WfNodeStatus.PENDING.name());
			wfProcess.setSuperNodeId(superNodeId);// 写死null了,返回的时候在写进去
			superNodeId = nodeId;
		} else {
			wfProcess.setStatus(WfNodeStatus.INIT.name());
			wfProcess.setSuperNodeId(superNodeId);
			superNodeId = nodeId;
		}
		wfProcess.setNodeType(WfApplyTypeEnum.COMMON.name());
		wfProcess.setWfNature(WfNatureEnum.APPROVAL);
		wfProcess.setIsSign(0L);
		wfProcess.setType(WfApplyTypeEnum.COMMON.name());
		Employee employee = new Employee(Long.parseLong(String.valueOf(applyId)));
		wfProcess.setApproverEmployee(employee);
		wfProcess.setCreateTime(new Date());
		// wfProcess.setApproveTime(approveTime);
		// wfProcess.setApproveResult(approveResult);
		// wfProcess.setRemark(remark);
		wfProcess.setFormId(applyCommon.getId());
		wfProcess.setApplyCode(applySequenceService.getSequence(WfApplyTypeEnum.COMMON));
		wfProcess.setApplyTitle(applyCommon.getTitle());
		wfProcess.setApplyUserId(Long.valueOf(applyCommon.getSubmitUser()));
		return superNodeId;
	}
	@Transactional(rollbackFor = Exception.class)
	public void updateReset(Long id) {
		this.applyCommonDao.updateReset(id);
		//所当前待审批的更新为reset
		this.wfProcessDao.updateReset(id);
		//把之后的所有未审批的单子变为失效
		this.wfProcessDao.updateNextReset(id);
		List<WfProcess> byFormId   = this.findApproveDetailByFormId(id, WfApplyTypeEnum.COMMON.name(), "");
//		List<WfProcess> byFormId = this.wfProcessDao.getByFormId(id);
		List<WfProcess> listWf = Lists.newArrayList();
		for (WfProcess wfProcess : byFormId) {
			if (wfProcess.getStatus().equals(WfNodeStatus.RESET.name())) {
				listWf.add(wfProcess);
				break;
			}
		}
		if (listWf.size() > 0) {
			ApplyCommon applyCommon = this.applyCommonDao.getById(id);
			Long applyUserId = listWf.get(0).getApproverEmployee().getId();
			User user = userService.getById(applyUserId);
			List<WechatUser> wechatUserList = wechatUserService.getByUserJobNum(Arrays.asList(user.getJobNum()));
			if (wechatUserList != null && wechatUserList.size() > 0) {
				messageManagerService.sendReCallTemplate(null, wechatUserList.get(0).getOpenid(), "通用申请撤回通知",
						applyCommon.getSubmitUserName(), applyCommon.getSubOrgName(), applyCommon.getSerialNumber(),
						"");
			}
		}
	}
	public void sendMessageForCCperson(String wfType, Long formId) {
		String ccPersonIds = "";
		String url = "";
		String msgType = "";
		if (wfType.equals(WfApplyTypeEnum.COMMON.name())) {
			ApplyCommon applyCommon = this.applyCommonDao.getById(formId);
			url = "/admin/commonApply/info?id=" + applyCommon.getId();
			ccPersonIds = applyCommon.getCcPerson();
			msgType = WfApplyTypeEnum.COMMON.getLabel();
		} else if (wfType.equals(WfApplyTypeEnum.SIGNATURE.name())) {
			Signature signature = this.signatureService.getById(formId);
			ccPersonIds = signature.getCcUser();
			url = "/api/signature/info?id=" + signature.getId();
			msgType = WfApplyTypeEnum.SIGNATURE.getLabel();
		} else if (wfType.equals(WfApplyTypeEnum.EXPENSE.name())) {
			Payment payment = this.paymentService.getById(formId);
			ccPersonIds = payment.getCcUser();
			url = "/api/payments/info?id=" + payment.getId();
			msgType = WfApplyTypeEnum.EXPENSE.getLabel();
		} else {
			throw new IllegalStateException(wfType + " error");
		}
		if (org.apache.commons.lang3.StringUtils.isBlank(ccPersonIds)) {
			return;
		}
		List<String> ids = Splitter.on(",").splitToList(ccPersonIds);// 处理ID
		if (null == ids || ids.size() == 0) {
			return;
		}
		List<Employee> employeeList = this.employeeService.getByUserJobNumForIDS(ids);
		List<String> jobNums = Lists.transform(employeeList, new Function<Employee, String>() {
			@Override
			public String apply(Employee employee) {
				return employee.getJobNum();
			}
		});
		String wxUrl = WXUtil.getOauthUrl(url);
		for (String jobNo : jobNums) {
			messageManagerService.sendCopyRemindTemplate(wxUrl, jobNo, "你有一条新的流程抄送提醒", msgType, "已完成",
					new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), "请点击详情进行查看");
		}
	}
	private void sendMessage(List<WfProcess> wfProcessList, WfProcess wfProcess, String sendPersonType, String flag) {
		if ("A".equals(sendPersonType)) {
			Map<Long, Long> wfProcessIdMap = new HashMap<Long, Long>();
			if (!wfProcessList.isEmpty() && wfProcessList.size() > 0) {
				if (wfProcessList.get(0).getId() == null) {
					Map<String, String> paramMap = new HashMap<String, String>();
					paramMap.put("nodeId", wfProcessList.get(0).getNodeId());
					paramMap.put("nodeType", wfProcessList.get(0).getNodeType());
					paramMap.put("wfNature", wfProcessList.get(0).getWfNature().name());
					paramMap.put("type", wfProcessList.get(0).getType());
					List<WfProcess> tmpWfProcess = wfProcessDao.getProcessByCondition(paramMap);
					tmpWfProcess.forEach(tmpWfProces1 -> {
						wfProcessIdMap.put(tmpWfProces1.getApproverEmployee().getId(), tmpWfProces1.getId());
					});
				} else {
					wfProcessList.forEach(tmpWfprocess2 -> {
						wfProcessIdMap.put(tmpWfprocess2.getApproverEmployee().getId(), tmpWfprocess2.getId());
					});
				}
				wfProcessList.forEach(tmpWfProcess -> {
					String status = tmpWfProcess.getStatus();
					if (WfNodeStatus.valueOf(status) == WfNodeStatus.PENDING || "N".equals(flag)) {
						SendParams sendParams = new SendParams();
						Long tmpApproverId = tmpWfProcess.getApproverEmployee().getId();
						sendParams.pushToUser(tmpApproverId);
						// String url = "/admin/approval/detail?id=" + wfProcessIdMap.get(tmpApproverId)
						// + "&type="
						// + wfProcessList.get(0).getType();
						// if (WfNodeStatus.valueOf(status) == WfNodeStatus.PENDING) {
						//
						// url += "&isApprove=true";
						// }
						String url = getWxUrl(wfProcess);
						sendParams.setUrl(WXUtil.getOauthUrl(url))
								.setFormId(wfProcessList.get(0).getFormId())
								.setWfType(WfApplyTypeEnum.valueOf(wfProcessList.get(0).getType()))// 流程类型
								.setIsApproverr(true)
								.setFromUserId(wfProcessList.get(0).getApplyUserId());
						messageManagerService.sendMessage(sendParams);
						// 保存消息日志
						saveWxMessage(sendParams);
					}
				});
			}
		} else if ("C".equals(sendPersonType)) {
			SendParams sendParams = new SendParams();
			String url = getWxUrl(wfProcess);
			// 微信多参数不好处理，改成和PC端一样的处理方式
			// String url = "/admin/businessAway/detailContainer?id=" +
			// wfProcess.getFormId() + "&type="
			// + wfProcess.getType() + "&applyUrl=" + tmpDetailUrl;
			sendParams.setUrl(WXUtil.getOauthUrl(url));
			if (wfProcessList == null || wfProcessList.isEmpty() || wfProcessList.size() <= 0) {
				sendParams.setFormId(wfProcess.getFormId()).setWfType(WfApplyTypeEnum.valueOf(wfProcess.getType()));
			} else {
				sendParams.setFormId(wfProcess.getFormId()).setWfType(WfApplyTypeEnum.valueOf(wfProcess.getType()));
			}
			sendParams.pushToUser(wfProcess.getApplyUserId())// 接收人
					.setIsApproverr(false)
					.setOpinion("AGREE".equals(wfProcess.getApproveResult()) ? "同意" : "拒绝")
					.setFromUserId(wfProcess.getApproverEmployee().getId());// 审批人
			messageManagerService.sendMessage(sendParams);
			// 保存消息日志
			saveWxMessage(sendParams);
		}
	}
	private String getWxUrl(WfProcess wfProcess) {
		String tmpDetailUrl = "";
		if (WfApplyTypeEnum.valueOf(wfProcess.getType()) == WfApplyTypeEnum.BUSINESS) {
			tmpDetailUrl = "/api/apply/applyBusinessAway/" + wfProcess.getFormId();
		} else if (WfApplyTypeEnum.valueOf(wfProcess.getType()) == WfApplyTypeEnum.LEAVE) {
			tmpDetailUrl = "/api/vacations/" + wfProcess.getFormId();
		} else if (WfApplyTypeEnum.valueOf(wfProcess.getType()) == WfApplyTypeEnum.SIGNATURE) {
			tmpDetailUrl = "/api/signature/info?id=" + wfProcess.getFormId();
		} else if (WfApplyTypeEnum.valueOf(wfProcess.getType()) == WfApplyTypeEnum.EXPENSE) {
			tmpDetailUrl = "/api/payments/info?id=" + wfProcess.getFormId();
		} else if (WfApplyTypeEnum.valueOf(wfProcess.getType()) == WfApplyTypeEnum.COMMON) {
			tmpDetailUrl = "/admin/commonApply/info?id=" + +wfProcess.getFormId();
		}
		return tmpDetailUrl;
	}
	private void saveWxMessage(SendParams sendParams) {
		try {
			WechatMessageLog wxMessageLog = new WechatMessageLog();
			wxMessageLog.setUrl(sendParams.getUrl());
			wxMessageLog.setFormId(sendParams.getFormId());
			wxMessageLog.setWfType(sendParams.getWfType().getLabel());
			wxMessageLog.setApproverr(sendParams.getIsApproverr() + "");
			wxMessageLog.setOpinion(sendParams.getOpinion());
			wxMessageLog.setFromUserId(sendParams.getFromUserId());
			List<ToUser> toUsers = sendParams.getToUsers();
			List<Long> userIds = Lists.newArrayList();
			for (ToUser user : toUsers) {
				userIds.add(user.getUserId());
			}
			wxMessageLog.setToUsers(Joiner.on(",").skipNulls().join(userIds));
			wxMessageLog.setCreateTime(new Date());
			wechatMessageLogService.insert(wxMessageLog);
		} catch (Exception ex) {
		}
	}
	public List<WfProcess> getByFormId(Long formId) {
		return wfProcessDao.getByFormId(formId);
	}
	public List<Map<String, Object>> getWfprocessByFormId(Long formId) {
		return wfProcessDao.getWfprocessByFormId(formId);
	}
	public List<Map<String, Object>> sourceData(Long id){
		return wfProcessDao.sourceData(id);
	}
}