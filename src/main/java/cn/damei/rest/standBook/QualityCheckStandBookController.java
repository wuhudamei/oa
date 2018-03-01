package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.entity.standBook.AreaLevelInfo;
import cn.damei.service.standBook.QualityCheckStandBookService;
import cn.damei.utils.HttpUtil;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.des.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/qualityCheckStandBook")
public class QualityCheckStandBookController extends BaseController {
    @Autowired
    private QualityCheckStandBookService qualityCheckStandBookService;
    @RequestMapping(value = "/findQualityCheckByOrderNo",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findQualityCheck(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getQualityCheckUrl();
        String plan = HttpUtil.post(url,orderno,type);
        return plan;
    }
    @RequestMapping(value = "/findRepeatQualityCheck",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findRepeatQualityCheck(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getRepeatQualityCheckUrl();
        String plan = HttpUtil.post(url,orderno,type);
        return plan;
    }
    @RequestMapping(value = "/findAreaLevelInfo",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findAreaLevelInfo(@RequestParam String orderNumber){
        String secretKey = PropertyHolder.getWorkerMd5();
        String key = MD5.encryptKey(orderNumber+secretKey).toUpperCase();
        Map<String, String> params = Maps.newHashMap();
        params.put("orderNumber",orderNumber);
        params.put("key",key);
        String url = PropertyHolder.getAreaLevelInfoUrl();
        String plan = HttpUtils.post(url,params);
        List<AreaLevelInfo> areaLevelInfoList = null;
        if(plan != null && plan.length()>0){
            areaLevelInfoList = qualityCheckStandBookService.findAreaLevelInfoByOrderNo(plan,orderNumber);
        }
        return areaLevelInfoList;
    }
}
