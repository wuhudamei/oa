package cn.damei.service.account;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.entity.account.User;
import cn.damei.shiro.ShiroCacheHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class PermissionHandler {
    @Autowired
    private UserService userService;
    @Autowired
    private ShiroCacheHelper shiroCacheHelper;
    public void clearCacheByRoleId(Long roleId) {
        if (roleId != null) {
            List<User> users = userService.findByRoleId(roleId);
            users.forEach(e -> {
                if (StringUtils.isNotBlank(e.getUsername())) {
                    this.clearCacheByUserName(e.getUsername());
                }
            });
        }
    }
    public void clearCacheByUserId(Long userId) {
        if (userId != null) {
            this.clearCacheByUserName(Optional.ofNullable(userService.getById(userId)).map(User::getUsername).orElse(null));
        }
    }
    public void clearCacheByUserName(String userName) {
        if (StringUtils.isNotBlank(userName)) {
            shiroCacheHelper.clearAuthorizationInfo(userName);
        }
    }
}
