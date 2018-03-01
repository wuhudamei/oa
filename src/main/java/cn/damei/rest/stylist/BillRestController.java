package cn.damei.rest.stylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.service.stylist.BillDetailsService;
import cn.damei.service.stylist.BillService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/stylist/bills")
public class BillRestController extends BaseController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailsService billDetailsService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "searchMonth", required = false) String searchMonth,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        ShiroUser logged = WebUtils.getLoggedUser();
        Map<String, Object> params = new HashMap<>();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "searchMonth", searchMonth);
        PageTable pageTable = billService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/generateBill", method = RequestMethod.GET)
    public StatusDto generateBill(@RequestParam(value = "generateMonth") String generateMonth) {
        ShiroUser logged = WebUtils.getLoggedUser();
        return billService.generateBill(generateMonth, logged);
    }
    @RequestMapping(value = "/{id}/findDetails", method = RequestMethod.GET)
    public StatusDto findByBillId(@PathVariable(value = "id") Long billId) {
        return StatusDto.buildSuccess(billDetailsService.findByBillId(billId));
    }
    @RequestMapping(value = "/{id}/submit", method = RequestMethod.GET)
    public StatusDto commit(@PathVariable(value = "id") Long id) {
        if (id == null) {
            return StatusDto.buildFailure("请传入要确认的月度提成账单Id");
        }
        this.billService.commit(id);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}/rollback", method = RequestMethod.GET)
    public StatusDto rollback(@PathVariable(value = "id") Long id) {
        if (id == null) {
            return StatusDto.buildFailure("请传入要取消的月度提成账单Id");
        }
        this.billService.rollback(id);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}/getByMonthBillId", method = RequestMethod.GET)
    public StatusDto getByMonthBillId(@PathVariable(value = "id") Long monthBillId) {
        return StatusDto.buildSuccess(billService.getByMonthBillId(monthBillId));
    }
}
