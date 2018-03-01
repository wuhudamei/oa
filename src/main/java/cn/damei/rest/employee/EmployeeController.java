package cn.damei.rest.employee;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.employee.EmpOrgDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.employee.Employee;
import cn.damei.entity.organization.ZTreeOrg;
import cn.damei.enumeration.Status;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.service.account.UserService;
import cn.damei.service.employee.EmployeeService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/employees")
public class EmployeeController extends BaseController {
    @Autowired
    private EmployeeService employeeService;
    @Autowired
    private UserService userService;
    @Autowired
    private UploadService uploadService;
    @RequestMapping(method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String orgCode,
                       @RequestParam(required = false) EmployeeStatus employeeStatus,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "orgCode", orgCode);
        MapUtils.putNotNull(params, "employeeStatus", employeeStatus);
        PageTable page = this.employeeService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value="/applylist",method = RequestMethod.GET)
    public Object applylist(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String orgCode,
                       @RequestParam(required = false) EmployeeStatus employeeStatus,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "orgCode", orgCode);
        MapUtils.putNotNull(params, "employeeStatus", employeeStatus);
        if(!params.keySet().contains("keyword")) {
        	ShiroUser user = WebUtils.getLoggedUser();
        	Long departmentId = user.getDepartmentId();
        	if(departmentId == null) {
        		Long companyId = user.getCompanyId();
        		MapUtils.putNotNull(params, "companyId", companyId);
        	}else {
        		MapUtils.putNotNull(params, "department", departmentId);
        	}
        }
        PageTable page = this.employeeService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value = "/openid", method = RequestMethod.GET)
    public Object listWithOpenId(@RequestParam(required = false) String keyword,
                                 @RequestParam(required = false) String orgCode,
                                 @RequestParam(required = false) EmployeeStatus employeeStatus,
                                 @RequestParam(defaultValue = "0") int offset,
                                 @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "orgCode", orgCode);
        MapUtils.putNotNull(params, "employeeStatus", employeeStatus);
        PageTable page = this.employeeService.searchScrollPageWithOpenid(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Employee employee) {
        if (employee == null) {
            return StatusDto.buildFailure("员工为null");
        }
        if (!this.employeeService.validateOrgCode(employee.getOrgCode(), null)) {
            return StatusDto.buildFailure("员工编号已存在");
        }
        if (!this.employeeService.validateUsername(employee.getUsername(), null)) {
            return StatusDto.buildFailure("用户名已存在");
        }
        if (!StringUtils.isEmpty(employee.getPhoto())) {
            employee.setPhoto(this.uploadService.submitPath(employee.getPhoto()));
        }
        employee.setCreateUser(WebUtils.getLoggedUser().getId());
        employee.setCreateTime(new Date());
        employee.setAccountStatus(Status.OPEN);
        employee.setEmployeeStatus(EmployeeStatus.ON_JOB);
        employee.setAccountSource("OA");
        int i = this.employeeService.insert(employee);
        if (i < 1)
            return StatusDto.buildFailure("保存失败");
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("员工id不能为null");
        Employee employee = this.employeeService.getFullInfoById(id);
        if("DIMISSION".equals(employee.getEmployeeStatus().getLabel())){
            employee.setEntryDate(new Date());
            employee.getOrg().getOrg().setOrgName(null);
        }
        if (employee == null)
            return StatusDto.buildFailure("未查询到此用户");
        return StatusDto.buildSuccess(employee);
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody Employee employee) {
        if (employee == null || employee.getId() == null) {
            return StatusDto.buildFailure("员工为null");
        }
        if (employee.getOrgId() == 1) {
            return StatusDto.buildFailure("员工不允许直接设置在集团下");
        }
        if (!this.employeeService.validateOrgCode(employee.getOrgCode(), employee.getId())) {
            return StatusDto.buildFailure("员工编号已存在");
        }
        if (!this.employeeService.validateUsername(employee.getUsername(), employee.getId())) {
            return StatusDto.buildFailure("用户名已存在");
        }
        if (!StringUtils.isEmpty(employee.getPhoto())) {
            employee.setPhoto(this.uploadService.submitPath(employee.getPhoto()));
        }
        employee.setUpdateUser(WebUtils.getLoggedUser().getId());
        employee.setUpdateTime(new Date());
        int i = this.employeeService.updateFullInfo(employee);
        if (i < 1)
            return StatusDto.buildFailure("更新失败");
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}/password", method = RequestMethod.GET)
    public Object resetPwd(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为空！");
        return this.userService.resetPwd(id);
    }
    @RequestMapping(value = "/{id}/dimission", method = RequestMethod.GET)
    public Object dimission(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为空！");
        this.employeeService.dimission(id);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}/org/tree", method = RequestMethod.GET)
    public Object getOrgsTree(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("员工id不能为null");
        }
        List<ZTreeOrg> tree = this.employeeService.buildOrgsTree(id);
        return StatusDto.buildSuccess(tree);
    }
    @RequestMapping(value = "/{id}/org", method = RequestMethod.POST)
    public Object saveOrgs(@PathVariable Long id, @RequestBody List<EmpOrgDto> orgs) {
        if (id == null) {
            return StatusDto.buildFailure("员工id不能为null");
        }
        this.employeeService.savePartTimeOrgs(id, orgs);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/principal/department", method = RequestMethod.POST)
    public Object departmentPrincipal(Long empId, Long departmentId) {
        if (empId == null || departmentId == null)
            return StatusDto.buildFailure("员工id或部门id为null");
        this.employeeService.setDepartmentPrincipal(empId, departmentId);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/principal/company", method = RequestMethod.POST)
    public Object companyPrincipal(Long empId, Long companyId) {
        if (empId == null || companyId == null)
            return StatusDto.buildFailure("员工id或公司id为null");
        this.employeeService.setCompanyPrincipal(empId, companyId);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为空！");
        }
        int i = this.employeeService.deleteById(id);
        if (i < 1)
            return StatusDto.buildFailure("删除失败！");
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/getEmployeeOrgCode", method = RequestMethod.GET)
    public Object getEmployeeOrgCode() {
    	Map<String,String> orgCodeMap = new HashMap<String,String>();
    	orgCodeMap.put("orgCode", this.employeeService.getMaxNextOrgCode());
        return StatusDto.buildSuccess(orgCodeMap);
    }
}
