package cn.damei.rest.api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.organization.MdniOrganization;
import cn.damei.service.organization.MdniOrganizationService;
import java.util.List;
@RestController
@RequestMapping(value = "/api/interface/store")
public class SroreDataSupportController extends BaseController {
    @Autowired
    MdniOrganizationService mdniOrganizationService;
    @RequestMapping(value = "/cogradient", method = RequestMethod.GET)
    public Object cogradient() {
        try{
            List<MdniOrganization> mdniOrganizationList = mdniOrganizationService.findStores();
            return StatusDto.buildSuccess(mdniOrganizationList);
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败");
        }
    }
}
