package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusBootTableDto;
import cn.damei.entity.standBook.SelectMaterialStandBook;
import cn.damei.service.standBook.MaterialCostStandBookService;
import cn.damei.service.standBook.SelectMaterialStandBookService;
import java.util.List;
@RestController
@RequestMapping("/api/materialCostStandBook")
public class MaterialCostStandBookController extends BaseController{
    @Autowired
    private MaterialCostStandBookService materialCostStandBookService;
    @Autowired
    private SelectMaterialStandBookService selectMaterialStandBookService;
    @RequestMapping("/principalMaterial")
    public Object principalMaterial(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.materialCostStandBookService.findAllPrincipalMaterial(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
    @RequestMapping("/auxiliaryMaterial")
    public Object auxiliaryMaterial(@RequestParam(value = "orderno") String orderNo){
        List<SelectMaterialStandBook> selectMaterialStandBookDetails = this.selectMaterialStandBookService.findAllAuxiliaryMaterial(orderNo);
        return StatusBootTableDto.buildDataSuccessStatusDto(selectMaterialStandBookDetails,Long.parseLong(selectMaterialStandBookDetails.size()+""));
    }
}
