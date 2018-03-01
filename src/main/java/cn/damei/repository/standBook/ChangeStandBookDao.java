package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.ChangeStandBook;
import java.util.List;
@Repository
public interface ChangeStandBookDao extends CrudDao<ChangeStandBook> {
    List<ChangeStandBook> findChange(@Param("orderno") String orderno);
}
