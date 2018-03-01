package cn.damei.service.signbill;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.mdniorder.MdniOrder;
import cn.damei.entity.singleSign.SignAndPublish;
import cn.damei.repository.signbill.PublishBillDao;
import cn.damei.repository.signbill.SignBillDao;
import cn.damei.utils.DateUtils;
@Service
public class PublishBillService extends CrudService<PublishBillDao, SignAndPublish>{
	@Autowired
	private PublishBillDao publishBillDao;
	@Autowired
	private SignBillDao signBillDao;
	public synchronized String getCurrentOrderNum() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("currentDate", DateUtils.format(new Date(), "yyyyMMdd"));
        return publishBillDao.getCurrentOrderNum(paramMap);
    }
	public String getPublishBillCode() {
        return getCurrentOrderNum();
    }
	public void updateStatus(String id) {
		publishBillDao.updateStatus(id);
	}
	public void deleteBill(Long id, String signCode) {
		Integer num = publishBillDao.findTotalNumBySignCode(signCode);
		if(num == 1){
			publishBillDao.deleteById(id);
			signBillDao.deleteBySignCode(signCode);
		}else{
			publishBillDao.deleteById(id);
		}
	}
	public List<SignAndPublish> findExportBill(Map<String, Object> params) {
		List<SignAndPublish> list = publishBillDao.search(params);
		return list;
	}
}
