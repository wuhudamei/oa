package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.Finance;
import cn.damei.repository.standBook.FinanceDao;
import java.util.List;
@Service
public class FinanceService extends CrudService<FinanceDao, Finance> {
    public List<Finance> findFinance(String orderno){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return this.entityDao.findFinance(orderno);
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<Finance> findFinanceDownPayment(String orderno){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return this.entityDao.findFinanceDownPayment(orderno);
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
