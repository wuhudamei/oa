package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.PaymentDetails;
import cn.damei.enumeration.ApplyStatus;
import java.util.List;
@Repository
public interface PaymentDetailsDao extends CrudDao<PaymentDetails> {
    void deleteByPaymentId(@Param(value = "paymentId") Long paymentId);
    void batchInsert(@Param(value = "paymentDetails") List<PaymentDetails> paymentDetails);
    List<PaymentDetails> getByPaymentId(@Param(value = "paymentId") Long paymentId);
    List<PaymentDetails> findByConditions(@Param("companyId") Long companyId, @Param("type") Long type, @Param("month") String month, @Param("statuses") List<ApplyStatus> statuses);
    List<PaymentDetails> getDetailsBySignatureId(@Param("signatureId") Long signatureId, @Param("statuses") List<ApplyStatus> statuses);
    public void deletePaymentId(@Param("paymentId") Long paymentId);
}