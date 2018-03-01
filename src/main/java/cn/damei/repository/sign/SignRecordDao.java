package cn.damei.repository.sign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sign.Sign;
import cn.damei.entity.sign.SignRecord;
import java.util.List;
import java.util.Map;
@Repository
public interface SignRecordDao extends CrudDao<SignRecord>{
    SignRecord getByEmpIdAndDate(Map<String, Object> map);
    Integer getBySignOutTime(@Param("employeeId")Long employeeId, @Param("formatDate")String formatDate);
    SignRecord getSignOutTime(@Param("employeeId")Long employeeId, @Param("formatDate")String formatDate);
}
