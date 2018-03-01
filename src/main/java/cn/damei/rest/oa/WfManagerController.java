package cn.damei.rest.oa;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.oa.WfProcess;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.service.oa.WfProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/wfmanager")
public class WfManagerController extends BaseController {
	@Autowired
	private WfProcessService wfProcessService;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object listWithOrder(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String type, @RequestParam(required = false) String wfType,
			@RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = "10") int limit, HttpServletRequest request) {
		ShiroUser user = WebUtils.getLoggedUser();
		Map<String, Object> params = Maps.newHashMap();
		// if((WfApplyTypeEnum.LEAVE.name() + "-" +
		// WfApplyTypeEnum.BUSINESS.name()).equals(type)){
		// MapUtils.putNotNull(params, "type", " in ('"+ WfApplyTypeEnum.BUSINESS +
		// "','" + WfApplyTypeEnum.LEAVE + "')");
		// }else{
		// if(StringUtils.isNotBlank(type)){
		// MapUtils.putNotNull(params, "type", "='" + type + "'");
		// }
		// }
		// if("HIS".equals(wfType)){
		// MapUtils.putNotNull(params, "status", "COMPLETE");
		// }else{
		// MapUtils.putNotNull(params, "status", "PENDING");
		// }
		// MapUtils.putNotNull(params, "keyword", keyword);
		// MapUtils.putNotNull(params, "userId", user.getId());
		MapUtils.putNotNull(params, "userId", user.getId());
		MapUtils.putNotNull(params, "status", "PENDING");
		Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
		PageTable<WfProcess> pageTable = this.wfProcessService.searchScrollPage(params,
				new Pagination(offset, limit, sort));
		return StatusDto.buildSuccess(pageTable);
	}
	@RequestMapping(value = "/getWfApprove/{id}", method = RequestMethod.GET)
	public Object getWfApprove(@PathVariable Long id, HttpServletRequest request) {
		WfProcess wfProcess = wfProcessService.getById(id);
		return StatusDto.buildSuccess(wfProcess);
	}
	@RequestMapping(value = "/wfApproveDetail", method = RequestMethod.GET)
	public Object wfApproveDetail(@RequestParam(value = "formId") Long formId,
			@RequestParam(value = "type") String type, @RequestParam(value = "showType") String showType,
			HttpServletRequest request) {
		List<WfProcess> processList = wfProcessService.findApproveDetailByFormId(formId, type, showType);
		return StatusDto.buildSuccess(processList);
	}
	@RequestMapping(value = "/wfApproveDetailAll", method = RequestMethod.GET)
	public Object wfApproveDetailAll(@RequestParam(value = "formId") Long formId,
			@RequestParam(value = "type") String type, @RequestParam(value = "showType") String showType,
			HttpServletRequest request) {
		List<WfProcess> processList = wfProcessService.findApproveDetailByFormId(formId, type, showType);
		return StatusDto.buildSuccess(processList);
	}
	@RequestMapping(value = "/wfApprove", method = RequestMethod.POST)
	public Object wfApprove(@RequestBody WfProcess wfProcess, HttpServletRequest request) {
		try {
			Long id = wfProcess.getId();
			WfProcess dbWf = wfProcessService.getById(id);
			Employee curreentApproverEmployee = wfProcess.getApproverEmployee();
			// 正常的审批是当前登陆用户,转派和添加则是添加的目标
			if (null == curreentApproverEmployee) {
				ShiroUser user = WebUtils.getLoggedUser();
				Employee approverEmployee = new Employee();
				approverEmployee.setId(user.getId());
				dbWf.setApproverEmployee(approverEmployee);
			} else {
				dbWf.setApproverEmployee(curreentApproverEmployee);
			}
			dbWf.setRemark(wfProcess.getRemark());
			dbWf.setApproveResult(wfProcess.getApproveResult());
			dbWf.setApproveTime(new Date());
			dbWf.setTargetUserId(wfProcess.getTargetUserId());
			dbWf.setCurrentApprove(wfProcess.getCurrentApprove());
			boolean retResult = wfProcessService.wfOperation(dbWf);
			if (retResult) {
				return StatusDto.buildSuccess();
			} else {
				return StatusDto.buildFailure();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("出现异常，请管理管理员");
		}
	}
	@RequestMapping(value = "/sourcedata")
	public Object sourcedata(@RequestParam Long id) {
		List<Map<String, Object>> sourceData = wfProcessService.sourceData(id);
		PageTable<Map<String, Object>> page = new PageTable<Map<String, Object>>(sourceData, sourceData.size());
		return StatusDto.buildSuccess(page);
	}
	@RequestMapping(value = "/execWfApprove", method = RequestMethod.POST)
	public Object execWfApprove(Long id, String remark, String approveResult, HttpServletRequest request) {
		try {
			WfProcess wfProcess = wfProcessService.getById(id);
			wfProcess.setRemark(remark);
			wfProcess.setApproveResult(approveResult);
			boolean retResult = wfProcessService.wfOperation(wfProcess);
			if (retResult) {
				return StatusDto.buildSuccess();
			} else {
				return StatusDto.buildFailure();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return StatusDto.buildFailure("出现异常，请管理管理员");
		}
	}
}
