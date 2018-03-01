package cn.damei.repository.oa;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.oa.WfProcess;
@Repository
public interface WfProcessDao extends CrudDao<WfProcess> {
	int batchInsert(@Param("list") List<WfProcess> list);
	int updateReadTime(Map<String, Object> paramMap);
	int updateCurrentLev(WfProcess wfProcess);
	int updateRefuse(WfProcess wfProcess);
	int updateReset(Long id);
	int updateNextReset(Long id);
	List<WfProcess> getNextNode(Map<String, Object> paramMap);
	List<WfProcess> getCurrentLeveIsEnd(Map<String, Object> paramMap);
	List<WfProcess> findApproveDetailByFormId(Map<String, Object> params);
	List<WfProcess> getProcessByCondition(Map<String, String> params);
	int updateFormTable(Map<String, String> paramMap);
	int updateApplyCcUser(Map<String, String> paramMap);
	int updateApplyCcUserStatus(Map<String, String> paramMap);
	List<WfProcess> getByFormId(Long formId);
	List<Map<String,Object>> getWfprocessByFormId(Long formId);
	public int updateWfProcessSupserNodeId(Map<String, Object> paramMap);
	public int updateNextNodeSupserNodeId(Map<String, Object> paramMap);
	List<Map<String, Object>> sourceData(Long id);
}
