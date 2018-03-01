package cn.damei.rest.salarydetail;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.salarydetail.SalaryBasicData;
import cn.damei.entity.salarydetail.SalaryDetail;
import cn.damei.service.salarydetail.SalaryBasicDataService;
import cn.damei.service.salarydetail.SalaryDetailService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/rest/salarybasicdata")
public class SalaryBasicDataController extends BaseController{
    @Autowired
    private SalaryDetailService salaryDetailService;
    @Autowired
    private SalaryBasicDataService salaryBasicDataService;
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String orgCode,
                       @RequestParam(required = false) String salaMonth,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", null);
        MapUtils.putNotNull(params, "orgCode", null);
        MapUtils.putNotNull(params, "salaMonth", null);
        PageTable page = this.salaryDetailService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping("/insertOrUpdate")
    public Object insertOrUpdate(@RequestBody SalaryBasicData salaryBasicData){
        if(salaryBasicData == null){
            return StatusDto.buildFailure("操作失败");
        }else {
            Object obj = this.salaryBasicDataService.insertOrUpdate(salaryBasicData);
            return obj;
        }
    }
    @RequestMapping(value = "/export")
    public void export(@RequestParam(required = false) String salaMonth) {
        salaryDetailService.export(salaMonth);
    }
    @RequestMapping(value = "/getSalaryDetailByEmpId")
    public Object getSalaryDetailByEmpId(@RequestParam String empId){
        Map<String, Object> map = Maps.newHashMap();
        MapUtils.putNotNull(map, "empId", empId);
        MapUtils.putNotNull(map, "id", null);
        List<SalaryDetail> salaryDetailList = this.salaryDetailService.getSalaryDetail(map);
        return StatusDto.buildSuccess(salaryDetailList);
    }
    @RequestMapping(value = "/getSalaryDetailById")
    public Object getSalaryDetailById(@RequestParam(required = false) String id){
        Map<String, Object> map = Maps.newHashMap();
        MapUtils.putNotNull(map, "empId", null);
        MapUtils.putNotNull(map, "id", id);
        List<SalaryDetail> salaryDetailList = this.salaryDetailService.getSalaryDetail(map);
        return StatusDto.buildSuccess(salaryDetailList);
    }
    @RequestMapping("/getById")
    public Object getById(@RequestParam Long id){
        if(id == 0){
            return StatusDto.buildFailure("操作失败");
        }
        return StatusDto.buildSuccess(salaryBasicDataService.getById(id));
    }
    @RequestMapping("/findAllSalaryBasicData")
    public Object findAllSalaryBasicData(@RequestParam Long empId){
        List<SalaryBasicData>  salaryBasicDataList = this.salaryBasicDataService.findAllSalaryBasicData(empId);
        Map<String,Object> map = Maps.newHashMap();
        map.put("data",salaryBasicDataList);
        if(salaryBasicDataList == null){
            return StatusDto.buildFailure(map);
        }
        return StatusDto.buildSuccess(map);
    }
    @RequestMapping("/deleteById")
    public Object deleteById(@RequestParam Long id){
        if(id == 0){
            return StatusDto.buildFailure("操作失败");
        }
        return StatusDto.buildSuccess(this.salaryBasicDataService.deleteById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody SalaryDetail salaryDetail) {
        return StatusDto.buildSuccess();
    }
}
