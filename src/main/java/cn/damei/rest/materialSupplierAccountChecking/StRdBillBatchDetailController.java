package cn.damei.rest.materialSupplierAccountChecking;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillItem;
import cn.damei.service.materialSupplierAccountChecking.StRdBillBatchDetailService;
import cn.damei.service.materialSupplierAccountChecking.StRdBillItemService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value ="/api/stRdBillBatchDetail")
public class StRdBillBatchDetailController extends BaseController {
    @Autowired
    private StRdBillBatchDetailService stRdBillBatchDetailService;
    @Autowired
    private StRdBillItemService stRdBillItemService;
    @RequestMapping("/detail")
    public Object findBillBatchDetail(@RequestParam String orderNo,
                                      @RequestParam String pdSupplier,
                                      @RequestParam String brandname,
                                      @RequestParam String startTime,
                                      @RequestParam String endTime){
        if(StringUtils.isNotBlank(startTime)){
            startTime = startTime + " 00:00:00";
        }
        if(StringUtils.isNotBlank(endTime)){
            endTime = endTime + " 23:59:59";
        }
        List<StRdBillItem> itemDetail = this.stRdBillItemService.findBillBatchDetail(orderNo,pdSupplier,brandname,startTime,endTime);
        Map<String, List<StRdBillItem>> map = new HashMap<>();
        map.put("data",itemDetail);
        return StatusDto.buildSuccess(map);
    }
}
