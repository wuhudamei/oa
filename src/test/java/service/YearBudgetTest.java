package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.service.budget.YearBudgetDetailService;
import cn.damei.service.budget.YearBudgetService;
public class YearBudgetTest extends SpringTestCase {
    @Autowired
    YearBudgetDetailService yearBudgetDetailService;
    @Autowired
    YearBudgetService yearBudgetService;
    @Test
    public void testBudget() {
        yearBudgetService.getYearBudgetByType(3L, "2017", 2L);
    }
}
