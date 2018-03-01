package cn.damei.repository.materialSupplierAccountChecking;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatch;
import java.util.List;
@Repository
public interface StRdBillBatchDao extends CrudDao<StRdBillBatch> {
    List<StRdBillBatch>  findAll(@Param("supplier") String supplier, @Param("startTime") String startTime, @Param("endTime") String endTime);
    String selectByDate(@Param("currentDate") String currentDate);
}
