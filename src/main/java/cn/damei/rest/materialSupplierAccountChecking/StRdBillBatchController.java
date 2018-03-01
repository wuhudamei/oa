package cn.damei.rest.materialSupplierAccountChecking;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.service.materialSupplierAccountChecking.StRdBillBatchService;
import javax.servlet.http.HttpServletRequest;
@RestController
@RequestMapping(value ="/api/stRdBillBatch")
public class StRdBillBatchController extends BaseController {
    @Autowired
    private StRdBillBatchService stRdBillBatchService;
    @RequestMapping("/findAll")
    public Object findAll(HttpServletRequest request){
        String supplier = request.getParameter("pdSupplier");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        if(StringUtils.isNotBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNotBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        return StatusDto.buildSuccess(this.stRdBillBatchService.findAll(supplier,startTime,endTime));
    }
}
