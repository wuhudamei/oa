package cn.damei.rest.budget;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.budget.Budget;
import cn.damei.entity.budget.YearBudget;
import cn.damei.entity.budget.YearBudgetDetail;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.service.account.UserService;
import cn.damei.service.budget.YearBudgetDetailService;
import cn.damei.service.budget.YearBudgetService;
import cn.damei.service.organization.MdniOrganizationService;
import cn.damei.service.process.ProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/yearBudget")
public class YearBudgetController extends BaseController {
    @Autowired
    YearBudgetService yearBudgetService;
    @Autowired
    YearBudgetDetailService yearBudgetDetailService;
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @Autowired
    UserService userService;
    @Autowired
    private ProcessService processService;
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "status", required = false) ApplyStatus status,
                            @RequestParam(value = "startDate", required = false) String startDate,
                            @RequestParam(value = "endDate", required = false) String endDate,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUser().getId();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "userId", userId);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "startData", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
        PageTable<Budget> pageTable = yearBudgetService.searchScrollPage(params, new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public StatusDto add(@RequestBody YearBudget yearBudget) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        if (yearBudget.getId() == null) {
            int i = yearBudgetService.getByTypeAndYear(yearBudget.getType().name(), yearBudget.getBudgetYear(), loggedUser.getCompanyId());
            if (i > 0) {
                return StatusDto.buildFailure("已存在该类年度预算申请！");
            }
        }
        if (yearBudget.getStatus() == ApplyStatus.DRAFT) {
            yearBudgetService.createOrUpdate(yearBudget, loggedUser, true, false);
        } else {
            int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.YEARBUDGET.name(),WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
            if (rowNum > 0) {
                yearBudgetService.createOrUpdate(yearBudget, loggedUser, false, false);
            } else {
                return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
            }
        }
        return StatusDto.buildSuccess("保存成功");
    }
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public StatusDto submit(@RequestBody YearBudget yearBudget) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        if (yearBudget.getId() == null) {
            int i = yearBudgetService.getByTypeAndYear(yearBudget.getType().name(), yearBudget.getBudgetYear(), loggedUser.getCompanyId());
            if (i > 0) {
                return StatusDto.buildFailure("已存在该类年度预算申请！");
            }
        }
        if (yearBudget.getStatus() == ApplyStatus.DRAFT) {
            yearBudgetService.createOrUpdate(yearBudget, loggedUser, true, true);
        } else {
            int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.YEARBUDGET.name(),WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
            if (rowNum > 0) {
                yearBudgetService.createOrUpdate(yearBudget, loggedUser, false, true);
            } else {
                return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
            }
        }
        return StatusDto.buildSuccess("提交成功");
    }
    @RequestMapping(value = "/commit/{id}")
    public StatusDto commit(@PathVariable Long id) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        YearBudget yearBudget = yearBudgetService.getById(id);
        if(yearBudget == null){
            return StatusDto.buildFailure("没有查询到记录！");
        }
        int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.YEARBUDGET.name(),WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
        if (rowNum > 0) {
            yearBudgetService.commit(yearBudget, loggedUser);
        } else {
            return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
        }
        return StatusDto.buildSuccess("提交成功");
    }
    @RequestMapping(value = "/{id}")
    public StatusDto getById(@PathVariable Long id) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        YearBudget yearBudget = yearBudgetService.getById(id);
        yearBudget.setApplyUser(userService.getById(yearBudget.getApplyUser().getId()));
        if (loggedUser.getCompanyId() != null) {
            yearBudget.setApplyCompany(mdniOrganizationService.getById(loggedUser.getCompanyId()));
        }
        List<YearBudgetDetail> yearBudgetDetailList = yearBudgetDetailService.getByBudgetId(yearBudget.getId());
        yearBudget.setYearBudgetDetailList(yearBudgetDetailList);
        return StatusDto.buildSuccess(yearBudget);
    }
    @RequestMapping(value = "/delete/{id}")
    public StatusDto deleteById(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为null！");
        yearBudgetService.deleteYearAndDetails(id);
        return StatusDto.buildSuccess("删除成功");
    }
    @RequestMapping(value = "/getInfo/{id}")
    public StatusDto getInfo(@PathVariable Long id) {
        return null;
    }
}
