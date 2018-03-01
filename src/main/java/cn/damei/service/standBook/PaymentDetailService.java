package cn.damei.service.standBook;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.standBook.PaymentDetail;
import cn.damei.repository.standBook.PaymentDetailDao;
@Service
public class PaymentDetailService extends CrudService<PaymentDetailDao, PaymentDetail> {
    public PaymentDetail findPayment(String orderno){
        DynamicDataSourceHolder.setDataSource("dataSourceMdni");
        try {
            return this.entityDao.findPayment(orderno);
        }catch(Exception e){
            return null;
        }finally {
            DynamicDataSourceHolder.clearDataSource();//清除此数据源
        }
    }
}
