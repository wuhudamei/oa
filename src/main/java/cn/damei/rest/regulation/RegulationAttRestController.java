package cn.damei.rest.regulation;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.regulation.RegulationAtt;
import cn.damei.service.regulation.RegulationAttService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/regulations/att")
public class RegulationAttRestController extends BaseController {
    @Autowired
    private RegulationAttService regulationAttService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "regulationId", required = false) Long regulationId,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUser().getId();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "userId", userId);
        MapUtils.putNotNull(params, "regulationId", regulationId);
        PageTable<RegulationAtt> pageTable = regulationAttService.searchScrollPage(params, new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return StatusDto.buildSuccess(pageTable);
    }
}