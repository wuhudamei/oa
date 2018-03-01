package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.Rule;
import java.util.List;
@Repository
public interface RuleDao extends CrudDao<Rule> {
    void clean();
    void batchInsert(@Param(value = "rules") List<Rule> rules);
}