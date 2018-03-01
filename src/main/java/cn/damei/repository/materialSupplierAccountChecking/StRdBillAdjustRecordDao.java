package cn.damei.repository.materialSupplierAccountChecking;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillAdjustRecord;
import java.util.List;
@Repository
public interface StRdBillAdjustRecordDao extends CrudDao<StRdBillAdjustRecord> {
    List<StRdBillAdjustRecord> findAll(@Param("billItemId") String billItemId);
}
