package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import cn.damei.service.standBook.SelectMaterialStandBookService;
import java.util.List;
@RestController
@RequestMapping("/api/selectMaterialStandBook")
public class SelectMaterialStandBookController extends BaseController{
    @Autowired
    private SelectMaterialStandBookService selectMaterialStandBookService;
    @RequestMapping("/comboStandard")
    public Object comboStandard(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllComboStandard(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/upgradeItem")
    public Object upgradeItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllUpgradeItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/addItem")
    public Object addItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllAddItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/reduceItem")
    public Object reduceItem(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllReduceItem(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/basicInstallPrice")
    public Object basicInstallPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllbasicInstallPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/reduceItemInstallPrice")
    public Object reduceItemInstallPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllreduceItemInstallPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/otherPrice")
    public Object otherPrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllotherPrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/otherSynthesizePrice")
    public Object otherSynthesizePrice(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllOtherSynthesizePrice(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/auxiliaryMaterial")
    public Object auxiliaryMaterial(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllAuxiliaryMaterial(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
}
