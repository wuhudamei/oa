package cn.damei.service.stylist;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.MonthBill;
import cn.damei.repository.stylist.MonthBillDao;
import cn.damei.utils.MapUtils;
import org.springframework.stereotype.Service;
import java.util.Map;
@Service
public class MonthBillService  extends CrudService<MonthBillDao,MonthBill> {
    public void changeStatusByMonth(String month, MonthBill.Status status) {
        if (StringUtils.isNotBlank(month) && status != null) {
            this.changeStatusByCondition(MapUtils.of("month", month, "status", status));
        }
    }
    public void changeStatus(Long id, MonthBill.Status status) {
        if(id != null && status != null){
            this.changeStatusByCondition(MapUtils.of("id", id, "status", status));
        }
    }
    private void changeStatusByCondition(Map<String, ? extends Object> params) {
        this.entityDao.changeStatusByCondition(params);
    }
}