package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.Signature;
import cn.damei.enumeration.ApplyStatus;
import java.util.List;
@Repository
public interface SignatureDao extends CrudDao<Signature> {
    List<Signature> findByMonthAndUser(@Param("userId") Long userId, @Param("month") String month, @Param("statuses") List<ApplyStatus> statuses, @Param("paymentId") Long paymentId);
    public void updateUseStaus(@Param("signatureId")Long signatureId,@Param("targetValue")Integer targetValue);
}