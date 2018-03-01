package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.Evaluate;
import cn.damei.repository.stylist.EvaluateDao;
import cn.damei.shiro.ShiroUser;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class EvaluateService extends CrudService<EvaluateDao, Evaluate> {
    @Autowired
    private EvaluateDao evaluateDao;
    public int insert(Evaluate entity, ShiroUser logged) throws Exception {
        entity.setCreateUser(logged.getId());
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("jobNo", entity.getJobNo());
        paramMap.put("evaluateMonth", entity.getEvaluateMonth());
        List<Evaluate> empEvaluate = this.getEvaluateByCondition(paramMap);
        if (empEvaluate == null || empEvaluate.size() <= 0) {
            return super.insert(entity);
        } else {
            return -1;
        }
    }
    public int batchInsert(List<Evaluate> evaluateList, ShiroUser logged) {
        if (CollectionUtils.isNotEmpty(evaluateList)) {
            evaluateList.forEach(evaluate -> {
                evaluate.setCreateUser(logged.getId());
            });
            return this.entityDao.batchInsert(evaluateList);
        }
        return 0;
    }
    public List<Evaluate> getEvaluateByCondition(Map<String, String> paramMap) {
        return evaluateDao.getEvaluateByCondition(paramMap);
    }
    public List<Evaluate> findByMonth(String generateMonth) {
        Map<String, String> paramMap = new HashMap<>();
        paramMap.put("evaluateMonth", generateMonth);
        return getEvaluateByCondition(paramMap);
    }
}