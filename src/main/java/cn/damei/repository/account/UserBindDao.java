package cn.damei.repository.account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.account.UserBind;
import java.util.List;
@Repository
public interface UserBindDao extends CrudDao<UserBind> {
    UserBind getByOidAndPlatform(@Param("oid") String oid, @Param("platform") UserBind.Platfrom platform);
    List<UserBind> getByUserId(@Param(value = "userId") Long userId, @Param(value = "platform") UserBind.Platfrom platform);
    List<UserBind> getByUserIds(@Param(value = "userIds") List<Long> userIds, @Param(value = "platform") UserBind.Platfrom platform);
}
