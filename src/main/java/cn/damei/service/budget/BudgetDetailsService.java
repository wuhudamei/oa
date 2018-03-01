package cn.damei.service.budget;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.budget.BudgetDetails;
import cn.damei.repository.budget.BudgetDetailsDao;
@Service
public class BudgetDetailsService  extends CrudService<BudgetDetailsDao,BudgetDetails> {
}