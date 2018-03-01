package cn.damei.repository.signbill;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.mdniorder.MdniOrder;
import cn.damei.entity.singleSign.SignBill;
@Repository
public interface SignBillDao extends CrudDao<SignBill>{
	String getCurrentOrderNum(Map<String, String> paramMap);
	void deleteBySignCode(String signCode);
	List<MdniOrder> getOrderInfoByCondition(Map<String,String> parMap);
}
