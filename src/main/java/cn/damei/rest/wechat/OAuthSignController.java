package cn.damei.rest.wechat;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
@RestController
@RequestMapping()
public class OAuthSignController extends BaseController {
    @RequestMapping("/MP_verify_KznZROUB9KVvgvAK.txt")
    public Object production(HttpServletResponse response) {
        try (PrintWriter writer = response.getWriter()) {
            writer.write("KznZROUB9KVvgvAK");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping("/MP_verify_CkSqh5WBpmK04WDs.txt")
    public Object test(HttpServletResponse response) { 
        try (PrintWriter writer = response.getWriter()) {
            writer.write("CkSqh5WBpmK04WDs");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
