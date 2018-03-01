package cn.damei.repository.completionSiteData;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.completionSiteData.ContractInformation;
import java.util.List;
import java.util.Map;
@Repository
public interface CompletionSiteDataDao extends CrudDao<ContractInformation> {
    List<ContractInformation> getCompletionSiteData(Map<String, Object> params);
    int batchInsert(@Param(value = "contractInformationList") List<ContractInformation> contractInformationList);
    ContractInformation getContractTotalAmountByProjectCode(String orderNumber);
    ContractInformation getPaidTotalAmountByProjectCode(String orderNumber);
    ContractInformation getChangeTotalAmountByProjectCode(String orderNumber);
    void deleteByStoreName(@Param(value = "storeName")String storeName);
}
