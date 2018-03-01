package cn.damei.service.system;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.system.SystemParam;
import cn.damei.repository.system.SystemParamDao;
@Service
public class SystemParamService extends CrudService<SystemParamDao, SystemParam>{
	public SystemParam getByKey(String key){
		return this.entityDao.getByKey(key);
	}
}
