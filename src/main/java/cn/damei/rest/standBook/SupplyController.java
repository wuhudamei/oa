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
@RequestMapping("/api/supply")
public class SupplyController extends BaseController {
    @RequestMapping(value = "/findSupplyInfoByContractNo",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findSupplyInfoByContractNo(@RequestParam String contractNo){
        String secretKey="7b5df6aq2we4r3t6y1vxnmhjklpewd23";
        String key = MD5.encryptKey(contractNo+secretKey).toUpperCase();
        Map<String, String> params = Maps.newHashMap();
        params.put("contractNo",contractNo);
        params.put("key",key);
        String url = PropertyHolder.getSupplyUrl();
        String supply = HttpUtils.post(url,params);
        return supply;
    }
}
