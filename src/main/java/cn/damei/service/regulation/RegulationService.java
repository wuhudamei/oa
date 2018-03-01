package cn.damei.service.regulation;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.regulation.Regulation;
import cn.damei.repository.regulation.RegulationDao;
@Service
public class RegulationService extends CrudService<RegulationDao, Regulation> {
    public void createOrUpdate(Regulation regulation) {
        if (regulation.getId() == null) {
            super.insert(regulation);
        } else {
            super.update(regulation);
        }
    }
}