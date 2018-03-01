package cn.damei.rest.commonapply;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.UUID;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.pdf.BaseFont;
import cn.damei.common.BaseController;
import cn.damei.common.service.ServiceException;
import cn.damei.common.view.ViewDownLoad;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.commonapply.ApplyCommon;
import cn.damei.entity.oa.WfProcess;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.UploadCategory;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfApproveResult;
import cn.damei.enumeration.WfNodeStatus;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.commonapply.ApplyCommonService;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtil;
import cn.damei.utils.MapUtils;
import cn.damei.utils.PDFUtil;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/applyCommon")
public class ApplyCommonController extends BaseController {
	@Autowired
	private UploadService uploadService;
	@Autowired
	private ApplyCommonService applyCommonService;
	@Autowired
	private ApplySequenceService applySequenceService;
	@Autowired
	private WfProcessService wfProcessService;
	@Autowired
	private SignatureService signatureService;
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/list")
	public Object mdnOrderList(@RequestParam(required = false) ApplyStatus status,
			@RequestParam(required = false) String dataType,
			@RequestParam(required = false) String type,
			@RequestParam(required = false) String keyword,
			@RequestParam(defaultValue = "0") int offset, @RequestParam(defaultValue = "20") int limit,
			@RequestParam(defaultValue = "last_update") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort) {
		Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
		Map<String, Object> params = Maps.newHashMap();
		// MapUtils.putNotNull(params, "status", status);
		// MapUtils.putNotNull(params, "dataType", dataType);// 查抄送人
		// if(!dataType.equals("MONIT")) {
		// MapUtils.putNotNull(params, "loginUserId",
		// WebUtils.getLoggedUser().getId());// 查抄送人
		// }
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		MapUtils.putNotNull(params, "loginUserId", loggedUser.getId());
		MapUtils.putNotNull(params, "keyword", keyword);// 查抄送人
		MapUtils.putNotNull(params, "status", status);
		if (WfNodeStatus.PENDING.name().equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.PENDING.name());
		} else if (WfNodeStatus.COMPLETE.name().equals(dataType)) {// 已审批
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.COMPLETE.name());
		} else if ("CCPERSON".equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", "CCPERSON");
		} else if ("APPROVED".equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", "APPROVED");
		} else if ("MONIT".equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", "MONIT");
		}
		PageTable<ApplyCommon> searchScrollPage = applyCommonService.searchScrollPage(params,
				new Pagination(offset, limit, sort));
		if (searchScrollPage == null) {
			return StatusBootTableDto.buildDataSuccessStatusDto();
		}
		return StatusDto.buildSuccess(searchScrollPage);
	}
	@RequestMapping(value = "/info")
	public Object info(@RequestParam Long id) {
		ApplyCommon applyCommon = applyCommonService.getInfoById(id);
		return StatusDto.buildSuccess(applyCommon);
	}
	@RequestMapping(value = "/sourcedata")
	public Object sourcedata(@RequestParam Long id) {
		Map<String, Object> sourceData = applyCommonService.sourceData(id);
		return StatusDto.buildSuccess(sourceData);
	}
	@RequestMapping(value = "/info/{id}")
	public Object applyInfo(@PathVariable Long id) {
		ApplyCommon applyCommon = applyCommonService.getInfoById(id);
		return StatusDto.buildSuccess(applyCommon);
	}
	@RequestMapping(value = "/{id}/get", method = RequestMethod.GET)
	public Object getDetails(@PathVariable(value = "id") Long id) {
		ApplyCommon applyCommon = applyCommonService.getInfoById(id);
		if (applyCommon == null) {
			return StatusDto.buildFailure("没有此通用申请单信息！");
		}
		return StatusDto.buildSuccess(applyCommon);
	}
	@RequestMapping(value = "/upload", method = RequestMethod.POST)
	public Object upload(@RequestParam(value = "file") CommonsMultipartFile file,
			@RequestParam UploadCategory category) {
		try {
			String saveTmpPath = uploadService.upload(file, category);
			Map<String, String> data = Maps.newHashMap();
			data.put("fileName", file.getOriginalFilename());
			data.put("path", WebUtils.getUploadFilePath(saveTmpPath));
			data.put("fullPath", WebUtils.getFullUploadFilePath(saveTmpPath));
			return StatusDto.buildSuccess(data);
		} catch (Exception e) {
			e.printStackTrace();
			String msg = "上传文件失败";
			if (e instanceof ServiceException) {
				msg = e.getMessage();
			}
			return StatusDto.buildFailure(msg);
		}
	}
	@RequestMapping(value = "/generSerialNumber", method = RequestMethod.POST)
	public Object generSerialNumber() {
		String sequence = applySequenceService.getSequence(WfApplyTypeEnum.COMMON);
		return StatusDto.buildSuccess(sequence);
	}
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public Object delete(@RequestParam String path) {
		if (StringUtils.isEmpty(path))
			return StatusDto.buildFailure("文件路径不能为空！");
		String relatePath = this.uploadService.getRelatePath(path);
		if (this.uploadService.delete(relatePath))
			return StatusDto.buildSuccess();
		return StatusDto.buildFailure("删除失败");
	}
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public Object save(ApplyCommon applyCommon, HttpServletRequest requst, HttpServletResponse response) {
		ShiroUser user = execParams(applyCommon, requst);
		String applyPerson = applyCommon.getApplyPerson();
		if ("".equals(applyPerson) || applyPerson == null) {
			return StatusDto.buildFailure("审批人不能为空");
		}
		applyCommonService.saveApplyCommon(applyCommon, user);
		return StatusDto.buildSuccess("提交成功");
	}
	@RequestMapping(value = "/del/{id}", method = RequestMethod.GET)
	public Object del(@PathVariable Long id) {
		String msg = "操作成功";
		try {
			applyCommonService.del(id);
		} catch (Exception e) {
			msg = "操作异常请联系管理员";
		}
		return StatusDto.buildSuccess(msg);
	}
	@RequestMapping(value = "/addCCUser", method = RequestMethod.POST)
	public Object addCCUser(String ccUsers, String ccUserIds, String ccUserNames, Long id, HttpServletRequest requst,
			HttpServletResponse response) {
		ApplyCommon applyCommon = new ApplyCommon();
		applyCommon.setId(id);
		applyCommon.setCcPerson(ccUserIds);
		applyCommon.setCcPersonName(ccUserNames);
		applyCommon.setCcPersonInfo(ccUsers);
		this.applyCommonService.updateCCuserAdd(applyCommon);
		return StatusDto.buildSuccess("提交成功");
	}
	private ShiroUser execParams(ApplyCommon applyCommon, HttpServletRequest requst) {
		String photoSrcs = requst.getParameter("photosInfo");
		String accessories = requst.getParameter("accessoriesInfo");
		String applyPersonInfo = requst.getParameter("applyPersonInfo");
		String ccPersonInfo = requst.getParameter("ccPersonInfo");
		String ccPersonIds = requst.getParameter("ccPersonIds");
		String applyPersonIds = requst.getParameter("applyPersonIds");
		String ccPersonName = requst.getParameter("ccPersonName");
		String applyPersonName = requst.getParameter("applyPersonName");
		String applyTime = requst.getParameter("applyTime");
		Date parseToDateTime = DateUtil.parseToDateTime(applyTime);
		applyCommon.setApplyPerson(applyPersonIds);
		applyCommon.setPhotos(photoSrcs);
		applyCommon.setAccessories(accessories);
		applyCommon.setCcPerson(ccPersonIds);
		applyCommon.setApplyPersonInfo(applyPersonInfo);
		applyCommon.setCcPersonInfo(ccPersonInfo);
		applyCommon.setCreateTime(new Date());
		applyCommon.setApplyPersonName(applyPersonName);
		applyCommon.setCcPersonName(ccPersonName);
		applyCommon.setStatus(ApplyStatus.APPROVALING.name());
		applyCommon.setApplyTime(parseToDateTime);
		ShiroUser user = WebUtils.getLoggedUser();
		applyCommon.setSubmitUser(Integer.parseInt(user.getId() + ""));
		return user;
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(ApplyCommon applyCommon, HttpServletRequest requst, HttpServletResponse response) {
		ShiroUser user = execParams(applyCommon, requst);
		String applyPerson = applyCommon.getApplyPerson();
		if ("".equals(applyPerson) || applyPerson == null) {
			return StatusDto.buildFailure("审批人不能为空");
		}
		applyCommonService.updateApplyCommon(applyCommon, user);
		return StatusDto.buildSuccess("提交成功");
	}
	@RequestMapping(value = "/download")
	public ModelAndView download(HttpServletRequest requst, HttpServletResponse response) {
		String filePath = requst.getParameter("file");
		String relatePath = this.uploadService.getRelatePath(filePath);
		ResourceBundle bundle = ResourceBundle.getBundle("application");
		String uploadDir = bundle.getString("upload.dir");
		File file = new File(uploadDir, relatePath);
		// String fileExtension = Files.getFileExtension(file.getAbsolutePath());
		// 获取文件扩展名
		// String fileName = UUID.randomUUID().toString() + "." + fileExtension;
		View viewDownLoad = new ViewDownLoad(new File(file.getAbsolutePath()), "application/force-download");
		return new ModelAndView(viewDownLoad);
	}
	@RequestMapping(value = "/print/{id}")
	public ModelAndView print(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws DocumentException, IOException {
		String fileFullPath = this.applyCommonService.creataPdf(id, request, response, session);
		View viewDownLoad = new ViewDownLoad(new File(fileFullPath), "application/pdf");
		return new ModelAndView(viewDownLoad);
	}
	@RequestMapping(value = "/view/{id}")
	public Object view(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException, DocumentException {
		String fileFullPath = this.applyCommonService.creataPdf(id, request, response, session);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fileFullPath)), httpHeaders,
				HttpStatus.OK);
	}
	@RequestMapping(value = "/reset/{id}")
	public Object reset(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			this.wfProcessService.updateReset(id);
		} catch (Exception e) {
			return StatusDto.buildSuccess("撤回失败！");
		}
		return StatusDto.buildSuccess("已撤回！");
	}
}
