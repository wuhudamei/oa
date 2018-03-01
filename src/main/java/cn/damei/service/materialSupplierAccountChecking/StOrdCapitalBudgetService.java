package cn.damei.service.materialSupplierAccountChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.materialSupplierAccountChecking.StOrdCapitalBudget;
import cn.damei.repository.materialSupplierAccountChecking.StOrdCapitalBudgetDao;
import java.util.List;
@Service
public class StOrdCapitalBudgetService extends CrudService<StOrdCapitalBudgetDao,StOrdCapitalBudget> {
    @Autowired
    private StOrdCapitalBudgetDao stOrdCapitalBudgetDao;
    public List<StOrdCapitalBudget> findStOrdCapitalBudgetByNoAndMobileAndName( String keyword, String predictStartTime,String predictEndTime, String startTime, String endTime){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return this.stOrdCapitalBudgetDao.findStOrdCapitalBudgetByNoAndMobileAndName(keyword,predictStartTime,predictEndTime,startTime,endTime);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
