package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.SignatureDetails;
import cn.damei.enumeration.ApplyStatus;
import java.util.List;
@Repository
public interface SignatureDetailsDao extends CrudDao<SignatureDetails> {
    void deleteBySignatureId(@Param(value = "signatureId") Long signatureId);
    void batchInsert(@Param(value = "signatureDetails") List<SignatureDetails> signatureDetails);
    List<SignatureDetails> getBySignatureId(@Param(value = "signatureId") Long signatureId);
    List<SignatureDetails> findSignatureDetails(@Param("companyId") Long companyId, @Param("signatureMonth") String signatureMonth, @Param("type") Long type, @Param("statuses") List<ApplyStatus> statuses);
}
