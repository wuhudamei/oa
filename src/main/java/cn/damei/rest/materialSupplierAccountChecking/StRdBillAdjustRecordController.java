package cn.damei.rest.materialSupplierAccountChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillAdjustRecord;
import cn.damei.service.materialSupplierAccountChecking.StRdBillAdjustRecordService;
@RestController
@RequestMapping(value = "/api/stRdBillAdjustRecord")
public class StRdBillAdjustRecordController extends BaseController {
    @Autowired
    private StRdBillAdjustRecordService stRdBillAdjustRecordService;
    @RequestMapping("/findAll")
    public Object findAll(@RequestParam String billItemId) {
        return StatusDto.buildSuccess(this.stRdBillAdjustRecordService.findAll(billItemId));
    }
    @RequestMapping("/insert")
    public Object insert(StRdBillAdjustRecord stRdBillAdjustRecord) {
        return StatusDto.buildSuccess(this.stRdBillAdjustRecordService.insert(stRdBillAdjustRecord));
    }
}
