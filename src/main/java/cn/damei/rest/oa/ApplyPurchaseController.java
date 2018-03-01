package cn.damei.rest.oa;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.github.pagehelper.util.StringUtil;
import com.google.common.collect.Maps;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.oa.ApplyPurchase;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.service.employee.EmployeeOrgService;
import cn.damei.service.oa.ApplyPurchaseService;
import cn.damei.service.oa.ApplySequenceService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.service.process.ProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/apply/applyPurchase")
public class ApplyPurchaseController extends BaseController {
	@Autowired
	ApplyPurchaseService applyPurchaseService;
	@Autowired
	MdniDictionaryService mdniDictionaryService;
	@Autowired
	EmployeeOrgService employeeOrgService;
	@Autowired
	ApplySequenceService applySequenceService;
	@Autowired
	WfProcessService wfProcessService;
	@Autowired
	private ProcessService processService;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public Object listWithOrder(@RequestParam(required = false) String keyword,
			@RequestParam(required = false) String status, @RequestParam(defaultValue = "id") String orderColumn,
			@RequestParam(defaultValue = "DESC") String orderSort,
			@RequestParam(required = false, defaultValue = "0") int offset,
			@RequestParam(required = false, defaultValue = "10") int limit) {
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "status", status);
		ShiroUser loginUser = WebUtils.getLoggedUser();
		MapUtils.putNotNull(params, "userId", loginUser.getId());
		Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
		PageTable<ApplyPurchase> pageTable = applyPurchaseService.searchScrollPage(params,
				new Pagination(offset, limit, sort));
		return StatusDto.buildSuccess(pageTable);
	}
	@RequestMapping(method = RequestMethod.POST)
	public Object add(ApplyPurchase applyPurchase) {
		ShiroUser shiroUser = WebUtils.getLoggedUser();
		int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.PURCHASE.name(), WfNatureEnum.APPROVAL.name(),
				shiroUser.getOrgId());
		if (rowNum > 0) {
			if (applyPurchaseService.add(applyPurchase, true) > 0) {
				return StatusDto.buildSuccess("保存成功");
			}
			return StatusDto.buildFailure("保存失败");
		} else {
			return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
		}
	}
	@RequestMapping(value = "submit", method = RequestMethod.POST)
	public Object submit(@RequestParam Long id) {
		ShiroUser shiroUser = WebUtils.getLoggedUser();
		ApplyPurchase applyPurchase = applyPurchaseService.getById(id);
		int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.PURCHASE.name(), WfNatureEnum.APPROVAL.name(),
				shiroUser.getOrgId());
		if (rowNum > 0) {
			if (applyPurchaseService.submit(applyPurchase) > 0) {
				return StatusDto.buildSuccess();
			} else {
				return StatusDto.buildFailure("提交失败");
			}
		} else {
			return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
		}
	}
	@RequestMapping(value = "/delete/{id}")
	public Object delete(@PathVariable Long id) {
		if (applyPurchaseService.deleteById(id) > 0) {
			return StatusDto.buildSuccess("删除成功");
		} else {
			return StatusDto.buildFailure("删除失败");
		}
	}
	@RequestMapping(value = "/saveDraft", method = RequestMethod.POST)
	public Object saveDraft(ApplyPurchase applyPurchase) {
		if (applyPurchase.getId() == null) {
			if (applyPurchaseService.add(applyPurchase, false) > 0) {
				return StatusDto.buildSuccess("创建成功");
			}
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
			String applyTitle = "";
			try {
				Date date = sdf.parse(applyPurchase.getPurchaseMonth());
				applyTitle = DateUtils.format(date, "yyyy年MM月") + " 采购申请";
			} catch (ParseException e) {
				e.printStackTrace();
			}
			applyPurchase.setApplyTitle(applyTitle);
			if (applyPurchaseService.update(applyPurchase) > 0) {
				return StatusDto.buildSuccess("修改成功");
			}
		}
		return StatusDto.buildFailure("创建失败");
	}
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public Object update(ApplyPurchase applyPurchase) {
		ShiroUser shiroUser = WebUtils.getLoggedUser();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String applyTitle = "";
		String applyCode = "";
		// applyTitle = DateUtils.format(date, "yyyy年MM月") +" 采购申请";
		if (applyPurchase != null && shiroUser != null) {
			String purchaseMonth = applyPurchase.getPurchaseMonth();
			if (StringUtil.isNotEmpty(purchaseMonth)) {
				try {
					Date date = sdf.parse(purchaseMonth);
					applyTitle = DateUtils.format(date, "yyyy年MM月") + " 采购申请";
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
			applyPurchase.setCreateTime(new Date());
			applyPurchase.setApplyUser(shiroUser.getId());
			applyPurchase.setStatus(ApplyStatus.APPROVALING);
			applyPurchase.setApplyTitle(applyTitle);
			if (applyPurchaseService.update(applyPurchase) > 0) {
				return StatusDto.buildSuccess();
			}
		}
		return StatusDto.buildFailure("更新失败");
	}
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Object get(@PathVariable Long id) {
		return StatusDto.buildSuccess(applyPurchaseService.getByIdWithDic(id));
	}
}