package cn.damei.repository.process;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.process.ProcessEntity;
@Repository
public interface ProcessDao extends CrudDao<ProcessEntity> {
	List<ProcessEntity> findTemplateByCondition(Map<String, Object> paramMap);
	List<ProcessEntity> findTemplateByConditionByDayOrMoney(Map<String, Object> paramMap);
	ProcessEntity findTemplateAllPath(Map<String, Object> paramMap);
	List<ProcessEntity> findExecuteTemplateByCondition(Map<String, Object> paramMap);
	int checkOrgRepeat(@Param("type") String type, @Param("nature") String nature, @Param("id") Long id,
			@Param("org") String org);
	int findWfByTypeAndOrg(@Param("type") String type, @Param("wfNature") String wfNature, @Param("org") Long org);
	void updateAllPathById(@Param("allPath") String allPath, @Param("id") Long id);
}