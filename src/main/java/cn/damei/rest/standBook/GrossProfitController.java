package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.standBook.GrossProfitStandBook;
import cn.damei.service.standBook.GrossProfitStandBookService;
import java.util.List;
@RestController
@RequestMapping("/api/grossProfit")
public class GrossProfitController extends BaseController {
    @Autowired
    private GrossProfitStandBookService grossProfitStandBookService;
    @RequestMapping("/findGrossProfit")
    public Object findGrossProfit(@RequestParam String orderno){
        List<GrossProfitStandBook> grossProfitList = this.grossProfitStandBookService.findGrossProfit(orderno);
        return StatusDto.buildSuccess(grossProfitList);
    }
    @RequestMapping("/findChangeMoney")
    public Object findChangeMoney(@RequestParam String orderno){
        GrossProfitStandBook grossProfit = this.grossProfitStandBookService.findChangeMoney(orderno);
        return StatusDto.buildSuccess(grossProfit);
    }
}
