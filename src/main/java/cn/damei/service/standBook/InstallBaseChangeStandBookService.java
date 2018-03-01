package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.InstallBaseChangeStandBook;
import cn.damei.repository.standBook.InstallBaseChangeStandBookDao;
import java.util.List;
@Service
public class InstallBaseChangeStandBookService extends CrudService<InstallBaseChangeStandBookDao,InstallBaseChangeStandBook> {
    public List<InstallBaseChangeStandBook> findInstallBaseChange(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            List<InstallBaseChangeStandBook> list = this.entityDao.findInstallBaseChange(orderno);
            return list;
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
