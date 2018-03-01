package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.DismantleStandBook;
import cn.damei.repository.standBook.DismantleStandBookDao;
import java.util.*;
@Service
public class DismantleStandBookService extends CrudService<DismantleStandBookDao,DismantleStandBook> {
    @Autowired
    private DismantleStandBookDao dismantleStandBookDao;
    public List<DismantleStandBook> findDismantle(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            List<DismantleStandBook> dismantleStandBookList = this.dismantleStandBookDao.findDismantle(orderno);
            return dismantleStandBookList;
        }catch (Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
