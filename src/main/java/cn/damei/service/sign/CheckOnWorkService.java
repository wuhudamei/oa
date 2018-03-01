package cn.damei.service.sign;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sign.Sign;
import cn.damei.repository.sign.CheckOnWorkDao;
import java.util.List;
import java.util.Map;
@Service
public class CheckOnWorkService extends CrudService<CheckOnWorkDao,Sign>{
    public List<Sign> findCheckOnWork(Map<String, Object> params) {
        return entityDao.findCheckOnWork(params);
    }
    public Long findSignNum() {
        return entityDao.findSignNum();
    }
    public List<Sign> findCollect(Map<String, Object> params) {
        return entityDao.findCollect(params);
    }
    public List<Sign> findAllCount(Map<String, Object> params) {
        return entityDao.findAllCount(params);
    }
    public List<Sign> findAllcount(Map<String, Object> params) {
        return entityDao.findAllcount(params);
    }
}
