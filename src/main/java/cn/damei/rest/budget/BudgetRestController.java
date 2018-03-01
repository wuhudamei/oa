package cn.damei.rest.budget;
import java.sql.SQLException;
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
import cn.damei.dto.budget.BudgetDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.budget.Budget;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.service.budget.BudgetService;
import cn.damei.service.process.ProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/budgets")
public class BudgetRestController extends BaseController {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private ProcessService processService;
    @RequestMapping(method = RequestMethod.GET)
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
        PageTable<Budget> pageTable = budgetService.searchScrollPage(params, new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(method = RequestMethod.POST)
    public StatusDto createOrUpdate(@RequestBody Budget budget) throws SQLException {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        String result = checkBudget(budget);
        if (result != null) {
            return StatusDto.buildFailure(result);
        }
        if (budget.getStatus() == ApplyStatus.DRAFT) {
            budgetService.createOrUpdate(budget, loggedUser, true);
        } else {
            int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.BUDGET.name(), WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
            if (rowNum > 0) {
                budgetService.createOrUpdate(budget, loggedUser, false);
            } else {
                return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
            }
        }
        return StatusDto.buildSuccess("操作成功");
    }
    @RequestMapping("/{budgetId}/commit")
    public StatusDto saveDraft(@PathVariable(value = "budgetId") Long budgetId) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        Budget budget = budgetService.getById(budgetId);
        if (budget == null) {
            return StatusDto.buildFailure("没有查询到您要提交的记录！");
        }
        int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.BUDGET.name(), WfNatureEnum.APPROVAL.name(), loggedUser.getOrgId());
        if (rowNum > 0) {
            if (budgetService.commit(budget, loggedUser)) {
                return StatusDto.buildSuccess();
            }
        } else {
            return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
        }
        return StatusDto.buildFailure("提交草稿失败！");
    }
    private String checkBudget(Budget budget) {
        if (1 != 1)
            return "";
        return null;
    }
    @RequestMapping(value = "/{budgetId}/get", method = RequestMethod.GET)
    public StatusDto getDetails(@PathVariable(value = "budgetId") Long budgetId) {
        Budget budget = budgetService.getDetails(budgetId);
        if (budget == null) {
            return StatusDto.buildFailure("没有此预算信息！");
        }
        return StatusDto.buildSuccess(budget);
    }
    @RequestMapping(value = "/{id}/del", method = RequestMethod.DELETE)
    public StatusDto delete(@PathVariable(value = "id") Long id) {
        budgetService.deleteById(id);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/multiple/{budgetId}", method = RequestMethod.GET)
    public StatusDto getBudgetMulti(@PathVariable(value = "budgetId") Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为null！");
        BudgetDto budget = this.budgetService.buildBudgetDetail(id);
        return StatusDto.buildSuccess(budget);
    }
}