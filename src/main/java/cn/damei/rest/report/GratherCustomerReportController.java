package cn.damei.rest.report;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import cn.damei.common.BaseController;
@Controller
@RequestMapping("/api/grathercustomer")
public class GratherCustomerReportController extends BaseController {
    @RequestMapping("")
    public String grathercustomerReport(HttpServletRequest request, HttpServletResponse response) {
    	return "admin/report/grathercustomer";
    }
  }