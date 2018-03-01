package cn.damei.repository.materialSupplierAccountChecking;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.materialSupplierAccountChecking.StOrdCapitalBudget;
import java.util.List;
@Repository
public interface StOrdCapitalBudgetDao extends CrudDao<StOrdCapitalBudget> {
    List<StOrdCapitalBudget> findStOrdCapitalBudgetByNoAndMobileAndName(@Param("keyword") String keyword,@Param("predictStartTime") String predictStartTime,@Param("predictEndTime") String predictEndTime,@Param("startTime") String startTime,@Param("endTime") String endTime);
}
