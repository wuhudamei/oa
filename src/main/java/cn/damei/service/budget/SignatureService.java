package cn.damei.service.budget;
import com.google.common.collect.Lists;
import com.lowagie.text.DocumentException;
import cn.damei.common.service.CrudService;
import cn.damei.dto.budget.SignatureCostDetailDto;
import cn.damei.dto.budget.SignatureCostItemDto;
import cn.damei.dto.budget.SignatureDto;
import cn.damei.entity.account.User;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.budget.SignatureDetails;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.entity.oa.WfProcessParam;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.entity.process.ProcessEntity;
import cn.damei.enumeration.*;
import cn.damei.repository.budget.SignatureDao;
import cn.damei.repository.budget.SignatureDetailsDao;
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
@Service
public class SignatureService extends CrudService<SignatureDao, Signature> {
	@Autowired
	private SignatureDetailsDao signatureDetailsDao;
	@Autowired
	private ApplySequenceService applySequenceService;
	@Autowired
	private WfProcessService wfProcessService;
	@Autowired
	private BudgetService budgetService;
	@Autowired
	private YearBudgetService yearBudgetService;
	@Autowired
	private MdniDictionaryService dictionaryService;
	@Autowired
	private SignatureDetailsService signatureDetailsService;
	@Autowired
	private SignatureDao signatureDao;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ProcessDao processDao;
	@Autowired
	private ProcessService processService;
	public String getApplyCode() {
		String applyCode = applySequenceService.getSequence(WfApplyTypeEnum.SIGNATURE);
		return applyCode;
	}
	@Transactional(rollbackFor = Exception.class)
	public String createOrUpdate(Signature signature, ShiroUser loggedUser, boolean isDraft) {
		String msg = "";
		try {
			List<SignatureDetails> signatureDetails = signature.getSignatureDetails();
			WfProcessParam wfProcessParam = insertWf(signature, loggedUser);
			List<ProcessEntity> allList = processService.disposeApprovalAndExecute(wfProcessParam);
			String disposeCcUser = processService.disposeCcUser(allList);
			if (allList.size() == 0) {
				return "该部门没有流程！";
			}
			if (isDraft) {
				signature.setStatus(ApplyStatus.DRAFT);
			} else {
				signature.setStatus(ApplyStatus.APPROVALING);
			}
			signature.setCompany(new MdniOrganization(loggedUser.getCompanyId()));
			signature.setUpdateUser(loggedUser.getId());
			signature.setUpdateDate(new Date());
			if (signature.getId() == null) {
				signature.setCreateUser(new User(loggedUser.getId()));
				signature.setCreateDate(new Date());
				signature.setCcUser(disposeCcUser);
				this.entityDao.insert(signature);
			} else {
				this.entityDao.update(signature);
				signatureDetailsDao.deleteBySignatureId(signature.getId());
			}
			// 构建并保存签报详情
			for (SignatureDetails details : signatureDetails) {
				details.setSignatureId(signature.getId());
			}
			signatureDetailsDao.batchInsert(signatureDetails);
			// 构建审批全路径，保存数据并且给第一级的审批人发送消息
			wfProcessParam.setFormId(signature.getId());
			wfProcessService.generAllWfprocessTree(wfProcessParam, allList);
			// 从流程模板上，找到目标路径
		} catch (Exception e) {
			e.printStackTrace();
			msg = "操作异常！";
		}
		return msg;
	}
	@Transactional(rollbackFor = Exception.class)
	public boolean commit(Signature signature, ShiroUser loggedUser) {
		// 设置成审批中状态
		signature.setStatus(ApplyStatus.APPROVALING);
		this.entityDao.update(signature);
		try {
			insertWf(signature, loggedUser);
		} catch (Exception e) {
			logger.error("提交签报单申请调用工作流失败！" + e.getMessage());
			throw new RuntimeException(e.getMessage());
		}
		return true;
	}
	public Signature getDetails(final Long signatureId) {
		Signature signature = this.entityDao.getById(signatureId);
		if (signature == null) {
			return null;
		}
		List<SignatureDetails> signatureDetails = signatureDetailsDao.getBySignatureId(signatureId);
		signature.setSignatureDetails(signatureDetails);
		return signature;
	}
	public SignatureDto getMultiDetails(final Long signatureId) {
		Signature signature = this.entityDao.getById(signatureId);
		if (signature == null || signature.getType() == null) {
			return null;
		}
		// 获取签报明细,并根据费用小类Id(costItemId)进行分组
		List<SignatureDetails> signatureDetails = signatureDetailsDao.getBySignatureId(signatureId);
		Map<Long, List<SignatureDetails>> signatureDetailsMap = signatureDetails.stream()
				.collect(groupingBy(SignatureDetails::getCostItemId));
		SignatureDto signatureDto = new SignatureDto();
		// 构建签报基础字段信息
		buildBaseSignatureDto(signatureDto, signature);
		// 构建费用小类
		if (signatureDetailsMap != null && signatureDetailsMap.size() > 0) {
			String thisYear = DateUtils.format(DateUtils.parse(signature.getSignatureDate(), "yyyy-MM"), "yyyy");
			Long thisType = signature.getType();
			Long companyId = signature.getCompany().getId();
			// 获取月份字符串
			String thisMonthShortStr = signature.getSignatureDate().substring(5);
			List<String> months = BudgetService.twelveMonth.stream().filter((o) -> o.compareTo(thisMonthShortStr) <= 0)
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
			Map<Long, Double> currentMonthBudget = budgetService.getMonthBudgetMap(signature.getSignatureDate(),
					signature.getType(), signature.getCompany().getId());
			// 本月实际执行金额
			Map<Long, Double> currentMonthExecution = budgetService.getExecution(signature.getSignatureDate(),
					signature.getType(), signature.getCompany().getId());
			for (Map.Entry<Long, List<SignatureDetails>> signatureItem : signatureDetailsMap.entrySet()) {
				// 获取费用小项及包含的 费用明细项
				Long costItemId = signatureItem.getKey();
				List<SignatureDetails> details = signatureItem.getValue();
				if (details != null && details.size() > 0) {
					// 全年预算累计
					Double thisyearBudgetTotal = budgetService.getYearlBudgetTotal(costItemId, months,
							monthBudgetMap.get(costItemId), yearBudgetMap.get(costItemId));
					// 全年执行累计
					Double thisYearExecution = thisYearExecutionMap.get(costItemId) == null ? 0d
							: thisYearExecutionMap.get(costItemId);
					// 构建费用小项
					SignatureCostItemDto paymentCostItemDto = new SignatureCostItemDto().setCostItemId(costItemId)
							.setCostItemName(details.get(0).getCostItemName())
							.setCostRemain(getRemainMoneyByCostItemId(yearBudgetMap, currentMonthBudget,
									currentMonthExecution, costItemId, thisMonthShortStr))
							.setYearRemain(thisyearBudgetTotal - thisYearExecution);
					// 构建费用明细信息
					for (SignatureDetails costDetail : details) {
						paymentCostItemDto.pushCostDetail(SignatureCostDetailDto.build(costDetail));
					}
					signatureDto.pushCostItem(paymentCostItemDto);
				}
			}
		}
		return signatureDto;
	}
	public Map<Long, Double> calculateBudgetRemain(final Long companyId, final Long type, final String signatureDate) {
		if (companyId == null || type == null || StringUtils.isEmpty(signatureDate)) {
			return Collections.EMPTY_MAP;
		}
		// 科目大类下的科目小类
		List<MdniDictionary> dictionaries = dictionaryService.getByParendCode(type, null);
		if (CollectionUtils.isNotEmpty(dictionaries)) {
			// 保存各科目小类预算剩余金额
			Map<Long, Double> remains = new HashMap<>();
			String thisYear = DateUtils.format(DateUtils.parse(signatureDate, "yyyy-MM"), "yyyy");
			// 获取月份字符串 如：2017-07 -> 07
			String thisMonthShortStr = signatureDate.substring(5);
			// 年度预算
			Map<Long, Map<String, Double>> yearBudgets = this.yearBudgetService.getYearBudgetByType(type, thisYear,
					companyId);
			// 月度预算
			Map<Long, Double> thisMonthBudgets = budgetService.getMonthBudgetMap(signatureDate, type, companyId);
			// 当月已经占用的
			Map<Long, Double> thisMonthExecutions = signatureDetailsService.getOccupied(companyId, type, signatureDate,
					ApplyStatus.APPROVALING, ApplyStatus.ADOPT);
			for (MdniDictionary dictionary : dictionaries) {
				Double remain = getRemainMoneyByCostItemId(yearBudgets, thisMonthBudgets, thisMonthExecutions,
						dictionary.getId(), thisMonthShortStr);
				remains.put(dictionary.getId(), remain);
			}
			return remains;
		}
		return Collections.EMPTY_MAP;
	}
	public List<Signature> getSignaturesByMonthAndUser(final Long userId, final String month, Long paymentId) {
		if (userId == null || StringUtils.isEmpty(month)) {
			return Collections.emptyList();
		}
		return this.entityDao.findByMonthAndUser(userId, month, Lists.newArrayList(ApplyStatus.ADOPT), paymentId);
	}
	private WfProcessParam insertWf(final Signature signature, final ShiroUser loggedUser) {
		// 调用工作流，启动流程
		WfProcessParam param = new WfProcessParam();
		param.setFormId(signature.getId());
		param.setApplyUserId(loggedUser.getId());
		param.setApplyTitle(signature.getApplyTitle());
		param.setApplyCode(signature.getApplyCode());
		param.setCompanyId(loggedUser.getCompanyId());
		param.setDepartmentId(loggedUser.getDepartmentId());
		param.setOrgId(loggedUser.getOrgId());
		param.setApplyType(WfApplyTypeEnum.SIGNATURE.name());
		param.setOverBudget(signature.getSurpassBudget());
		param.setProcessTypeId(signature.getType());
		param.setSubjectId(signature.getCostItem());
		param.setGenertaeWfProcessType(2L);
		param.setApplyAmount(signature.getTotalMoney());
		return param;
		// wfProcessService.createWfDayOrMoney(param);
	}
	private Double getRemainMoneyByCostItemId(final Map<Long, Map<String, Double>> yearBudgetMoney,
			final Map<Long, Double> currentBudgetMoney, final Map<Long, Double> currentExecutionMoney,
			final Long costItemId, final String currentMonthStr) {
		Double zero = 0d;
		Double monthBudget = Optional.ofNullable(currentBudgetMoney).map(e -> e.get(costItemId)).orElse(zero);
		Double yearBudget = Optional.ofNullable(yearBudgetMoney).map(e -> e.get(costItemId))
				.map(e -> e.get(currentMonthStr)).orElse(zero);
		Double execution = Optional.ofNullable(currentExecutionMoney).map(e -> e.get(costItemId)).orElse(zero);
		return (monthBudget.equals(zero) ? yearBudget : monthBudget) - execution;
	}
	private void buildBaseSignatureDto(final SignatureDto signatureDto, final Signature signature) {
		signatureDto.setId(signature.getId());
		signatureDto.setApplyName(signature.getCreateUser().getName());
		signatureDto.setCompany(signature.getCompany().getOrgName());
		signatureDto.setApplyTitle(signature.getApplyTitle());
		signatureDto.setApplyCode(signature.getApplyCode());
		signatureDto.setAttachment(signature.getAttachment());
		signatureDto.setSignatureDate(signature.getSignatureDate());
		signatureDto.setApplyDate(signature.getCreateDate());
		signatureDto.setTotalMoney(signature.getTotalMoney());
		signatureDto.setRemark(signature.getReason());
	}
	// 更新签报单的使用状态
	public void updateUseStaus(Long signatureId, Integer targetValue) {
		this.signatureDao.updateUseStaus(signatureId, targetValue);
	}
	public String creataPdf(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			Signature signature = this.getById(id);
			String stateImgPath = execApplyCommonState(session, signature);
			String filePath = createPdfFile(id, signature);
			String newFileName = addWater(session, signature.getCreateUser().getName(), stateImgPath, filePath);
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
	private String execApplyCommonState(HttpSession session, Signature signature) {
		String stateImgPath = session.getServletContext().getRealPath("/") + "/static/template/";
		if (signature.getStatus().equals(ApplyStatus.APPROVALING)) {
			stateImgPath += "03.png";
			if (StringUtils.isNoneEmpty(signature.getCurrentApproverName())) {
				signature.setCurrentApproverName("待[" + signature.getCurrentApproverName() + "]审批");
			}
		} else if (signature.getStatus().equals(ApplyStatus.REFUSE)) {// 通过
			stateImgPath += "02.png";
			signature.setCurrentApproverName("已拒绝");
		} else if (signature.getStatus().equals(ApplyStatus.ADOPT)) {// 通过
			stateImgPath += "01.png";
			signature.setCurrentApproverName("已通过");
		} else if (signature.getStatus().equals(ApplyStatus.RESET)) {// 通过
			signature.setCurrentApproverName("已撤回");
		}
		return stateImgPath;
	}
	// 生成pdf文件及构造PDF单元格对象
	private String createPdfFile(Long id, Signature signature) throws DocumentException, IOException {
		List<LinkedHashMap<String, Object>> resultList = execState(id);
		List<PDFCell> list = new ArrayList<PDFCell>();
		list.add(PDFCell.buildTitleCell(0, "签报申请详情"));
		list.add(PDFCell.buildNoBorderCell(1, signature.getCompanyName() +" "+signature.getCompany().getOrgName(), 2));
		list.add(PDFCell.buildNoBorderCell(2, "", 2));
		list.add(PDFCell.buildLabelCell(3, "申请标题："));
		list.add(PDFCell.buildValueCell(4, signature.getApplyTitle(), 1));
		list.add(PDFCell.buildLabelCell(5, "申请编码："));
		list.add(PDFCell.buildValueCell(6, signature.getApplyCode(), 1));
		list.add(PDFCell.buildLabelCell(7, "费用承担公司："));
		list.add(PDFCell.buildValueCell(8, signature.getCompany().getOrgName(), 1));
		list.add(PDFCell.buildLabelCell(9, "费用申请月份："));
		list.add(PDFCell.buildValueCell(10, signature.getSignatureDate(), 1));
		list.add(PDFCell.buildLabelCell(11, "提交人："));
		list.add(PDFCell.buildValueCell(12, signature.getCreateUser().getName(), 1));
		list.add(PDFCell.buildLabelCell(13, "申请时间："));
		list.add(PDFCell.buildValueCell(14,
				new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(signature.getCreateDate()), 1));
		list.add(PDFCell.buildLabelCell(15, "费用总额："));
		list.add(PDFCell.buildValueCell(16, signature.getTotalMoney(), 1));
		list.add(PDFCell.buildLabelCell(17, "费用说明："));
		list.add(PDFCell.buildValueCell(18, signature.getReason(), 1));
		list.add(PDFCell.buildLabelCell(19, "审批过程："));
		list.add(PDFCell.buildValueCell(20, resultList, 3));
		list.add(PDFCell.buildValueCellBOLDForSize(21, "费用明细", 4, 12));
		list.add(PDFCell.buildLabelCell(22, "费用科目"));
		list.add(PDFCell.buildLabelCell(23, "科目明细"));
		list.add(PDFCell.buildLabelCell(24, "费用金额"));
		list.add(PDFCell.buildValueCellBOLD(25, "费用说明", 1));
		List<SignatureDetails> signatureDetailsList = signatureDetailsService.getDetailsBySignatureId(id);
		int index = 0;
		for (SignatureDetails signatureDetails : signatureDetailsList) {
			list.add(PDFCell.buildLabelCellNORMAL(26 + index, signatureDetails.getCostItemName()));
			list.add(PDFCell.buildLabelCellNORMAL(26 + index, signatureDetails.getCostDetailName()));
			list.add(PDFCell.buildLabelCellNORMAL(26 + index, String.valueOf(signatureDetails.getMoney())));
			list.add(PDFCell.buildLabelCellNORMAL(26 + index, signatureDetails.getRemark()));
			index++;
		}
		list.add(PDFCell.buildNoBorderCell(signatureDetailsList.size() * 4 + 1,
				"打印人：" + WebUtils.getLoggedUser().getName(), 2));
		list.add(PDFCell.buildNoBorderCell(signatureDetailsList.size() * 4 + 2,
				"打印时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()), 2));
		String fileName = String.format("common-%s.pdf", new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		String saveFilePath = uploadService.saveFilePath(UploadCategory.PDF, fileName);
		String filePath = new CreatePdf(4).generatePDFs(saveFilePath, list);
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
		String newFileName = creatName + "签报申请" + new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".pdf";
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