package cn.damei.repository.signbill;
import java.util.Map;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.singleSign.PublishBill;
import cn.damei.entity.singleSign.SignAndPublish;
@Repository
public interface PublishBillDao extends CrudDao<SignAndPublish>{
	void insertPublishBill(PublishBill publishBill);
	String getCurrentOrderNum(Map<String, String> paramMap);
	void updateStatus(String id);
	Integer findTotalNumBySignCode(String signCode);
}
