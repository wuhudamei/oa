package cn.damei.repository.system;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.system.SystemParam;
@Repository
public interface SystemParamDao extends CrudDao<SystemParam>{
	SystemParam getByKey(@Param("paramKey")String key);
}
