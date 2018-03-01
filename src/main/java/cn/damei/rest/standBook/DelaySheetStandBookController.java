package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.utils.HttpUtil;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.des.MD5;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/delaySheetStandBook")
public class DelaySheetStandBookController extends BaseController {
    @RequestMapping(value = "/findDelaySheetByOrderNo",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findDelaySheetByOrderNo(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getProjectUrl();
        String plan = HttpUtil.post(url,orderno,type);
        return plan;
    }
}
