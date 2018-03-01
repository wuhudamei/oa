package cn.damei.service.materialSupplierAccountChecking;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillBatchDetail;
import cn.damei.repository.materialSupplierAccountChecking.StRdBillBatchDetailDao;
import java.util.List;
@Service
public class StRdBillBatchDetailService extends CrudService<StRdBillBatchDetailDao, StRdBillBatchDetail> {
    public List<StRdBillBatchDetail> findAll(){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAll();
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int insert(StRdBillBatchDetail stRdBillBatchDetail){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.insert(stRdBillBatchDetail);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
    public int insertBatch(List<StRdBillBatchDetail> billBatachDetailList){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.insertBatch(billBatachDetailList);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
