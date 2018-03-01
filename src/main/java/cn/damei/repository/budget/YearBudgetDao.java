package cn.damei.repository.budget;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.budget.YearBudget;
@Repository
public interface YearBudgetDao extends CrudDao<YearBudget> {
    int getByTypeAndYear(@Param(value = "type") String type,
            @Param(value = "budgetYear") Integer budgetYear,
                         @Param(value = "applyCompany") Long applyCompany);
}