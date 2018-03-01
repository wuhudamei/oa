package cn.damei.repository.stylist;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.stylist.BillDetails;
import java.util.List;
@Repository
public interface BillDetailsDao extends CrudDao<BillDetails> {
    List<BillDetails> findByBillId(@Param(value = "billId") Long billId);
    void batchInsert(@Param(value = "bills") List<BillDetails> billDetails);
    void deleteByBillIds(@Param(value = "billIds") List<Long> billIds);
}