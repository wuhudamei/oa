package cn.damei.repository.standBook;
import org.apache.ibatis.annotations.Param;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.standBook.PaymentDetail;
public interface PaymentDetailDao extends CrudDao<PaymentDetail> {
    PaymentDetail findPayment(@Param("orderno") String orderno);
}
