package cn.damei.service.stylist;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.stylist.BillDetails;
import cn.damei.repository.stylist.BillDetailsDao;
import java.util.Collections;
import java.util.List;
@Service
public class BillDetailsService extends CrudService<BillDetailsDao, BillDetails> {
    public List<BillDetails> findByBillId(Long billId) {
        if (billId == null) {
            return Collections.emptyList();
        }
        return this.entityDao.findByBillId(billId);
    }
    public void batchInsert(List<BillDetails> billDetails) {
        if (CollectionUtils.isNotEmpty(billDetails)) {
            this.entityDao.batchInsert(billDetails);
        }
    }
    public void deleteByBillIds(List<Long> billIds) {
        if (CollectionUtils.isNotEmpty(billIds)) {
            this.entityDao.deleteByBillIds(billIds);
        }
    }
}