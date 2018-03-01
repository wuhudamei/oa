package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.OrderDetails;
import java.util.List;
import java.util.Map;
public interface OrderDetailsDao extends CrudDao<OrderDetails> {
    List<OrderDetails> findAllOrder(Map<String ,Object> params);
    OrderDetails findOrderDetail(@Param("orderno") String orderno);
    OrderDetails findServiceNameAndStylistName(@Param("orderno")String orderno);
}
