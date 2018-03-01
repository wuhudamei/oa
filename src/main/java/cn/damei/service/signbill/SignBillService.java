package cn.damei.service.signbill;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.entity.singleSign.PublishBill;
import cn.damei.entity.singleSign.SignBill;
import cn.damei.repository.signbill.PublishBillDao;
import cn.damei.repository.signbill.SignBillDao;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.DateUtils;
import cn.damei.utils.WebUtils;
@Service
public class SignBillService extends CrudService<SignBillDao, SignBill>{
	@Autowired
	private SignBillDao signBillDao;
	@Autowired
	private PublishBillDao publishBillDao;
	@Autowired
	private PublishBillService PublishBillService;
	public void insertBill(SignBill signBill) {
			//给signbill新增数据
			String signCode = getSignBillCode();
			signBill.setSignCode(signCode);
			ShiroUser loggedUser = WebUtils.getLoggedUser();
			String name = loggedUser.getName();
			signBill.setCreateUser(name);
			signBill.setCreateTime(new Date());
			signBillDao.insert(signBill);
			//得到punishBill数据，新增
			List<PublishBill> list = signBill.getPublishBillList();
			if(list.size()>0){
				for (int i = 0; i < list.size(); i++) {
					PublishBill publishBill = signBill.getPublishBillList().get(i);
					String punishCode = PublishBillService.getPublishBillCode();
					publishBill.setPunishCode(punishCode);
					publishBill.setSignCode(signCode);
					publishBill.setStatus(0);
					publishBill.setCreateUser(name);
					publishBill.setCreateTime(new Date());
					publishBillDao.insertPublishBill(publishBill);
				}
			}
	}
	public synchronized String getCurrentOrderNum() {
        Map<String, String> paramMap = new HashMap<String, String>();
        paramMap.put("currentDate", DateUtils.format(new Date(), "yyyyMMdd"));
        return signBillDao.getCurrentOrderNum(paramMap);
    }
	public String getSignBillCode() {
        return getCurrentOrderNum();
    }
}
