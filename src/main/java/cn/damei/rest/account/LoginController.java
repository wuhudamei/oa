package cn.damei.rest.account;
import com.corundumstudio.socketio.SocketIOClient;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.User;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.service.account.LoginService;
import cn.damei.service.account.UserService;
import cn.damei.service.oa.WfProcessService;
import cn.damei.utils.WebUtils;
import cn.damei.utils.cache.SocketIOClientCache;
import cn.damei.utils.cache.StringKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping(value = "/api/login")
public class LoginController extends BaseController {
    @Autowired
    private LoginService loginService;
    @Autowired
    private UserService userService;
    @RequestMapping(method = RequestMethod.POST)
    public Object login(String username, String password, String uuid) {
        if (StringUtils.isAnyBlank(username, password)) {
            return StatusDto.buildFailure("用户名或密码不能为空");
        }
        StatusDto res = loginService.login(username, password, false);
        if (res.isSuccess()) {
            SocketIOClient client = SocketIOClientCache.get(uuid);
            if (client != null) {
                client.disconnect();
            }
        }
        return res;
    }
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    public Object loginUser() {
        User user = this.userService.getById(WebUtils.getLoggedUserId());
        if (user == null) {
            return StatusDto.buildWithCode("2", "no user");
        }
        this.userService.buildUserWechatHeadImg(user);
        return StatusDto.buildSuccess(user);
    }
}
