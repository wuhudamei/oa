package cn.damei.rest.stylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import cn.damei.common.persistence.CrudDao;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.stylist.MonthBill;
import cn.damei.repository.stylist.MonthBillDao;
import cn.damei.service.stylist.MonthBillService;
import cn.damei.utils.MapUtils;
import java.util.HashMap;
import java.util.Map;
@RestController
@RequestMapping("/api/stylist/monthBills")
public class MonthBillRestController {
    @Autowired
    private MonthBillService monthBillService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "month", required = false) String month,
                            @RequestParam(value = "status", defaultValue = "NORMAL") MonthBill.Status status,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Map<String, Object> params = new HashMap<>();
        MapUtils.putNotNull(params, CrudDao.KEYWORD, keyword);
        MapUtils.putNotNull(params, MonthBillDao.MONTH, month);
        params.put(CrudDao.STATUS, status);
        return StatusDto.buildSuccess(monthBillService.searchScrollPage(params, new Pagination(offset, limit)));
    }
}
