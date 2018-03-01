package cn.damei.repository.subjectprocess;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.subjectprocess.SubjectProcess;
@Repository
public interface SubjectProcessDao extends CrudDao<SubjectProcess> {
	SubjectProcess findWfByCondition(Map<String,String> paramMap);
	int checkExistForSubAndPro(@Param("wfType") String wfType, @Param("proId") Long proId, @Param("subId") Long subId);
	int getByWfId(@Param("wfId") Long wfId);
}
