package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.Budget;
import cn.damei.entity.budget.BudgetMoney;
import cn.damei.entity.budget.BudgetResultMap;
import cn.damei.enumeration.ApplyStatus;
import java.util.List;
import java.util.Map;
@Repository
public interface BudgetDao extends CrudDao<Budget> {
    List<Budget> getByBudgetDate(@Param(value = "companyId") Long companyId,
                                 @Param(value = "budgetDate") String budgetDate,
                                 @Param(value = "status") ApplyStatus status);
    BudgetResultMap getBudgetDetailsByParam(Map<String, Object> params);
    Long sumBudgetByCompany(@Param(value = "companyId") Long companyId);
    List<BudgetMoney> getExecution(@Param("date") String date, @Param("type") Long type, @Param("companyId") Long companyId);
    List<BudgetMoney> getMonthBudget(@Param("date") String date, @Param("type") Long type, @Param("companyId") Long companyId);
    List<BudgetMoney> getMonthBudgetExcludeId(@Param("date") String date, @Param("type") Long type, @Param("companyId") Long companyId, @Param("excludeId") Long excludeId);
    List<BudgetMoney> getMonthBudgetGroupByMonth(@Param("date") String date, @Param("type") Long type, @Param("companyId") Long companyId);
}