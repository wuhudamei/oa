package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.ContractStatus;
import java.util.List;
@Repository
public interface ContractStatusDao extends CrudDao<ContractStatus> {
    void batchInsert(@Param(value = "contractStatuses") List<ContractStatus> contractStatuses);
}