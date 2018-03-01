package cn.damei.repository.oa;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.oa.ApplySequence;
@Repository
public interface ApplySequenceDao extends CrudDao<ApplySequence> {
    Integer  getSequence(ApplySequence applySequence);
    void updateSequence(ApplySequence applySequence);
}