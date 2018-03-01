package cn.damei.rest.vacation;
import java.util.Map;
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
import cn.damei.entity.vacation.Vacation;
import cn.damei.service.vacation.VacationBusinessService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value = "/api/vacationBusiness")
public class VacationBusinessController extends BaseController {
    @Autowired
    private VacationBusinessService vacationBusinessService;
    @RequestMapping(method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
            		   @RequestParam(required = false) String status,
            		   @RequestParam(required = false,defaultValue="0") String ccFlag,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "status", status);
        if("0".equals(ccFlag)){
        	MapUtils.putOrElse(params, "userId", WebUtils.getLoggedUserId(), 0l);
        }else if("1".equals(ccFlag)){
        	MapUtils.putOrElse(params, "ccUserId", WebUtils.getLoggedUserId(), 0l);
        }
        PageTable<Vacation> pageTable = this.vacationBusinessService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pageTable);
    }
}
