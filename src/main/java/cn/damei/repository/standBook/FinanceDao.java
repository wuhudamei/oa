package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.Finance;
import java.util.List;
@Repository
public interface FinanceDao extends CrudDao<Finance> {
    List<Finance> findFinance(@Param("orderno") String orderno);
    List<Finance> findFinanceDownPayment(@Param("orderno") String orderno);
}
