package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.service.standBook.FinanceService;
@RestController
@RequestMapping("/api/standBook")
public class FinanceController extends BaseController {
	@Autowired
	private FinanceService financeService;
	@RequestMapping("/getFinance")
	public Object findFinance(String orderno){
		return StatusDto.buildSuccess(this.financeService.findFinance(orderno));
	}
	@RequestMapping("/findFinanceDownPayment")
	public Object findFinanceDownPayment(String orderno){
		return StatusDto.buildSuccess(this.financeService.findFinanceDownPayment(orderno));
	}
}
