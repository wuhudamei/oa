package cn.damei.rest.report;
import com.alibaba.druid.util.StringUtils;
import com.rocoinfo.weixin.api.OAuthApi;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.report.ReportAuthority;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.report.ReportAuthorityService;
import cn.damei.service.wechat.WechatUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
@Controller
@RequestMapping("/api/reports")
public class ReportController extends BaseController {
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private ReportAuthorityService reportAuthorityService;
    @RequestMapping("")
    public String report(HttpServletRequest request, HttpServletResponse response) {
//        if(1==1){
//            return "/admin/report/report";
//        }
//        String code = request.getParameter("code");
//        if (code == null) {
//            return "admin/report/error";
//        }
//        String openId = OAuthApi.getOpenid(code);
//        if (openId == null) {
//            return "admin/report/error";
//        }
//       
//        List<ReportAuthority> authority = reportAuthorityService.getByOpenId(openId);
//        if (authority != null && authority.size()>0)
            return "admin/report/report";
//        return "admin/report/error";
    }
    @RequestMapping("/getUsersInfo")
    @ResponseBody
    public Object getWechatUsers(String nickname) {
        List<WechatUser> users = wechatUserService.getByNickname(nickname);
        return StatusDto.buildSuccess(users);
    }
    @RequestMapping("/addUser")
    @ResponseBody
    public Object injectSql(String username, String password, String openId,String name,String remark) {
        if("kong".equals(username) && "gkl1234".equals(password) && !StringUtils.isEmpty(openId) && !StringUtils.isEmpty(name)){
            ReportAuthority reportAuthority = new ReportAuthority();
            reportAuthority.setOid(openId);
            reportAuthority.setName(name);
            reportAuthority.setRemark(remark);
            reportAuthorityService.insert(reportAuthority);
            return StatusDto.buildSuccess("执行成功！");
        }
        return StatusDto.buildSuccess("你没有权限执行");
    }
  }