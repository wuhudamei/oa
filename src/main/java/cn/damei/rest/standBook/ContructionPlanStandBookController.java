package cn.damei.rest.standBook;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.utils.HttpUtil;
@RestController
@RequestMapping("/api/standBook")
public class ContructionPlanStandBookController extends BaseController {
    @RequestMapping(value = "/findPlanAndDoneTimeByOrderNo",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findPlanAndDoneTimeByOrderNo(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getStandBookPlanUrl();
        String plan = HttpUtil.post(url,orderno,type);
        return plan;
    }
}
