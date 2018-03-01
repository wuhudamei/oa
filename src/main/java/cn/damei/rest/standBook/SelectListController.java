package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.dto.StatusDto;
import cn.damei.entity.standBook.DesignTime;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import cn.damei.service.standBook.SelectListOccurrenceTimeService;
import cn.damei.service.standBook.SelectListService;
import java.util.List;
@RestController
@RequestMapping("/api/selectList")
public class SelectListController extends BaseController{
    @Autowired
    private SelectListService selectListService;
    @Autowired
    private SelectListOccurrenceTimeService selectListOccurrenceTimeService;
    @RequestMapping("/comboStandard")
    public Object comboStandard(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllComboStandard(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/upgradeItem")
    public Object upgradeItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllUpgradeItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/addItem")
    public Object addItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllAddItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/reduceItem")
    public Object reduceItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllReduceItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/basicInstallPrice")
    public Object basicInstallPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllbasicInstallPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/reduceItemInstallPrice")
    public Object reduceItemInstallPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllreduceItemInstallPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/otherPrice")
    public Object otherPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllotherPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/otherSynthesizePrice")
    public Object otherSynthesizePrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllOtherSynthesizePrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/auxiliaryMaterial")
    public Object auxiliaryMaterial(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectListService.findAllAuxiliaryMaterial(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/occurrenceTime")
    public Object findOccurrenceTime(@RequestParam String orderno) {
        DesignTime designTime = this.selectListOccurrenceTimeService.findOccurrenceTime(orderno);
        return StatusDto.buildSuccess(designTime);
    }
}
