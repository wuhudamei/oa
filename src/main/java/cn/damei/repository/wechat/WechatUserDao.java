package cn.damei.repository.wechat;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.WechatUser;
import java.util.List;
@Repository
public interface WechatUserDao extends CrudDao<WechatUser> {
    WechatUser getByOpenid(@Param("openid") String openid);
    int updateByOpenId(WechatUser wechatUser);
    List<WechatUser> getByNickname(String nickname);
    List<WechatUser> getByUserIds(@Param(value = "userIds") List<Long> userIds);
    WechatUser getByUserId(@Param("userId") Long userId);
    List<WechatUser> getByUserJobNum(@Param("jobNums") List<String> jobNums);
    List<String> getByParentOrgIds(@Param(value = "orgIds") List<Long> orgIds);
    int deleteByUserId(@Param(value = "userId") Long userId);
    void deleteWechatUser(@Param("openid") String openid,@Param("userId") Long userId,@Param("jobNum") String jobNum);
    List<WechatUser> findWechatUser(@Param("jobNum") String jobNum);
}