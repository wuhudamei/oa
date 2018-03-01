package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.Evaluate;
import java.util.List;
import java.util.Map;
@Repository
public interface EvaluateDao extends CrudDao<Evaluate> {
    List<Evaluate> getEvaluateByCondition(Map<String, String> paramMap);
    int batchInsert(@Param(value = "evaluateList") List<Evaluate> evaluateList);
}