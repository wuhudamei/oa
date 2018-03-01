package cn.damei.service.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.AuditChangeStandBook;
import cn.damei.entity.standBook.AuditStandBook;
import cn.damei.repository.standBook.AuditChangeStandBookDao;
import cn.damei.repository.standBook.AuditStandBookDao;
import java.util.Collections;
import java.util.List;
@Service
public class AuditStandBookService extends CrudService<AuditStandBookDao,AuditStandBook> {
    @Autowired
    private AuditChangeStandBookDao auditChangeStandBookDao;
    public AuditStandBook findContractAudit(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findContractAudit(orderno);
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public List<AuditChangeStandBook> findChangeAudit(String orderno) {
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            List<AuditChangeStandBook> auditStandBookList = this.auditChangeStandBookDao.findChangeAudit(orderno);
            return auditStandBookList;
        }catch(Exception e){
            return Collections.emptyList();
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
