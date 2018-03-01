package cn.damei.rest.wechat;
import com.rocoinfo.weixin.util.SignUtils;
import cn.damei.common.BaseController;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@RestController
@RequestMapping(value = "/wx")
public class SignController extends BaseController {
    @RequestMapping(method = RequestMethod.GET)
    public Object sign(@RequestParam String signature,
                       @RequestParam String timestamp,
                       @RequestParam String nonce,
                       @RequestParam String echostr,
                       HttpServletResponse response) {
        if (StringUtils.isNoneBlank(signature, timestamp, nonce, echostr) &&
                SignUtils.checkSignature(signature, timestamp, nonce)) {
            try (PrintWriter writer = response.getWriter()) {
                writer.write(echostr);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
