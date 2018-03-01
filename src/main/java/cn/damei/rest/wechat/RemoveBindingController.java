package cn.damei.rest.wechat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
import java.util.ArrayList;
import java.util.List;
@RestController
@RequestMapping("/wx/removeBinding")
public class RemoveBindingController {
    @Autowired
    WechatUserService wechatUserService;
    @RequestMapping("/removeUser")
    public Object removeBinding(){
        ShiroUser user = WebUtils.getLoggedUser();
        String jobNum = user.getJobNum();
        List<WechatUser> wechatUserList = wechatUserService.findWechatUser(jobNum);
        for (WechatUser wechatUser : wechatUserList) {
            wechatUserService.deleteWechatUser(wechatUser.getOpenid(),wechatUser.getUserId(),jobNum);
        }
        return StatusDto.buildSuccess();
    }
}
