package cn.damei.service.oa;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.oa.InsideMessage;
import cn.damei.repository.oa.InsideMessageDao;
@Service
public class InsideMessageService extends CrudService<InsideMessageDao, InsideMessage> {
}
