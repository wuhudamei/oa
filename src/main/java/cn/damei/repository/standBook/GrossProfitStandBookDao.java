package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.GrossProfitStandBook;
import java.util.List;
@Repository
public interface GrossProfitStandBookDao extends CrudDao<GrossProfitStandBook> {
    List<GrossProfitStandBook> findGrossProfit(@Param("orderNo") String orderno);
    GrossProfitStandBook findChangeMoney(@Param("orderNo") String orderno);
}
