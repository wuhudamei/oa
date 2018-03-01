package cn.damei.repository.vacation;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.vacation.VacationBusiness;
@Repository
public interface VacationBusinessDao extends CrudDao<VacationBusiness> {
}
