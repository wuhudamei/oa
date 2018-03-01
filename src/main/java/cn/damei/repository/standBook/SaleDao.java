package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.OrderDetails;
import cn.damei.entity.standBook.Sale;
import java.util.List;
import java.util.Map;
@Repository
public interface SaleDao extends CrudDao<Sale> {
    Sale findOrderCustomerByOrderNo(String orderno);
    Sale findPaymentByOrderNo(String orderno);
    Sale findPlaceByOrderNo(String orderno);
}
