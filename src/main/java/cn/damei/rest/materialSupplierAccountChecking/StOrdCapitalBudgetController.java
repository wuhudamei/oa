package cn.damei.rest.materialSupplierAccountChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.service.materialSupplierAccountChecking.StOrdCapitalBudgetService;
@RestController
@RequestMapping(value = "/api/stOrdCapitalBudget")
public class StOrdCapitalBudgetController extends BaseController {
    @Autowired
    private StOrdCapitalBudgetService stOrdCapitalBudgetService;
    @RequestMapping("/findStOrdCapitalBudgetByNoAndMobileAndName")
    public Object findStOrdCapitalBudgetByNoAndMobileAndName(@RequestParam(required = false) String keyword,
                                                             @RequestParam(required = false) String startDate,
                                                             @RequestParam(required = false) String endDate,
                                                             @RequestParam(required = false) String startTime,
                                                             @RequestParam(required = false) String endTime){
        return StatusDto.buildSuccess(this.stOrdCapitalBudgetService.findStOrdCapitalBudgetByNoAndMobileAndName(keyword,startDate,endDate,startTime,endTime));
    }
}