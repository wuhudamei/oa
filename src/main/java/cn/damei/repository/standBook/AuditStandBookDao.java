package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.AuditStandBook;
import java.util.List;
@Repository
public interface AuditStandBookDao extends CrudDao<AuditStandBook> {
    AuditStandBook findContractAudit(@Param("orderNo") String orderno);
}
