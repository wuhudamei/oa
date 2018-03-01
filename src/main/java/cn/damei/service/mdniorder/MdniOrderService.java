package cn.damei.service.mdniorder;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.datasource.DynamicDataSourceHolder;
import cn.damei.entity.mdniorder.MdniOrder;
import cn.damei.repository.mdniorder.MdniOrderDao;
@Service
public class MdniOrderService extends CrudService<MdniOrderDao, MdniOrder>{
	@Autowired
	private MdniOrderDao mdniOrderDao;
    public List<MdniOrder> getOrderInfoByCondition(Map<String,String> parMap) {
    	DynamicDataSourceHolder.setDataSource("dataSourceMdni");
		try {
			List<MdniOrder> midOrderList = mdniOrderDao.getOrderInfoByCondition(parMap);
			return midOrderList;
		}catch(Exception e){
			throw e;
		}finally {
			DynamicDataSourceHolder.clearDataSource();//清除此数据源
		}
    }
}
