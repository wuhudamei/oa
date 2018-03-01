package cn.damei.service.materialSupplierAccountChecking;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatch;
import cn.damei.repository.materialSupplierAccountChecking.StRdBillBatchDao;
import cn.damei.utils.WebUtils;
import java.util.Date;
import java.util.List;
@Service
public class StRdBillBatchService extends CrudService<StRdBillBatchDao, StRdBillBatch> {
    public List<StRdBillBatch> findAll(String supplier, String startTime, String endTime){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAll(supplier,startTime,endTime);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int insert(StRdBillBatch stRdBillBatch){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.insert(stRdBillBatch);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
