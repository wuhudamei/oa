package cn.damei.repository.commonapply;
import java.util.Map;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.commonapply.ApplyCommon;
@Repository
public interface ApplyCommonDao extends CrudDao<ApplyCommon> {
	int updateReset(Long id);
	Map<String, Object> sourceData(Long id);
}
