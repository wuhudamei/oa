package cn.damei.service.wechat;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.wechat.WechatMessageLog;
import cn.damei.repository.wechat.WechatMessageLogDao;
@Service
public class WechatMessageLogService extends CrudService<WechatMessageLogDao, WechatMessageLog> {
}
