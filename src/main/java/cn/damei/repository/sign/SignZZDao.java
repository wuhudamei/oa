package cn.damei.repository.sign;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.sign.Sign;
import cn.damei.enumeration.SignType;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Repository
public interface SignZZDao extends CrudDao<Sign> {
    int findWorkCoefficient(@Param("empId") Long empId, @Param("firstDay") String firstDay, @Param("lastDay") String lastDay);
}
