package cn.damei.repository.standBook;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.AreaLevelInfo;
import java.util.List;
@Repository
public interface QualityCheckStandBookDao extends CrudDao<AreaLevelInfo> {
    List<AreaLevelInfo> findAreaLevelInfoByOrderNo(String orderno);
}
