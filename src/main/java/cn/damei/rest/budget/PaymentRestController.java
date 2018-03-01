package cn.damei.rest.budget;
import com.google.common.collect.Maps;
import com.lowagie.text.DocumentException;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.common.view.ViewDownLoad;
import cn.damei.dto.StatusDto;
import cn.damei.dto.budget.PaymentDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.budget.Payment;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.enumeration.WfNodeStatus;
import cn.damei.service.budget.PaymentService;
import cn.damei.service.process.ProcessService;
import cn.damei.service.subjectprocess.SubjectProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/payments")
public class PaymentRestController extends BaseController {
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ProcessService processService;
    @Autowired
    private SubjectProcessService subjectProcessService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "ccFlag", required = false, defaultValue = "0") String ccFlag,
                            @RequestParam(value = "status", required = false) ApplyStatus status,
                            @RequestParam(value = "startDate", required = false) String startDate,
                            @RequestParam(value = "endDate", required = false) String endDate,
                            @RequestParam(value = "dataType", required = false) String dataType,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUser().getId();
        Map<String, Object> params = Maps.newHashMap();
        if ("0".equals(ccFlag)) {
            MapUtils.putNotNull(params, "userId", userId);
        } else if ("1".equals(ccFlag)) {
            MapUtils.putNotNull(params, "ccUserId", userId);
        }
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "startData", startDate);
        MapUtils.putNotNull(params, "endDate", endDate);
    	if (WfNodeStatus.PENDING.name().equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.PENDING.name());
			MapUtils.putNotNull(params, "userId", userId);
		} else if (WfNodeStatus.COMPLETE.name().equals(dataType)) {// 已审批
			MapUtils.putNotNull(params, "dataType", WfNodeStatus.COMPLETE.name());
		} else if ("CCUSER".equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", "CCUSER");
		}else if("APPROVED".equals(dataType)) {
			MapUtils.putNotNull(params, "dataType", "APPROVED");
		}
        PageTable<Payment> pageTable = paymentService.searchScrollPage(params, new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/getApplyCode", method = RequestMethod.GET)
    public StatusDto getApplyCode() {
        String applyCode = paymentService.getApplyCode();
        StatusDto statusDto = StatusDto.buildSuccess();
        statusDto.setData(applyCode);
        return statusDto;
    }
    @RequestMapping(method = RequestMethod.POST)
    public StatusDto createOrUpdate(@RequestBody Payment payment) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null) {
            return StatusDto.buildFailure("请刷新页面，重新登录！");
        }
        String result = checkBudget(payment);
        if (result != null) {
            return StatusDto.buildFailure(result);
        }
        if (payment.getStatus() == ApplyStatus.DRAFT) {
            paymentService.createOrUpdate(payment, loggedUser, true);
        } else {
            if (subjectProcessService.findWfByCondition(WfApplyTypeEnum.EXPENSE.name(), payment.getType(), payment.getCostItem()) == null) {
                return StatusDto.buildFailure("该流程类型和费用科目的审批流程不存在！");
            }
            int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.EXPENSE.name(), getWfNatureStr(payment.getSurpassBudget()), loggedUser.getOrgId());
            if (rowNum > 0) {
                paymentService.createOrUpdate(payment, loggedUser, false);
            } else {
                return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
            }
        }
        return StatusDto.buildSuccess("操作成功！");
    }
    @RequestMapping("/{paymentId}/commit")
    public StatusDto save2Draft(@PathVariable(value = "paymentId") Long paymentId) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        Payment payment = paymentService.getById(paymentId);
        if (payment == null) {
            return StatusDto.buildFailure("没有查询到您要提交的报销记录！");
        }
        if (subjectProcessService.findWfByCondition(WfApplyTypeEnum.EXPENSE.name(), payment.getType(), payment.getCostItem()) == null) {
            return StatusDto.buildFailure("该流程类型和费用科目的审批流程不存在！");
        }
        int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.EXPENSE.name(), getWfNatureStr(payment.getSurpassBudget()), loggedUser.getOrgId());
        if (rowNum > 0) {
            if (paymentService.commit(payment, loggedUser)) {
                return StatusDto.buildSuccess();
            }
        } else {
            return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
        }
        return StatusDto.buildFailure("提交预算失败！");
    }
    private String getWfNatureStr(Integer surpassBudget) {
        String wfNature = "";
        if (surpassBudget == 0) {//未超出
            wfNature = WfNatureEnum.EXECUTE.name();
        } else {//超出预算
            wfNature = WfNatureEnum.APPROVAL.name();
        }
        return wfNature;
    }
    private String checkBudget(Payment payment) {
        return null;
    }
    @RequestMapping(value = "/{paymentId}/get", method = RequestMethod.GET)
    public StatusDto getDetails(@PathVariable(value = "paymentId") Long paymentId) {
        Payment payment = paymentService.getDetails(paymentId);
        if (payment == null) {
            return StatusDto.buildFailure("没有此报销信息！");
        }
        return StatusDto.buildSuccess(payment);
    }
    @RequestMapping(value = "/{id}/del", method = RequestMethod.DELETE)
    public StatusDto delete(@PathVariable(value = "id") Long id) {
        paymentService.deleteById(id);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/multiple/{paymentId}", method = RequestMethod.GET)
    public StatusDto getBudgetMult(@PathVariable(value = "paymentId") Long paymentId) {
        Long firstTypeId = null;
        PaymentDto paymentDto = paymentService.getMultiDetails(paymentId);
        if (paymentDto == null) {
            return StatusDto.buildFailure("没有此报销信息！");
        }
        return StatusDto.buildSuccess(paymentDto);
    }
    @RequestMapping(value = "/getRemain", method = RequestMethod.GET)
    public StatusDto getRemains(Long signatureId, Long type, String month) {
        if (type == null || month == null) {
            return StatusDto.buildFailure("请指定查询参数！");
        }
        ShiroUser logged = WebUtils.getLoggedUser();
        Map<Long, Double> remains = paymentService.calculateRemain(signatureId, logged.getCompanyId(), type, month);
        return StatusDto.buildSuccess(remains);
    }
    @RequestMapping(value = "/viewPayment/{id}")
    public Object viewSignature(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
                                HttpSession session) throws IOException, DocumentException {
        String fileFullPath = this.paymentService.creataPdf(id, request, response, session);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.parseMediaType("application/pdf"));
        return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(new File(fileFullPath)), httpHeaders,
                HttpStatus.OK);
    }
    @RequestMapping(value = "/print/{id}")
    public ModelAndView print(@PathVariable Long id, HttpServletRequest request, HttpServletResponse response,
                              HttpSession session) throws DocumentException, IOException {
        String fileFullPath = this.paymentService.creataPdf(id, request, response, session);
        View viewDownLoad = new ViewDownLoad(new File(fileFullPath), "application/pdf");
        return new ModelAndView(viewDownLoad);
    }
}