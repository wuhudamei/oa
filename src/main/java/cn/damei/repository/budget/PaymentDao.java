package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.Payment;
@Repository
public interface PaymentDao extends CrudDao<Payment> {
    Double sumPaymentByCompany(@Param(value = "companyId") Long companyId);
}