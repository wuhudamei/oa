package cn.damei.service.subjectprocess;
import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.subjectprocess.SubjectProcess;
import cn.damei.repository.subjectprocess.SubjectProcessDao;
@SuppressWarnings("all")
@Service
public class SubjectProcessService extends CrudService<SubjectProcessDao, SubjectProcess> {
	public SubjectProcess findWfByCondition(String wfType,Long processTypeId,Long subjectId){
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("wfType", wfType);
		paramMap.put("processTypeId", String.valueOf(processTypeId));
		paramMap.put("subjectId", String.valueOf(subjectId));
		return this.entityDao.findWfByCondition(paramMap);
	}
	public boolean checkExistForSubAndPro(String wfType, Long processTypeId, Long subjectId) {
        return this.entityDao.checkExistForSubAndPro(wfType, processTypeId, subjectId) > 0 ? true : false;
    }
	public int getByWfId(Long wfId) {
		return this.entityDao.getByWfId(wfId);
	}
}
