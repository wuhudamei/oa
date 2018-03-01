package cn.damei.service.sign;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignRecord;
import cn.damei.repository.sign.SignRecordDao;
import java.util.List;
import java.util.Map;
@Service
public class SignRecordService extends CrudService<SignRecordDao,SignRecord>{
    public SignRecord getByEmpIdAndDate(Map<String,Object> map) {
        return entityDao.getByEmpIdAndDate(map);
    }
    public Integer getBySignOutTime(Long employeeId,String formatDate) {
        return this.entityDao.getBySignOutTime(employeeId,formatDate);
    }
    public SignRecord getSignOutTime(Long employeeId, String formatDate) {
        return this.entityDao.getSignOutTime(employeeId,formatDate);
    }
}
