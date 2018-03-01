package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.DismantleStandBook;
import java.util.*;
@Repository
public interface DismantleStandBookDao extends CrudDao<DismantleStandBook> {
    List<DismantleStandBook> findDismantle(@Param("orderNo")String orderno);
}
