package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.Bill;
import cn.damei.entity.stylist.MonthBill;
import java.util.List;
@Repository
public interface BillDao extends CrudDao<Bill> {
    void batchInsert(@Param(value = "bills") List<Bill> bills);
    void changeStatusByMonth(@Param(value = "month") String month, @Param(value = "status") MonthBill.Status status);
    void changeStatusByIds(@Param(value = "ids") List<Long> Ids, @Param(value = "status") MonthBill.Status status);
    void deleteByIds(@Param(value = "ids") List<Long> Ids);
    List<Bill> getByMonthBillId(@Param(value = "monthBillId") Long monthBillId);
}