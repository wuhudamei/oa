package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.GrossProfitStandBook;
import cn.damei.repository.standBook.GrossProfitStandBookDao;
import java.util.Collections;
import java.util.List;
@Service
public class GrossProfitStandBookService extends CrudService<GrossProfitStandBookDao,GrossProfitStandBook>{
    @Autowired
    private GrossProfitStandBookDao grossProfitStandBookDao;
    public List<GrossProfitStandBook> findGrossProfit(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            List<GrossProfitStandBook> list = this.grossProfitStandBookDao.findGrossProfit(orderNo);
            return list;
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public GrossProfitStandBook findChangeMoney(String orderNo) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            GrossProfitStandBook grossProfitStandBook = this.grossProfitStandBookDao.findChangeMoney(orderNo);
            return grossProfitStandBook;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
