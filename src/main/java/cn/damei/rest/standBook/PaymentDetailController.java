package cn.damei.rest.standBook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.service.standBook.PaymentDetailService;
@RestController
@RequestMapping("/api/standBook")
public class PaymentDetailController extends BaseController {
	@Autowired
	private PaymentDetailService paymentDetailService;
	@RequestMapping("/getPayment")
	public Object findPayment(String orderno){
		return StatusDto.buildSuccess(this.paymentDetailService.findPayment(orderno));
	}
}
