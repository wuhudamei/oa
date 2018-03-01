package cn.damei.repository.stylist;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.MonthBill;
import java.util.Map;
@Repository
public interface MonthBillDao extends CrudDao<MonthBill> {
    String MONTH = "month";
    void changeStatusByCondition(Map<String, ?> params);
}