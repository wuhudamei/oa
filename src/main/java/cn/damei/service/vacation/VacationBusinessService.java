package cn.damei.service.vacation;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.vacation.VacationBusiness;
import cn.damei.repository.vacation.VacationBusinessDao;
@Service
public class VacationBusinessService extends CrudService<VacationBusinessDao, VacationBusiness> {
}
