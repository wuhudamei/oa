package cn.damei.repository.financail;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.financail.FinancailPayment;
@Repository
public interface FinancailPaymentDao extends CrudDao<FinancailPayment> {
}
