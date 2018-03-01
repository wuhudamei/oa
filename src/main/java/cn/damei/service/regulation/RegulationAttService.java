package cn.damei.service.regulation;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.regulation.RegulationAtt;
import cn.damei.repository.regulation.RegulationAttDao;
@Service
public class RegulationAttService  extends CrudService<RegulationAttDao,RegulationAtt> {
}