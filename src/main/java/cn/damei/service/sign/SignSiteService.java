package cn.damei.service.sign;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sign.SignSite;
import cn.damei.repository.sign.SignSiteDao;
import java.util.List;
@Service
public class SignSiteService extends CrudService<SignSiteDao,SignSite>{
    public List<SignSite> findSignScope() {
      return  entityDao.findSignScope();
    }
    public Integer createOrUpdate(SignSite role) {
        SignSite signSite = entityDao.getById(role.getId());
        if (signSite == null) {
            return entityDao.insert(role);
        }else {
            return entityDao.update(role);
        }
    }
}
