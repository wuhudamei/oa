package cn.damei.repository.regulation;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.regulation.Regulation;
@Repository
public interface RegulationDao extends CrudDao<Regulation> {
}