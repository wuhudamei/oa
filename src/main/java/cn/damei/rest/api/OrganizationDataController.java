package cn.damei.rest.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.service.organization.MdniOrganizationService;
@RestController
@RequestMapping(value = "/api/interface/organization")
public class OrganizationDataController extends BaseController {
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @RequestMapping(value = "/getDesignGroup", method = RequestMethod.POST)
    public Object getDesignGroup(@RequestParam(value = "storeCode", required = true) String storeCode) {
        try{
            return StatusDto.buildSuccess(mdniOrganizationService.getDesignGroup(storeCode));
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败");
        }
    }
    @RequestMapping(value = "/getEmployeeByOrgCode", method = RequestMethod.POST)
    public Object getEmployeeByOrgCode(@RequestParam(required = false) String orgCode) {
        try{
            return StatusDto.buildSuccess(mdniOrganizationService.getEmployeeByOrgCode(orgCode));
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败");
        }
    }
    @RequestMapping(value = "/getAuditByOrgCode", method = RequestMethod.POST)
    public Object getAuditByOrgCode(@RequestParam(required = true) String storeCode) {
        try{
            return StatusDto.buildSuccess(mdniOrganizationService.getAuditByOrgCode(storeCode));
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败");
        }
    }
}
