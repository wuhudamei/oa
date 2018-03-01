package cn.damei.repository.wechat;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.wechat.WechatMessageLog;
@Repository
public interface WechatMessageLogDao extends CrudDao<WechatMessageLog> {
}