package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.utils.HttpUtil;
import cn.damei.utils.HttpUtils;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
@RestController
@RequestMapping("/api/principalInstallStandBook")
public class PrincipalInstallController extends BaseController {
    @RequestMapping(value = "/findPrincipalInstall",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findPrincipalInstall(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getPrincipalInstallUrl();
        String plan = HttpUtil.post(url,orderno,type);
        return plan;
    }
    @RequestMapping(value = "/findReview",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public Object findReview(@RequestParam String contractNo){
        HashMap<String , String > param = Maps.newHashMap();
        param.put("contractNo",contractNo);
        String url = PropertyHolder.getReviewUrl();
        String reviewList = HttpUtils.post(url,param);
        return reviewList;
    }
}
