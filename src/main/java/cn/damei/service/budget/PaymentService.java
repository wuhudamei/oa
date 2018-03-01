package cn.damei.service.budget;
import com.google.common.collect.Lists;
import com.lowagie.text.DocumentException;
import cn.damei.common.service.CrudService;
import cn.damei.dto.budget.PaymentCostDetailDto;
import cn.damei.dto.budget.PaymentCostItemDto;
import cn.damei.dto.budget.PaymentDto;
import cn.damei.entity.account.User;
import cn.damei.entity.budget.Payment;
import cn.damei.entity.budget.PaymentDetails;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.budget.SignatureDetails;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.process.ProcessEntity;
import cn.damei.enumeration.*;
import cn.damei.repository.budget.PaymentDao;
import cn.damei.repository.budget.PaymentDetailsDao;
import cn.damei.repository.process.ProcessDao;
import cn.damei.rest.commonapply.CreatePdf;
import cn.damei.rest.commonapply.PDFCell;
import cn.damei.rest.commonapply.WatermarkInfo;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.service.process.ProcessService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.reducing;
@SuppressWarnings("all")
@Service
public class PaymentService extends CrudService<PaymentDao, Payment> {
	@Autowired
	private PaymentDetailsDao paymentDetailsDao;
	@Autowired
	private PaymentDetailsService paymentDetailsService;
	@Autowired
	ApplySequenceService applySequenceService;
	@Autowired
	WfProcessService wfProcessService;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private YearBudgetService yearBudgetService;
	@Autowired
	private MdniDictionaryService dictionaryService;
	@Autowired
	private SignatureDetailsService signatureDetailsService;
	@Autowired
	private SignatureService signatureService;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private ProcessService processService;
	public String getApplyCode() {
		String applyCode = applySequenceService.getSequence(WfApplyTypeEnum.EXPENSE);
		return applyCode;
	}
	@Transactional(rollbackFor = Exception.class)
	public String createOrUpdate_(Payment payment, ShiroUser loggedUser, boolean isDraft) {
		String msg = "";
		try {
			List<PaymentDetails> paymentDetailsList = payment.getPaymentDetails();
			WfProcessParam wfProcessParam = insertWf(payment, loggedUser);
			Map<String, Object> paramMap = new HashMap<String, Object>();
			// paramMap.put("applyOrg", wfProcessParam.getOrgId());
			paramMap.put("wfType", wfProcessParam.getApplyType());
			paramMap.put("wfNature", WfNatureEnum.APPROVAL.name());
			List<ProcessEntity> processEntityList = processDao.findTemplateByConditionByDayOrMoney(paramMap);
			// 转换成父id的map方便下面按父id取值
			// ImmutableMap<Long, ProcessEntity> processEntityMap =
			// groupProcesByPid(processEntityList);
			// 从流程模板上，找到目标路径
			List<ProcessEntity> templateList = wfProcessService.findTargetWfTreeByApprovalTemplate(wfProcessParam,
					processEntityList);
			if (templateList.size() == 0) {
				return "该部门没有流程！";
			}
			// 设置申请部门，申请人
			if (isDraft) {
				payment.setStatus(ApplyStatus.DRAFT);
			} else {
				payment.setStatus(ApplyStatus.APPROVALING);
			}
			payment.setCompany(new MdniOrganization(loggedUser.getCompanyId()));
			payment.setUpdateUser(loggedUser.getId());
			payment.setUpdateDate(new Date());
			if (payment.getId() == null) {
				payment.setCreateUser(new User(loggedUser.getId()));
				payment.setCreateDate(new Date());
				this.entityDao.insert(payment);
			} else {
				this.entityDao.update(payment);
				// paymentDetailsDao.deleteBySignatureId(payment.getId());
			}
			// 构建并保存签报详情
			for (PaymentDetails details : paymentDetailsList) {
				details.setPaymentId(payment.getId());
			}
			paymentDetailsDao.batchInsert(paymentDetailsList);
			// 构建审批全路径，保存数据并且给第一级的审批人发送消息
			wfProcessParam.setFormId(payment.getId());
			int retNum = wfProcessService.generAllWfprocessTree(wfProcessParam, templateList);
		} catch (Exception e) {
			e.printStackTrace();
			msg = "操作异常！";
		}
		return msg;
	}
	@Transactional(rollbackFor = Exception.class)
	public String createOrUpdate(Payment payment, ShiroUser loggedUser, boolean isDraft) {
		String msg = "";
		List<PaymentDetails> paymentDetailsList = payment.getPaymentDetails();
		WfProcessParam wfProcessParam = insertWf(payment, loggedUser);
		List<ProcessEntity> allList = processService.disposeApprovalAndExecute(wfProcessParam);
		String disposeCcUser = processService.disposeCcUser(allList);
		if (allList.size() == 0) {
			return "该部门没有流程！";
		}
		if (isDraft) {
			payment.setStatus(ApplyStatus.DRAFT);
		} else {
			payment.setStatus(ApplyStatus.APPROVALING);
		}
		//
		payment.setCompany(new MdniOrganization(loggedUser.getCompanyId()));
		payment.setUpdateUser(loggedUser.getId());
		payment.setUpdateDate(new Date());
		if (payment.getId() == null) {
			payment.setCreateUser(new User(loggedUser.getId()));
			payment.setCreateDate(new Date());
			payment.setCcUser(disposeCcUser);
			this.entityDao.insert(payment);
			// 更新签报单的使用状态,为已使用
			if (null != payment.getPaymentSignatureId()) {
				signatureService.updateUseStaus(payment.getPaymentSignatureId(), 1);
			}
		} else {
			// 之前的签报单，并且和现在选择的不一样，处理原签报单的状态
			Payment sourcePayment = this.getById(payment.getId());
			if (null != sourcePayment.getPaymentSignatureId()
					&& !sourcePayment.getPaymentSignatureId().equals(payment.getPaymentSignatureId())) {
				// 原签报单的使用状态更新为0
				signatureService.updateUseStaus(sourcePayment.getPaymentSignatureId(), 0);
			}
			this.entityDao.update(payment);
			// 更新时，先删除原来的费用科目明细
			paymentDetailsDao.deleteByPaymentId(payment.getId());
		}
		// 构建并保存签报详情
		for (PaymentDetails details : paymentDetailsList) {
			details.setPaymentId(payment.getId());
		}
		// 先把原来的删除掉
		paymentDetailsDao.batchInsert(paymentDetailsList);
		//
		wfProcessParam.setFormId(payment.getId());
		int retNum = wfProcessService.generAllWfprocessTree(wfProcessParam, allList);
		return msg;
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean commit(Payment payment, ShiroUser loggedUser) {
		// 设置成审批中状态
		payment.setStatus(ApplyStatus.APPROVALING);
		this.entityDao.update(payment);
		try {
			insertWf(payment, loggedUser);
		} catch (Exception e) {
			logger.error("提交预算调用工作流失败！");
			throw new RuntimeException("提交预算调用工作流失败！");
		}
		return true;
	}
	public Payment getDetails(Long paymentId) {
		Payment payment = this.entityDao.getById(paymentId);
		if (payment == null) {
			return null;
		}
		List<PaymentDetails> paymentDetails = paymentDetailsDao.getByPaymentId(paymentId);
		payment.setPaymentDetails(paymentDetails);
		return payment;
	}
	public PaymentDto getMultiDetails(Long paymentId) {
		Payment payment = this.entityDao.getById(paymentId);
		if (payment == null || payment.getType() == null) {
			return null;
		}
		// 获取报销详情,并根据费用小类Id(costItemId)进行分组
		List<PaymentDetails> paymentItems = paymentDetailsDao.getByPaymentId(paymentId);
		Map<Long, List<PaymentDetails>> paymentDetailsMap = paymentItems.stream()
				.collect(groupingBy(PaymentDetails::getCostItemId));
		PaymentDto paymentDto = new PaymentDto();
		// 构建报销基础字段信息
		buildBaseBudgetDto(paymentDto, payment);
		// 构建费用小类
		if (paymentDetailsMap != null && paymentDetailsMap.size() > 0) {
			String thisYear = DateUtils.format(DateUtils.parse(payment.getPaymentDate(), "yyyy-MM"), "yyyy");
			Long thisType = payment.getType();
			Long companyId = payment.getCompany().getId();
			// 获取月份字符串
			String thisMonthShortStr = payment.getPaymentDate().substring(5);
			List<String> months = budgetService.twelveMonth.stream().filter((o) -> o.compareTo(thisMonthShortStr) <= 0)
					.collect(Collectors.toList());
			// 年度预算
			Map<Long, Map<String, Double>> yearBudgetMap = this.yearBudgetService.getYearBudgetByType(thisType,
					thisYear, companyId);
			// 全年月度预算的map key->小类id alue->(key是月份，value是当月预算)
			Map<Long, Map<String, Double>> monthBudgetMap = budgetService.getMonthBudgetTotalMap(thisYear, thisType,
					companyId);
			// 全年执行金额
			Map<Long, Double> thisYearExecutionMap = budgetService.getExecution(thisYear, thisType, companyId);
			// 月度预算
			Map<Long, Double> currentMonthBudget = budgetService.getMonthBudgetMap(payment.getPaymentDate(),
					payment.getType(), payment.getCompany().getId());
			// 本月实际执行金额
			Map<Long, Double> currentMonthExecution = budgetService.getExecution(payment.getPaymentDate(),
					payment.getType(), payment.getCompany().getId());
			for (Map.Entry<Long, List<PaymentDetails>> paymentItem : paymentDetailsMap.entrySet()) {
				// 获取费用小项及包含的 费用明细项
				Long costItemId = paymentItem.getKey();
				List<PaymentDetails> paymentDetails = paymentItem.getValue();
				if (paymentDetails != null && paymentDetails.size() > 0) {
					// 全年预算累计
					Double thisyearBudgetTotal = budgetService.getYearlBudgetTotal(costItemId, months,
							monthBudgetMap.get(costItemId), yearBudgetMap.get(costItemId));
					// 全年执行累计
					Double thisYearExecution = thisYearExecutionMap.get(costItemId) == null ? 0d
							: thisYearExecutionMap.get(costItemId);
					// 构建费用小项
					PaymentCostItemDto paymentCostItemDto = new PaymentCostItemDto().setCostItemId(costItemId)
							.setCostItemName(paymentDetails.get(0).getCostItemName())
							.setCostRemain(getRemainMoneyByCostItemId(yearBudgetMap, currentMonthBudget,
									currentMonthExecution, costItemId, thisMonthShortStr))
							.setYearRemain(thisyearBudgetTotal - thisYearExecution);
					// 构建费用明细信息
					for (PaymentDetails costDetail : paymentDetails) {
						paymentCostItemDto.pushCostDetail(PaymentCostDetailDto.build(costDetail));
					}
					paymentDto.pushCostItem(paymentCostItemDto);
				}
			}
		}
		return paymentDto;
	}
	public Double sumPaymentByCompany(Long companyId) {
		return this.entityDao.sumPaymentByCompany(companyId);
	}
	public Map<Long, Double> calculateRemain(Long signatureId, Long companyId, Long type, String month) {
		if (companyId == null || type == null || StringUtils.isEmpty(month)) {
			return Collections.EMPTY_MAP;
		}
		Map<Long, Double> remains = null;
		// 有签报单的返回该签报单金额，没有签报单返回本公司预算剩余金额
		if (signatureId != null) {
			remains = this.calculateSignatureRemain(signatureId, type);
		} else {
			remains = this.calculateBudgetRemain(companyId, type, month);
		}
		return remains;
	}
	private WfProcessParam insertWf(Payment payment, ShiroUser loggedUser) {
		// 调用工作流，启动流程
		WfProcessParam param = new WfProcessParam();
		param.setFormId(payment.getId());
		param.setApplyUserId(loggedUser.getId());
		param.setApplyTitle(payment.getApplyTitle());
		param.setApplyCode(payment.getApplyCode());
		param.setCompanyId(loggedUser.getCompanyId());
		param.setDepartmentId(loggedUser.getDepartmentId());
		param.setOrgId(loggedUser.getOrgId());
		param.setApplyType(WfApplyTypeEnum.EXPENSE.name());
		param.setProcessTypeId(payment.getType());
		param.setSubjectId(payment.getCostItem());
		param.setOverBudget(payment.getSurpassBudget());
		param.setGenertaeWfProcessType(2L);
		param.setApplyAmount(payment.getTotalMoney());
		return param;
		// wfProcessService.createWf(param);
	}
	private Map<Long, Double> calculateSignatureRemain(Long signatureId, Long type) {
		// 科目大类下的科目小类
		List<MdniDictionary> dictionaries = dictionaryService.getByParendCode(type, null);
		if (CollectionUtils.isNotEmpty(dictionaries)) {
			// 签报总金额
			Map<Long, Double> signatureMoneys = signatureDetailsService.getMoneyGroupByCostId(signatureId);
			//
			// Map<Long, Double> paymentMoneys =
			// paymentDetailsService.getOccupied(signatureId);
			//
			//
			// Map<Long, Double> remains = Maps.newHashMap();
			//
			// for (MdniDictionary dictionary : dictionaries) {
			// Double remain = this.calculateCostRemain(signatureMoneys, paymentMoneys,
			// dictionary.getId());
			// remains.put(dictionary.getId(), remain);
			// }
			// return remains;
			return signatureMoneys;
		}
		return Collections.emptyMap();
	}
	private Double calculateCostRemain(Map<Long, Double> signatureMoneys, Map<Long, Double> paymentMoneys,
			Long costId) {
		Double zero = 0d;
		Double signatureMoney = Optional.ofNullable(signatureMoneys).map(e -> e.get(costId)).orElse(zero);
		Double paymentMoney = Optional.ofNullable(paymentMoneys).map(e -> e.get(costId)).orElse(zero);
		return signatureMoney - paymentMoney;
	}
	private Map<Long, Double> calculateBudgetRemain(Long companyId, Long type, String month) {
		// 科目大类下的科目小类
		List<MdniDictionary> dictionaries = dictionaryService.getByParendCode(type, null);
		if (CollectionUtils.isNotEmpty(dictionaries)) {
			// 保存各科目小类预算剩余金额
			Map<Long, Double> remains = new HashMap<>();
			String thisYear = DateUtils.format(DateUtils.parse(month, "yyyy-MM"), "yyyy");
			// 获取月份字符串 如：2017-07 -> 07
			String thisMonthShortStr = month.substring(5);
			// 月度预算
			Map<Long, Double> thisMonthBudgets = budgetService.getMonthBudgetMap(month, type, companyId);
			// 年度预算
			Map<Long, Map<String, Double>> yearBudgets = this.yearBudgetService.getYearBudgetByType(type, thisYear,
					companyId);
			// 当月已经占用的
			Map<Long, Double> thisMonthOccupied = paymentDetailsService.getOccupied(companyId, type, month,
					ApplyStatus.APPROVALING, ApplyStatus.ADOPT);
			for (MdniDictionary dictionary : dictionaries) {
				Double remain = this.getRemainMoneyByCostItemId(yearBudgets, thisMonthBudgets, thisMonthOccupied,
						dictionary.getId(), thisMonthShortStr);
				remains.put(dictionary.getId(), remain);
			}
			return remains;
		}
		return Collections.emptyMap();
	}
	private Double getRemainMoneyByCostItemId(final Map<Long, Map<String, Double>> yearBudgetMoney,
			final Map<Long, Double> currentBudgetMoney, final Map<Long, Double> currentExecutionMoney,
			final Long costItemId, final String currentMonthStr) {
		Double zero = 0d;
		Double monthBudget = Optional.ofNullable(currentBudgetMoney).map(e -> e.get(costItemId)).orElse(zero);
		Double execution = Optional.ofNullable(currentExecutionMoney).map(e -> e.get(costItemId)).orElse(zero);
		Double yearBudget = Optional.ofNullable(yearBudgetMoney).map(e -> e.get(costItemId))
				.map(e -> e.get(currentMonthStr)).orElse(zero);
		return (monthBudget.equals(zero) ? yearBudget : monthBudget) - execution;
	}
	private void buildBaseBudgetDto(PaymentDto paymentDto, Payment payment) {
		paymentDto.setId(payment.getId());
		paymentDto.setApplyName(payment.getCreateUser().getName());
		paymentDto.setCompany(payment.getCompany().getOrgName());
		paymentDto.setApplyTitle(payment.getApplyTitle());
		paymentDto.setApplyCode(payment.getApplyCode());
		paymentDto.setSignatureId(payment.getSignatureId());
		paymentDto.setSignatureTitle(payment.getSignatureTitle());
		paymentDto.setPaymentDate(payment.getPaymentDate());
		paymentDto.setApplyDate(payment.getCreateDate());
		paymentDto.setTotalMoney(payment.getTotalMoney());
		paymentDto.setAttachment(payment.getAttachment());
		paymentDto.setInvoiceNum(payment.getInvoiceNum());
		paymentDto.setRemark(payment.getReason());
	}
	public String creataPdf(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			Payment payment = this.getById(id);
			String stateImgPath = execApplyCommonState(session, payment);
			String filePath = createPdfFile(id, payment);
			String newFileName = addWater(session, payment.getCreateUser().getName(), stateImgPath, filePath);
			// Files.deleteIfExists(Paths.get(filePath));
			return newFileName;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	// 申请单状态
	private String execApplyCommonState(HttpSession session, Payment payment) {
		String stateImgPath = session.getServletContext().getRealPath("/") + "/static/template/";
		if (payment.getStatus().equals(ApplyStatus.APPROVALING)) {
			stateImgPath += "03.png";
			if (StringUtils.isNoneEmpty(payment.getCurrentApproverName())) {
				payment.setCurrentApproverName("待[" + payment.getCurrentApproverName() + "]审批");
			}
		} else if (payment.getStatus().equals(ApplyStatus.REFUSE)) {// 通过
			stateImgPath += "02.png";
			payment.setCurrentApproverName("已拒绝");
		} else if (payment.getStatus().equals(ApplyStatus.ADOPT)) {// 通过
			stateImgPath += "01.png";
			payment.setCurrentApproverName("已通过");
		} else if (payment.getStatus().equals(ApplyStatus.RESET)) {// 通过
			payment.setCurrentApproverName("已撤回");
		}
		return stateImgPath;
	}
	// 生成pdf文件及构造PDF单元格对象
	private String createPdfFile(Long id, Payment payment) throws DocumentException, IOException {
		List<LinkedHashMap<String, Object>> resultList = execState(id);
		List<PDFCell> list = new ArrayList<PDFCell>();
		list.add(PDFCell.buildTitleCell(0, "报销申请详情"));
		list.add(PDFCell.buildNoBorderCell(1, payment.getDeptName() + " "+ payment.getCompany().getOrgName(), 3));
		list.add(PDFCell.buildNoBorderCell(2, "", 3));
		list.add(PDFCell.buildLabelCell(3, "申请标题："));
		list.add(PDFCell.buildValueCell(4, payment.getApplyTitle(), 2));
		list.add(PDFCell.buildLabelCell(5, "申请编码："));
		list.add(PDFCell.buildValueCell(6, payment.getApplyCode(), 2));
		list.add(PDFCell.buildLabelCell(7, "报销公司："));
		list.add(PDFCell.buildValueCell(8, payment.getCompany().getOrgName(), 2));
		list.add(PDFCell.buildLabelCell(9, "报销月份："));
		list.add(PDFCell.buildValueCell(10, payment.getPaymentDate(), 2));
		list.add(PDFCell.buildLabelCell(11, "提交人："));
		list.add(PDFCell.buildValueCell(12, payment.getCreateUser().getName(), 2));
		list.add(PDFCell.buildLabelCell(13, "申请时间："));
		list.add(PDFCell.buildValueCell(14, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(payment.getCreateDate()),
				2));
		list.add(PDFCell.buildLabelCell(15, "报销总额："));
		list.add(PDFCell.buildValueCell(16, payment.getTotalMoney(), 2));
		list.add(PDFCell.buildLabelCell(17, "票据总数："));
		list.add(PDFCell.buildValueCell(18, payment.getInvoiceNum(), 2));
		list.add(PDFCell.buildLabelCell(19, "费用申请单："));
		if (payment.getSignatureTitle() != null) {
			list.add(PDFCell.buildValueCell(20, payment.getSignatureTitle(), 3));
		} else {
			list.add(PDFCell.buildValueCell(20, "", 3));
		}
		list.add(PDFCell.buildLabelCell(21, "报销说明："));
		list.add(PDFCell.buildValueCell(22, payment.getReason(), 3));
		list.add(PDFCell.buildLabelCell(23, "审批过程："));
		list.add(PDFCell.buildValueCell(24, resultList, 5));
		list.add(PDFCell.buildValueCellBOLD(25, "费用明细：", 6));
		list.add(PDFCell.buildLabelCell(26, "报销金额："));
		list.add(PDFCell.buildLabelCell(27, "费用科目："));
		list.add(PDFCell.buildLabelCell(28, "科目明细："));
		list.add(PDFCell.buildLabelCell(29, "费用说明："));
		list.add(PDFCell.buildLabelCell(30, "票据张数："));
		list.add(PDFCell.buildLabelCell(31, "费用日期："));
		List<PaymentDetails> paymentDetailsList = paymentDetailsService.getByPaymentId(id);
		int index = 0;
		for (PaymentDetails paymentDetails : paymentDetailsList) {
			list.add(PDFCell.buildValueCell(32 + index, String.valueOf(paymentDetails.getMoney()), 1));
			list.add(PDFCell.buildValueCell(32 + index, paymentDetails.getCostItemName(), 1));
			list.add(PDFCell.buildValueCell(32 + index, paymentDetails.getCostDetailName(), 1));
			list.add(PDFCell.buildValueCell(32 + index, paymentDetails.getRemark(), 1));
			list.add(PDFCell.buildValueCell(32 + index, String.valueOf(paymentDetails.getInvoiceNum()), 1));
			list.add(PDFCell.buildValueCell(32 + index,
					new SimpleDateFormat("yyyy-MM-dd").format(paymentDetails.getCostDate()), 1));
			index++;
		}
		list.add(PDFCell.buildNoBorderCell(paymentDetailsList.size() * 6 + 1,
				"打印人：" + WebUtils.getLoggedUser().getName(), 3));
		list.add(PDFCell.buildNoBorderCell(paymentDetailsList.size() * 6 + 2,
				"打印时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 3));
		String fileName = String.format("common-%s.pdf", new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		String saveFilePath = uploadService.saveFilePath(UploadCategory.PDF, fileName);
		String filePath = new CreatePdf(6).generatePDFs(saveFilePath, list);
		return filePath;
	}
	// 计算审批状态
	private List<LinkedHashMap<String, Object>> execState(Long id) {
		List<Map<String, Object>> wfprocessByFormId = wfProcessService.getWfprocessByFormId(id);
		List<LinkedHashMap<String, Object>> resultList = new ArrayList<LinkedHashMap<String, Object>>();
		for (Map<String, Object> map : wfprocessByFormId) {
			LinkedHashMap<String, Object> linkMap = new LinkedHashMap<String, Object>();
			linkMap.put("name", map.get("name"));
			Object object = map.get("status");
			Object appResult = map.get("approve_result");
			String status = "";
			if (object.toString().equals("INIT")) {
				status = WfNodeStatus.INIT.getLabel();
			} else if (object.toString().equals(WfNodeStatus.PENDING.name())) {
				status = WfNodeStatus.PENDING.getLabel();
			} else if (object.toString().equals(WfNodeStatus.COMPLETE.name())) {
				status = WfNodeStatus.COMPLETE.getLabel();
			} else if (object.toString().equals(WfNodeStatus.RESET.name())) {
				status = WfNodeStatus.RESET.getLabel();
			}
			// 结果
			if (StringUtils.isNotBlank((String) appResult)
					&& ((String) appResult).equals(WfApproveResult.AGREE.name())) {
				// 通过
				status += WfApproveResult.AGREE.getLabel();
			}
			if (StringUtils.isNotBlank((String) appResult)
					&& ((String) appResult).equals(WfApproveResult.REFUSE.name())) {
				// 拒绝
				status += WfApproveResult.REFUSE.getLabel();
			}
			linkMap.put("status", status);
			linkMap.put("approve_time", map.get("approve_time"));
			linkMap.put("remark", map.get("remark"));
			resultList.add(linkMap);
		}
		return resultList;
	}
	// 添加水印
	private String addWater(HttpSession session, String creatName, String stateImgPath, String filePath)
			throws IOException, DocumentException {
		String newFileName = creatName + "报销申请" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".pdf";
		String saveFilePath = uploadService.saveFilePath(UploadCategory.PDF, newFileName);
		List<WatermarkInfo> watermarkInfoList = Lists.newArrayList();
		watermarkInfoList.add(new WatermarkInfo("大美装饰管理平台", 60, 620, 1, 0.1f));
		watermarkInfoList.add(new WatermarkInfo("大美装饰管理平台", 360, 480, 1, 0.1f));
		watermarkInfoList.add(new WatermarkInfo("大美装饰管理平台", 60, 280, 1, 0.1f));
		watermarkInfoList.add(new WatermarkInfo(
				session.getServletContext().getRealPath("/") + "/static/template/home.png", 40, 760, 0, 1f));
		if (stateImgPath.endsWith(".png")) {
			watermarkInfoList.add(new WatermarkInfo(stateImgPath, 430, 680, 0, 0.1f));
		}
		CreatePdf.imageWatermark(filePath, saveFilePath, watermarkInfoList);
		return saveFilePath;
	}
}