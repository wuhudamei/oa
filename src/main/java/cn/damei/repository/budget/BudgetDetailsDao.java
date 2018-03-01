package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.BudgetDetails;
import java.util.List;
@Repository
public interface BudgetDetailsDao extends CrudDao<BudgetDetails> {
    void deleteByBudgetId(@Param(value = "budgetId") Long budgetId);
    void batchInsert(@Param(value = "budgetDetails") List<BudgetDetails> budgetDetails);
    List<BudgetDetails> getByBudgetId(@Param(value = "budgetId") Long budgetId);
}