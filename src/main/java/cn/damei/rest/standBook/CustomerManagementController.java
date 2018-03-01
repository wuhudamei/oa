package cn.damei.rest.standBook;
import org.eclipse.jetty.util.ajax.JSON;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.common.PropertyHolder;
import cn.damei.utils.HttpUtil;
@RestController
@RequestMapping("/api/customerManagement")
public class CustomerManagementController extends BaseController {
    @RequestMapping(value = "/getCustomerManagement",method = RequestMethod.GET,produces = "application/json; charset=utf-8")
    public @ResponseBody Object findCustomerManagementInfoByOrderNo(@RequestParam String orderno){
        String type = "";
        String url = PropertyHolder.getCustomerManagementUrl();
        String customerManagement = HttpUtil.post(url,orderno,type);
        return customerManagement;
    }
}
