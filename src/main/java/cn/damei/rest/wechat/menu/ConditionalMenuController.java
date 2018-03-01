package cn.damei.rest.wechat.menu;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.wechat.menu.ConditionalMenu;
import cn.damei.service.wechat.menu.ConditionalMenuService;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/wx/condition/menu")
public class ConditionalMenuController extends BaseController {
    @Autowired
    private ConditionalMenuService conditionalMenuService;
    @RequestMapping(method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        PageTable page = this.conditionalMenuService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody ConditionalMenu menu) {
        int i = this.conditionalMenuService.insert(menu);
        if (i > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        return StatusDto.buildSuccess(this.conditionalMenuService.getById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object edit(@RequestBody ConditionalMenu menu) {
        int i = this.conditionalMenuService.update(menu);
        if (i > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteById(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        int i = this.conditionalMenuService.deleteById(id);
        if (i > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("删除失败");
    }
    @RequestMapping(value = "/sync/{id}", method = RequestMethod.GET)
    public Object sync(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        boolean b = this.conditionalMenuService.sync(id);
        if (b) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("同步失败");
    }
}
