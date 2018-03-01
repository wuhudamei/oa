package cn.damei.repository.oa;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.oa.InsideMessage;
import cn.damei.entity.oa.InsideMessageWithStatus;
import java.util.List;
import java.util.Map;
@Repository
public interface InsideMessageDao extends CrudDao<InsideMessage> {
    List<InsideMessage> mineMesForIndex(@Param("userId") Long userId);
    List<InsideMessageWithStatus> searchForList(Map<String, Object> params);
}
