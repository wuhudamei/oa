package cn.damei.service.wechat;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.repository.wechat.WechatUserDao;
import java.util.List;
@Service
public class WechatUserService extends CrudService<WechatUserDao, WechatUser> {
	public WechatUser getByOpenid(@Param("openid") String openid) {
		if (StringUtils.isNotBlank(openid))
			return this.entityDao.getByOpenid(openid);
		return null;
	}
	public int updateByOpenId(WechatUser wechatUser) {
		return this.entityDao.updateByOpenId(wechatUser);
	}
	public List<WechatUser> getByNickname(String nickname) {
		return this.entityDao.getByNickname(nickname);
	}
	public List<WechatUser> getByUserIds(List<Long> userIds) {
		return this.entityDao.getByUserIds(userIds);
	}
	public WechatUser getByUserId(Long id) {
		if (id == null)
			return null;
		return this.entityDao.getByUserId(id);
	}
	public List<WechatUser> getByUserJobNum(List<String> jobNumList) {
		if (jobNumList == null || jobNumList.size() <=0) {
			return null;
		}
		return this.entityDao.getByUserJobNum(jobNumList);
	}
	public List<String> getByParentOrgIds(List<Long> orgIds) {
		if (CollectionUtils.isEmpty(orgIds)) {
			return this.entityDao.getByParentOrgIds(null);
		}
		return this.entityDao.getByParentOrgIds(orgIds);
	}
	public void deleteByUserId(Long id) {
		this.entityDao.deleteByUserId(id);
	}
    public void deleteWechatUser(String openid, Long userId, String jobNum) {
		entityDao.deleteWechatUser(openid,userId,jobNum);
    }
	public List<WechatUser> findWechatUser(String jobNum) {
		return entityDao.findWechatUser(jobNum);
	}
}
