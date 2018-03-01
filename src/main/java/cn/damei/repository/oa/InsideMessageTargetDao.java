package cn.damei.repository.oa;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.oa.InsideMessageTarget;
import java.util.List;
@Repository
public interface InsideMessageTargetDao extends CrudDao<InsideMessageTarget> {
    void changeStatus(InsideMessageTarget insideMessageTarget);
    void batchInsert(@Param(value = "targets") List<InsideMessageTarget> targets);
}
