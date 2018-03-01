package cn.damei.service.budget;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.budget.YearBudgetDetail;
import cn.damei.repository.budget.YearBudgetDetailDao;
import java.util.List;
@Service
public class YearBudgetDetailService extends CrudService<YearBudgetDetailDao, YearBudgetDetail> {
    public void deleteByBudgetId(Long budgetId) {
        this.entityDao.deleteByBudgetId(budgetId);
    }
    public List<YearBudgetDetail> getByBudgetId(Long yearBudgetId) {
        return this.entityDao.getByBudgetId(yearBudgetId);
    }
    public void DeleteByBudgetId(Long budgetId) {
        this.entityDao.deleteByBudgetId(budgetId);
    }
    public YearBudgetDetail getBySubjectCode(Long subjectCode, String budgetYear, Long companyId) {
        return this.entityDao.getBySubjectCode(subjectCode, budgetYear, companyId);
    }
}