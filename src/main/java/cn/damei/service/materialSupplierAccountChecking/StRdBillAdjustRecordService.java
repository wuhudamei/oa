package cn.damei.service.materialSupplierAccountChecking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillAdjustRecord;
import cn.damei.entity.materialSupplierAccountChecking.StRdBillItem;
import cn.damei.entity.sign.Sign;
import cn.damei.repository.materialSupplierAccountChecking.StRdBillAdjustRecordDao;
import java.util.List;
import java.util.Map;
@Service
public class StRdBillAdjustRecordService extends CrudService<StRdBillAdjustRecordDao, StRdBillAdjustRecord> {
    @Autowired
    private StRdBillAdjustRecordDao stRdBillAdjustRecordDao;
    public List<StRdBillAdjustRecord> findAll(String billItemId){
        DynamicDataSourceHolder.setDataSource("dataSourceMdniXC");
        try {
            return this.entityDao.findAll(billItemId);
        }catch(Exception e){
            throw e;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
