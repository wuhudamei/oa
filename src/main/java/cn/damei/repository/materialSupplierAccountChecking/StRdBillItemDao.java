package cn.damei.repository.materialSupplierAccountChecking;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillItem;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface StRdBillItemDao  extends CrudDao<StRdBillItem> {
    List<Map<String,String>> findSupplyAndGood(@Param("status") String status,@Param("accep") String accep);
    List<StRdBillItem> findItemBySupplyAndSkuName(@Param("pdSupplier") String pdSupplier, @Param("brandname") String brandname , @Param("startTime") String startTime , @Param("endTime") String  endTime, @Param("orderNo") String  orderNo);
    List<StRdBillItem> findItemByAcceptance(@Param("pdSupplier") String pdSupplier, @Param("pdSkuname") String pdSkuname,@Param("keyword") String keyword);
    List<StRdBillItem> findBillBatchDetail(@Param("orderNo") String orderNo,@Param("pdSupplier") String pdSupplier,@Param("brandname") String brandname,@Param("startTime") String startTime,@Param("endTime") String endTime);
    int updateCount(@Param("billItemId") String billItemId,@Param("pdCurrentCount") BigDecimal pdCurrentCount);
    int updateBatch(@Param("accumulateSettlementCount") BigDecimal accumulateSettlementCount,@Param("accumulateSettlementMoney") BigDecimal accumulateSettlementMoney,@Param("pdCurrentCount") BigDecimal pdCurrentCount,@Param("reconciliationStatus") String reconciliationStatus,@Param("change")Boolean change,@Param("billItemId") String billItemId);
    StRdBillItem getByBillItemId(@Param("billItemId")String billItemId);
    List<StRdBillItem> findCheckOnWork(List<String> billItemIds );
    int checkAndAccept(Map<String, Object> params);
    List<Date> findAllAcceptanceTime();
    List<StRdBillItem> findMoneyByStatus();
    List<StRdBillItem> findCountByStatus();
    List<StRdBillItem> findPrice();
    int updatePrice(@Param("price") BigDecimal price,@Param("billItemId") String billItemId);
    List<StRdBillItem> findOrderByNoAccountCHecking(@Param("pdSupplier") String pdSupplier, @Param("brandname") String brandname, @Param("startTime") String startTime, @Param("endTime") String endTime,@Param("orderNo") String orderNo);
}
