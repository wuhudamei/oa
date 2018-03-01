package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.YearBudgetDetail;
import java.util.List;
@Repository
public interface YearBudgetDetailDao extends CrudDao<YearBudgetDetail> {
    void batchInsert(@Param(value = "details") List<YearBudgetDetail> details);
    void deleteByBudgetId(@Param(value = "budgetId") Long budgetId);
    List<YearBudgetDetail> getByBudgetId(@Param(value = "yearBudgetId") Long yearBudgetId);
    YearBudgetDetail getBySubjectCode(@Param(value = "subjectCode") Long subjectCode,
                                      @Param(value = "budgetYear") String budgetYear,
                                      @Param(value = "applyCompany") Long companyId);
}