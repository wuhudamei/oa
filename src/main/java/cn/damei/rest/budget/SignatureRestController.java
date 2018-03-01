package cn.damei.rest.budget;
import com.google.common.collect.Maps;
import com.lowagie.text.DocumentException;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.view.ViewDownLoad;
import cn.damei.dto.StatusDto;
import cn.damei.dto.budget.SignatureDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.budget.Signature;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.enumeration.WfNodeStatus;
import cn.damei.service.budget.SignatureService;
import cn.damei.service.process.ProcessService;
import cn.damei.service.subjectprocess.SubjectProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/signatures")
public class SignatureRestController extends BaseController {
	@Autowired
	private SignatureService signatureService;
	@Autowired
	private ProcessService processService;
	@Autowired
	private SubjectProcessService subjectProcessService;
	@RequestMapping(method = RequestMethod.GET)
	public Object search(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "ccFlag", required = false, defaultValue = "0") String ccFlag,
			@RequestParam(value = "status", required = false) ApplyStatus status,
			@RequestParam(value = "startDate", required = false) String startDate,
			@RequestParam(value = "endDate", required = false) String endDate,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit) {
		Long userId = WebUtils.getLoggedUser().getId();
		Map<String, Object> params = Maps.newHashMap();
		if ("0".equals(ccFlag)) {
			MapUtils.putNotNull(params, "userId", userId);
		} else if ("1".equals(ccFlag)) {   
			MapUtils.putNotNull(params, "ccUserId", userId);
		}
		if (WfNodeStatus.PENDING.name().equals(type)) {
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.PENDING.name());
			MapUtils.putNotNull(params, "userId", userId);
		} else if (WfNodeStatus.COMPLETE.name().equals(type)) {// 已审批
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.COMPLETE.name());
		} else if ("CCUSER".equals(type)) {
			MapUtils.putNotNull(params, "dataType", "CCUSER");
		}else if("APPROVED".equals(type)) {
			MapUtils.putNotNull(params, "dataType", "APPROVED");
		}
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "status", status);
		MapUtils.putNotNull(params, "startData", startDate);
		MapUtils.putNotNull(params, "endDate", endDate);
		PageTable<Signature> pageTable = signatureService.searchScrollPage(params,
				new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
		return StatusDto.buildSuccess(pageTable);
	}
	@RequestMapping(value = "/getApplyCode", method = RequestMethod.GET)
	public StatusDto getApplyCode() {
		String applyCode = signatureService.getApplyCode();
		StatusDto statusDto = StatusDto.buildSuccess();
		statusDto.setData(applyCode);
		return statusDto;
	}
	@RequestMapping(method = RequestMethod.POST)
	public StatusDto createOrUpdate(@RequestBody Signature signature) {
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		if (loggedUser == null) {
			return StatusDto.buildFailure("请刷新页面，重新登录！");
		}
		if (signature.getStatus() == ApplyStatus.DRAFT) {
			signatureService.createOrUpdate(signature, loggedUser, true);
		} else {
			// 判断该流程和科目是否存在
			if (subjectProcessService.findWfByCondition(WfApplyTypeEnum.SIGNATURE.name(), signature.getType(),
					signature.getCostItem()) == null) {
				return StatusDto.buildFailure("该流程类型和费用科目的审批流程不存在！");
			}
			int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.SIGNATURE.name(),
					WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
			if (rowNum > 0) {
				String str = signatureService.createOrUpdate(signature, loggedUser, false);
				if (StringUtils.isNotBlank(str)) {
					return StatusDto.buildFailure(str);
				} else {
					return StatusDto.buildSuccess("操作成功！");
				}
			} else {
				return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
			}
		}
		return StatusDto.buildSuccess("操作成功！");
	}
	@RequestMapping("/{signatureId}/commit")
	public StatusDto save2Draft(@PathVariable(value = "signatureId") Long signatureId) {
		ShiroUser loggedUser = WebUtils.getLoggedUser();
		Signature signature = signatureService.getById(signatureId);
		if (signature == null) {
			return StatusDto.buildFailure("没有查询到您要提交的签报申请！");
		}
		// 判断该流程和科目是否存在
		if (subjectProcessService.findWfByCondition(WfApplyTypeEnum.SIGNATURE.name(), signature.getType(),
				signature.getCostItem()) == null) {
			return StatusDto.buildFailure("该流程类型和费用科目的审批流程不存在！");
		}
		int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.SIGNATURE.name(), WfNatureEnum.APPROVAL.name(),
				loggedUser.getOrgId());
		if (rowNum > 0) {
			if (signatureService.commit(signature, loggedUser)) {
				return StatusDto.buildSuccess();
			}
		} else {
			return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
		}
		return StatusDto.buildFailure("提交签报单失败！");
	}
	private String checkBudget(Signature signature) {
		return null;
	}
	@RequestMapping(value = "/{signatureId}/get", method = RequestMethod.GET)
	public StatusDto getDetails(@PathVariable(value = "signatureId") Long signatureId) {
		Signature signature = signatureService.getDetails(signatureId);
		if (signature == null) {
			return StatusDto.buildFailure("没有此签报单信息！");
		}
		return StatusDto.buildSuccess(signature);
	}
	@RequestMapping(value = "/{id}/del", method = RequestMethod.DELETE)
	public StatusDto delete(@PathVariable(value = "id") Long id) {
		signatureService.deleteById(id);
		return StatusDto.buildSuccess();
	}
	@RequestMapping(value = "/multiple/{signatureId}", method = RequestMethod.GET)
	public StatusDto getBudgetMulti(@PathVariable(value = "signatureId") Long signatureId) {
		SignatureDto signatureDto = signatureService.getMultiDetails(signatureId);
		if (signatureDto == null) {
			return StatusDto.buildFailure("没有此签报单信息！");
		}
		return StatusDto.buildSuccess(signatureDto);
	}
	@RequestMapping(value = "/getBudgetRemain", method = RequestMethod.GET)
	public StatusDto getBudgetRemain(@RequestParam("type") Long type, @RequestParam("month") String month) {
		ShiroUser logged = WebUtils.getLoggedUser();
		return StatusDto.buildSuccess(signatureService.calculateBudgetRemain(logged.getCompanyId(), type, month));
	}
	@RequestMapping(value = "/getSignaturesByMonth", method = RequestMethod.GET)
	public StatusDto getSignaturesByMonth(String month, Long paymentId) {
		if (StringUtils.isEmpty(month)) {
			return StatusDto.buildFailure("请指定查询月份！");
		}
		Long userId = WebUtils.getLoggedUserId();
		List<Signature> signatures = signatureService.getSignaturesByMonthAndUser(userId, month, paymentId);
		return StatusDto.buildSuccess(signatures);
	}
	@RequestMapping(value = "/viewSignature/{id}")
	public Object viewSignature(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException, DocumentException {
		String fileFullPath = this.signatureService.creataPdf(id, request, response, session);
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fileFullPath)), httpHeaders,
				HttpStatus.OK);
	}
	@RequestMapping(value = "/print/{id}")
	public ModelAndView print(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws DocumentException, IOException {
		String fileFullPath = this.signatureService.creataPdf(id, request, response, session);
		View viewDownLoad = new ViewDownLoad(new File(fileFullPath), "application/pdf");
		return new ModelAndView(viewDownLoad);
	}
}