package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.AuditChangeStandBook;
import java.util.List;
@Repository
public interface AuditChangeStandBookDao extends CrudDao<AuditChangeStandBook> {
    List<AuditChangeStandBook> findChangeAudit(@Param("orderNo") String orderno);
}
