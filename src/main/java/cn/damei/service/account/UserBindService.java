package cn.damei.service.account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.account.UserBind;
import cn.damei.repository.account.UserBindDao;
import java.util.List;
@Service
public class UserBindService extends CrudService<UserBindDao, UserBind> {
    public UserBind getByOidAndPlatform(String oid, UserBind.Platfrom platfrom) {
        if (StringUtils.isNotBlank(oid)) {
            return this.entityDao.getByOidAndPlatform(oid, platfrom);
        }
        return null;
    }
    public List<UserBind> getUserId(Long userId) {
        return this.entityDao.getByUserId(userId, UserBind.Platfrom.WECHAT);
    }
    public List<UserBind> getByUserIds(List<Long> userIds, UserBind.Platfrom wechat) {
        return this.entityDao.getByUserIds(userIds,wechat);
    }
}
