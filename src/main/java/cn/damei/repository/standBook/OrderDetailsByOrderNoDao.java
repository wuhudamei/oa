package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.OrderDetails;
@Repository
public interface OrderDetailsByOrderNoDao extends CrudDao<OrderDetails> {
    OrderDetails findOrderDetailByOrderNo(@Param("orderno") String orderno);
}
