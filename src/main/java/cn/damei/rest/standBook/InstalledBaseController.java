package cn.damei.rest.standBook;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.utils.HttpUtils;
import cn.damei.utils.des.MD5;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@RestController
@RequestMapping("/api/standBook")
public class InstalledBaseController extends BaseController {
    @RequestMapping(value = "/installedBase",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Object installedBase(@RequestParam String orderId){
        String secretKey="7b5df6aq2we4r3t6y1vxnmhjklpewd23";
        String key = MD5.encryptKey(orderId+secretKey).toUpperCase();
        String url = PropertyHolder.getInstallBaseUrl();
        Map<String, String> params = Maps.newHashMap();
        params.put("orderId",orderId);
        params.put("key",key);
        String plan = HttpUtils.post(url,params);
        return plan;
    }
    @RequestMapping(value = "/installedBasePm",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public Object installedBasePm(@RequestParam String orderId){
        String secretKey="7b5df6aq2we4r3t6y1vxnmhjklpewd23";//接口唯一的key
        String key = MD5.encryptKey(orderId+secretKey).toUpperCase();//md5加密后的key
        String url = PropertyHolder.getInstallBasePmUrl();//获取接口地址
        Map<String, String> params = Maps.newHashMap();//构造参数
        params.put("orderId",orderId);
        params.put("key",key);
        String plan = HttpUtils.post(url,params);
        return plan;
    }
}
