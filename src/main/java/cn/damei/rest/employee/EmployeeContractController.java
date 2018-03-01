package cn.damei.rest.employee;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.employee.EmployeeContract;
import cn.damei.service.employee.EmployeeContractService;
import cn.damei.service.upload.UploadService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/employeeContract")
public class EmployeeContractController extends BaseController {
    @Autowired
    private EmployeeContractService service;
    @Autowired
    private UploadService uploadService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public Object listWithOrder(@RequestParam(required = false) Long empId,
                                @RequestParam(defaultValue = "id") String orderColumn,
                                @RequestParam(defaultValue = "DESC") String orderSort,
                                @RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "10") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "empId", empId);
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
        PageTable pageTable = this.service.searchScrollPage(params, new Pagination(offset, limit, sort));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(EmployeeContract contract) {
        ShiroUser loginUser = WebUtils.getLoggedUser();
        if (contract != null && loginUser != null) {
            contract.setCreateUser(loginUser.getId());
            contract.setCreateTime(new Date());
            if (!StringUtils.isEmpty(contract.getFileUrl())) {
                contract.setFileUrl(uploadService.submitPath(contract.getFileUrl()));
            }
            if (this.service.insert(contract) > 0) {
                return StatusDto.buildSuccess();
            }
        }
        return StatusDto.buildFailure("创建失败");
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(EmployeeContract contract) {
        ShiroUser loginUser = WebUtils.getLoggedUser();
        if (contract != null && contract.getId() != null && loginUser != null) {
            contract.setUpdateUser(loginUser.getId());
            contract.setUpdateTime(new Date());
            if (!StringUtils.isEmpty(contract.getFileUrl())) {
                contract.setFileUrl(uploadService.submitPath(contract.getFileUrl()));
            }
            if (this.service.update(contract) > 0) {
                return StatusDto.buildSuccess();
            }
        }
        return StatusDto.buildFailure("更新失败");
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        return StatusDto.buildSuccess(this.service.getById(id));
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {
        if (this.service.deleteById(id) > 0)
            return StatusDto.buildSuccess();
        return StatusDto.buildFailure();
    }
}
