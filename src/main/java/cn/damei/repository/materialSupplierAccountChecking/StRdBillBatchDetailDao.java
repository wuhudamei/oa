package cn.damei.repository.materialSupplierAccountChecking;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatchDetail;
import java.util.List;
@Repository
public interface StRdBillBatchDetailDao extends CrudDao<StRdBillBatchDetail> {
    List<StRdBillBatchDetail> findAll();
    int insertBatch(List<StRdBillBatchDetail> billBatachDetailList);
}
