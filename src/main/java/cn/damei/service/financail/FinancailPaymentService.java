package cn.damei.service.financail;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.financail.FinancailPayment;
import cn.damei.repository.financail.FinancailPaymentDao;
@Service
public class FinancailPaymentService extends CrudService<FinancailPaymentDao, FinancailPayment> {
}
