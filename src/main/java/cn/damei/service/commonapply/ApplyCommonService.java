package cn.damei.service.commonapply;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import cn.damei.common.service.CrudService;
import cn.damei.entity.commonapply.ApplyCommon;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.UploadCategory;
import cn.damei.enumeration.WfApproveResult;
import cn.damei.enumeration.WfNodeStatus;
import cn.damei.repository.commonapply.ApplyCommonDao;
import cn.damei.rest.commonapply.CreatePdf;
import cn.damei.rest.commonapply.PDFCell;
import cn.damei.rest.commonapply.WatermarkInfo;
import cn.damei.service.oa.WfProcessService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
@Service
public class ApplyCommonService extends CrudService<ApplyCommonDao, ApplyCommon> {
	@Autowired
	private WfProcessService wfProcessService;
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ApplyCommonDao applyCommonDao;
	@Transactional(rollbackFor = Exception.class)
	public void saveApplyCommon(ApplyCommon applyCommon, ShiroUser user) {
		this.insert(applyCommon);
		this.wfProcessService.createCommonWfProcess(applyCommon, user);
	}
	@Transactional(rollbackFor = Exception.class)
	public void updateApplyCommon(ApplyCommon applyCommon, ShiroUser user) {
		this.update(applyCommon);
		this.wfProcessService.createCommonWfProcess(applyCommon, user);
	}
	public void updateCCuserAdd(ApplyCommon applyCommon) {
		this.update(applyCommon);
	}
	public ApplyCommon getInfoById(Long id) {
		ApplyCommon applyCommon = this.getById(id);
		return applyCommon;
	}
	public String creataPdf(Long id, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		try {
			ApplyCommon applyCommon = this.getInfoById(id);
			String stateImgPath = execApplyCommonState(session, applyCommon);
			String filePath = createPdfFile(id, applyCommon);
			String newFileName = addWater(session, applyCommon, stateImgPath, filePath);
			// Files.deleteIfExists(Paths.get(filePath));
			return newFileName;
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
	// 生成pdf文件及构造PDF单元格对象
	private String createPdfFile(Long id, ApplyCommon applyCommon) throws DocumentException, IOException {
		List<LinkedHashMap<String, Object>> resultList = execState(id);
		List<PDFCell> list = new ArrayList<PDFCell>();
		list.add(PDFCell.buildTitleCell(0, "通用审批"));
		list.add(PDFCell.buildNoBorderCell(1, applyCommon.getOrgName() + " " + applyCommon.getSubOrgName(), 3));
		list.add(PDFCell.buildNoBorderCell(2, applyCommon.getApplyTimeHMS(), 3));
		list.add(PDFCell.buildLabelCell(3, "申请编号："));
		list.add(PDFCell.buildValueCell(4, applyCommon.getSerialNumber(), 5));
		list.add(PDFCell.buildLabelCell(5, "申请标题："));
		list.add(PDFCell.buildValueCell(6, applyCommon.getTitle(), 5));
		list.add(PDFCell.buildLabelCell(7, "申请人："));
		list.add(PDFCell.buildValueCell(8, applyCommon.getSubmitUserName(), 5));
		list.add(PDFCell.buildLabelCell(9, "申请部门："));
		list.add(PDFCell.buildValueCell(10, applyCommon.getSubOrgName(), 5));
		list.add(PDFCell.buildLabelCell(11, "申请内容："));
		list.add(PDFCell.buildValueCell(12, applyCommon.getContent(), 5));
		list.add(PDFCell.buildLabelCell(13, "审批过程："));
		list.add(PDFCell.buildValueCell(14, resultList, 5));
		list.add(PDFCell.buildLabelCell(15, "审批状态："));
		list.add(PDFCell.buildValueCell(16, applyCommon.getWaitApplyPerson(), 5));
		list.add(PDFCell.buildNoBorderCell(17, "打印人：" + WebUtils.getLoggedUser().getName(), 3));
		list.add(PDFCell.buildNoBorderCell(17, "打印时间：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()),
				3));
		String fileName = String.format("common-%s.pdf", new SimpleDateFormat("yyMMddHHmmss").format(new Date()));
		String saveFilePath = uploadService.saveFilePath(UploadCategory.PDF, fileName);
		String filePath = new CreatePdf(6).generatePDFs(saveFilePath, list);
		return filePath;
	}
	// 申请单状态
	private String execApplyCommonState(HttpSession session, ApplyCommon applyCommon) {
		String stateImgPath = session.getServletContext().getRealPath("/") + "/static/template/";
		if (applyCommon.getStatus().equals(ApplyStatus.APPROVALING.name())) {
			stateImgPath += "03.png";
			if (StringUtils.isNoneEmpty(applyCommon.getWaitApplyPerson())) {
				applyCommon.setWaitApplyPerson("待[" + applyCommon.getWaitApplyPerson() + "]审批");
			}
		} else if (applyCommon.getStatus().equals(ApplyStatus.REFUSE.name())) {// 通过
			stateImgPath += "02.png";
			applyCommon.setWaitApplyPerson("已拒绝");
		} else if (applyCommon.getStatus().equals(ApplyStatus.ADOPT.name())) {// 通过
			stateImgPath += "01.png";
			applyCommon.setWaitApplyPerson("已通过");
		} else if (applyCommon.getStatus().equals(ApplyStatus.RESET.name())) {// 通过
			applyCommon.setWaitApplyPerson("已撤回");
		}
		return stateImgPath;
	}
	private void getChild(Map<String, Map<String, Object>> listMap, List<Map<String, Object>> orderList,
			String startKey) {
		Map<String, Object> map = listMap.get(startKey);
		if (map == null) {
			return;
		}
		orderList.add(map);
		startKey = map.get("node_id").toString();
		getChild(listMap, orderList, startKey);
	}
	private List<Map<String, Object>> orderApproveList(List<Map<String, Object>> queryForList) {
		List<Map<String, Object>> orderList = Lists.newArrayList();
		Map<String, Map<String, Object>> orderMap = Maps.newHashMap();
		for (Map<String, Object> map : queryForList) {
			Object superId = map.get("super_node_id");
			Object nodeId = map.get("node_id");
			if (superId == null || superId.equals(nodeId)) {
				orderList.add(map);
			} else {
				orderMap.put(superId.toString(), map);
			}
		}
		if (orderList.size() > 0) {
			getChild(orderMap, orderList, orderList.get(0).get("node_id").toString());
		}
		return orderList;
	}
	public void filterByStatus(List<Map<String, Object>> orderApproveList) {
		Iterator<Map<String, Object>> iterator = orderApproveList.iterator();
		while (iterator.hasNext()) {
			Map<String, Object> itMap = iterator.next();
			if (itMap.containsKey("status")) {
				Object statusObj = itMap.get("status");
				if (null != statusObj) {
					String statusStr = statusObj.toString();
					if (statusStr.equals(WfNodeStatus.INVALIDATE.name())) {
						iterator.remove();
					}
				}
			}
		}
	}
	// 计算审批状态
	private List<LinkedHashMap<String, Object>> execState(Long id) {
		List<Map<String, Object>> wfprocessByForm = wfProcessService.getWfprocessByFormId(id);
		// 手工排序
		List<Map<String, Object>> orderApproveList = orderApproveList(wfprocessByForm);
		filterByStatus(orderApproveList); 
		List<LinkedHashMap<String, Object>> resultList = new ArrayList<LinkedHashMap<String, Object>>();
		for (Map<String, Object> map : orderApproveList) {
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
			} else if (object.toString().equals(WfNodeStatus.ADD.name())) {
				status = WfNodeStatus.ADD.getLabel();
			} else if (object.toString().equals(WfNodeStatus.TURN.name())) {
				status = WfNodeStatus.TURN.getLabel();
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
	private String addWater(HttpSession session, ApplyCommon applyCommon, String stateImgPath, String filePath)
			throws IOException, DocumentException {
		String newFileName = applyCommon.getSubmitUserName() + "通用审批"
				+ new SimpleDateFormat("yyMMddHHmmss").format(new Date()) + ".pdf";
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
	public Map<String, Object> sourceData(Long id){
		return this.applyCommonDao.sourceData(id);
	}
	public int del(Long id) {
		return this.applyCommonDao.deleteById(id);
	}
}
