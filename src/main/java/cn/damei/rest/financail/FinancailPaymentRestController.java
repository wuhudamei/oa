package cn.damei.rest.financail;
import java.util.Date;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.budget.Payment;
import cn.damei.entity.budget.Signature;
import cn.damei.entity.financail.FinancailPayment;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.PaymentStatus;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.financail.FinancailPaymentService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping("/api/financail/payment")
public class FinancailPaymentRestController extends BaseController {
	@Autowired
	private FinancailPaymentService financailPaymentService;
	@Autowired
	private PaymentService paymentService;
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "status", required = false) PaymentStatus status,
			@RequestParam(value = "offset", defaultValue = "0") int offset,
			@RequestParam(value = "limit", defaultValue = "20") int limit) {
		Map<String, Object> params = Maps.newHashMap();
		MapUtils.putNotNull(params, "keyword", keyword);
		MapUtils.putNotNull(params, "status", status);
		PageTable<FinancailPayment> pageTable = this.financailPaymentService.searchScrollPage(params,
				new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
		return StatusDto.buildSuccess(pageTable);
	}
	@RequestMapping(value = "/change", method = RequestMethod.POST)
	public StatusDto change(FinancailPayment financailPayment) {
		// 补全操作人操作时间
		financailPayment.setPaymentHandler(WebUtils.getLoggedUserId());
		financailPayment.setPaymentHandleDate(new Date());
		// 判断状态不同，更新为不同状态
		if (PaymentStatus.GRANT.equals(financailPayment.getStatus())) {
			financailPayment.setStatus(PaymentStatus.TOREIMBURSED);
			// 如果是授权要给说明加一段汉字
			if (StringUtils.isNotEmpty(financailPayment.getNote())) {
				financailPayment.setNote("董事长授权说明:" + financailPayment.getNote());
			}
		} else if (PaymentStatus.TOREIMBURSED.equals(financailPayment.getStatus())) {
			financailPayment.setStatus(PaymentStatus.REIMBURSED);
		} else {
			return StatusDto.buildFailure("状态不合法");
		}
		this.financailPaymentService.update(financailPayment);
		// 报销成功后，要回写表单的状态
		if (PaymentStatus.REIMBURSED.equals(financailPayment.getStatus())) {
			Payment payment = new Payment();
			payment.setStatus(ApplyStatus.REIMBURSED);
			payment.setId(financailPayment.getFormId());
			this.paymentService.update(payment);
		}
		return StatusDto.buildSuccess("操作成功");
	}
}
