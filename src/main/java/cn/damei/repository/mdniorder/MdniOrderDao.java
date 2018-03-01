package cn.damei.repository.mdniorder;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.mdniorder.MdniOrder;
@Repository
public interface MdniOrderDao extends CrudDao<MdniOrder> {
	List<MdniOrder> getOrderInfoByCondition(Map<String,String> parMap);
}
