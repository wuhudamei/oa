package cn.damei.rest.salarydetail;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.common.view.ViewExcel;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.salarydetail.SalaryDetail;
import cn.damei.service.salarydetail.SalaryDetailService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/rest/salarydetail")
public class SalaryDetailController extends BaseController{
    @Autowired
    private SalaryDetailService salaryDetailService;
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String orgCode,
                       @RequestParam(required = false) String salaMonth,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "orgCode", orgCode);
        MapUtils.putNotNull(params, "salaMonth", salaMonth);
        PageTable page = this.salaryDetailService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping("/insertOrUpdate")
    public Object insertOrUpdate(@RequestBody SalaryDetail salaryDetail){
        if(salaryDetail == null){
            return StatusDto.buildFailure("操作失败");
        }else {
            this.salaryDetailService.insertOrUpdate(salaryDetail);
            return StatusDto.buildSuccess("操作成功");
        }
    }
    @RequestMapping(value = "/export")
    public ModelAndView export(@RequestParam(required = false) String salaMonth) {
        ModelAndView export = salaryDetailService.export(salaMonth);
        return export;
    }
    @RequestMapping(value = "/getSalaryDetailByEmpId")
    public Object getSalaryDetailByEmpId(@RequestParam String empId,@RequestParam(required = false) String salaMonth){
        Map<String, Object> map = Maps.newHashMap();
        MapUtils.putNotNull(map, "empId", empId);
        MapUtils.putNotNull(map, "id", null);
        MapUtils.putNotNull(map, "salaMonth", salaMonth);
        List<SalaryDetail> salaryDetailList = this.salaryDetailService.getSalaryDetail(map);
        return StatusDto.buildSuccess(salaryDetailList);
    }
    @RequestMapping(value = "/getSalaryDetailById")
    public Object getSalaryDetailById(@RequestParam(required = false) String id,@RequestParam(required = false) String empId){
        Map<String, Object> map = Maps.newHashMap();
        MapUtils.putNotNull(map, "empId", empId);
        MapUtils.putNotNull(map, "id", id);
        List<SalaryDetail> salaryDetailList = this.salaryDetailService.getSalaryDetail(map);
        return StatusDto.buildSuccess(salaryDetailList);
    }
    @RequestMapping("/getById")
    public Object getById(@RequestParam Long id){
        if(id == 0){
            return StatusDto.buildFailure("操作失败");
        }
        return StatusDto.buildSuccess(salaryDetailService.getById(id));
    }
    @RequestMapping("/findAllSalaryDetail")
    public Object findAllSalaryDetail(){
        List<SalaryDetail>  salaryDetailList = this.salaryDetailService.findAllSalaryDetail();
        Map<String,Object> map = Maps.newHashMap();
        map.put("data",salaryDetailList);
        if(salaryDetailList == null){
            return StatusDto.buildFailure(map);
        }
        return StatusDto.buildSuccess(map);
    }
    @RequestMapping("/deleteById")
    public Object deleteById(@RequestParam Long id){
        if(id == 0){
            return StatusDto.buildFailure("操作失败");
        }
        return StatusDto.buildSuccess(this.salaryDetailService.deleteById(id));
    }
    @RequestMapping(value = "update",method = RequestMethod.PUT)
    public Object update(@RequestBody SalaryDetail salaryDetail) {
        salaryDetail.setUpdateUser(WebUtils.getLoggedUser().getId());
        salaryDetail.setUpdateDate(new Date());
        int i = this.salaryDetailService.updateSalary(salaryDetail);
        if (i < 1)
            return StatusDto.buildFailure("更新失败");
        return StatusDto.buildSuccess();
    }
    @RequestMapping("/salary")
    public Object salary(){
      return StatusDto.buildSuccess(salaryDetailService.salaryTax());
    }
    @RequestMapping("/findAllByUpMonth")
    public Object findAllByUpMonth(@RequestParam Long empId){
        return StatusDto.buildSuccess(this.salaryDetailService.findAllByUpMonth(empId));
    }
    @RequestMapping("/insertSign")
    public Object insertSign(){
        this.salaryDetailService.insertss();
        return StatusDto.buildSuccess();
    }
}
