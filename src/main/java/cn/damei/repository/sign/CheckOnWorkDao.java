package cn.damei.repository.sign;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sign.Sign;
import java.util.List;
import java.util.Map;
@Repository
public interface CheckOnWorkDao extends CrudDao<Sign>{
    List<Sign> findCheckOnWork(Map<String, Object> params);
    Long findSignNum();
    List<Sign> findCollect(Map<String, Object> params);
    List<Sign> findAllCount(Map<String, Object> params);
    List<Sign> findAllcount(Map<String, Object> params);
}
