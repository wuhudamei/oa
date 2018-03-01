package cn.damei.rest.wechat.Jssdk;
import com.rocoinfo.weixin.api.JsApiTicketApi;
import com.rocoinfo.weixin.model.JssdkResult;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.UnsupportedEncodingException;
@RequestMapping(value = "/wx/jssdk")
@RestController
public class JSSDKController extends BaseController {
    @RequestMapping("/config")
    public Object getConfig(String url){
        JssdkResult signature = null;
        try {
            signature = JsApiTicketApi.signature(url);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return StatusDto.buildSuccess(signature);
    }
}
