package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.entity.budget.Budget;
import cn.damei.repository.budget.BudgetDao;
import cn.damei.service.budget.BudgetService;
public class BudgetTest extends SpringTestCase {
    @Autowired
    private BudgetService budgetService;
    @Autowired
    private BudgetDao budgetDao;
    @Test
    public void testBudgetDao() {
        Budget budget = budgetDao.getById(6L);
        System.out.println(budget);
    }
    @Test
    public void testGetExecutions() {
        this.budgetService.getExecution("2017", 4L, 1L);
    }
}
