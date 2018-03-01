package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.ChangeStandBook;
import cn.damei.repository.standBook.ChangeStandBookDao;
import java.util.List;
@Service
public class ChangeStandBookService extends CrudService<ChangeStandBookDao,ChangeStandBook> {
    public List<ChangeStandBook> findChange(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            List<ChangeStandBook> list = this.entityDao.findChange(orderno);
            return list;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
