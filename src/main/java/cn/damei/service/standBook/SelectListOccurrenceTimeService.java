package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.DesignTime;
import cn.damei.repository.standBook.SelectListOccurrenceTimeDao;
@Service
public class SelectListOccurrenceTimeService extends CrudService<SelectListOccurrenceTimeDao,DesignTime> {
    public DesignTime findOccurrenceTime(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return this.entityDao.findOccurrenceTime(orderno);
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
