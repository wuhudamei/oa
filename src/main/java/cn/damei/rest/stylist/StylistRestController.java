package cn.damei.rest.stylist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.stylist.Stylist;
import cn.damei.service.stylist.StylistService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/stylists")
public class StylistRestController extends BaseController {
    @Autowired
    private StylistService stylistService;
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(required = false) String keyword,
                            @RequestParam(required = false, defaultValue = "0") int offset,
                            @RequestParam(required = false, defaultValue = "20") int limit) {
        Map<String, Object> params = new HashMap<>();
        MapUtils.putNotNull(params, "keyword", keyword);
        PageTable<Stylist> page = stylistService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value = "/{id}/del",method = RequestMethod.DELETE)
    public StatusDto deleteById(@PathVariable(value = "id") Long id) {
        stylistService.deleteById(id);
        return StatusDto.buildSuccess("删除成功！");
    }
    @RequestMapping(value = "/batchInsert", method = RequestMethod.POST)
    public StatusDto create(@RequestBody List<Stylist> stylists) {
        ShiroUser logged = WebUtils.getLoggedUser();
        stylistService.batchInsert(stylists, logged);
        return StatusDto.buildSuccess("添加设计师成功！");
    }
}
