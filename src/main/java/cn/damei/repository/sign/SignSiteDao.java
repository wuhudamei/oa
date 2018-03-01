package cn.damei.repository.sign;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sign.SignSite;
import java.util.List;
@Repository
public interface SignSiteDao extends CrudDao<SignSite>{
    List<SignSite> findSignScope();
}
