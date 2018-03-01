package cn.damei.rest.wechat.tag;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.wechat.tag.Tag;
import cn.damei.entity.wechat.tag.TagEmployee;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.service.wechat.tag.TagEmployeeService;
import cn.damei.service.wechat.tag.TagService;
import cn.damei.utils.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/wx/tag")
public class TagController extends BaseController {
    @Autowired
    private TagService tagService;
    @Autowired
    private TagEmployeeService tagEmployeeService;
    @RequestMapping(method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        PageTable page = this.tagService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Tag wechatTag) {
        boolean b = this.tagService.create(wechatTag);
        if (b) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("创建失败");
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        return StatusDto.buildSuccess(this.tagService.getById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody Tag wechatTag) {
        boolean b = this.tagService.edit(wechatTag);
        if (b) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("创建失败");
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteById(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        boolean b = this.tagService.delete(id);
        if (b) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Object findAll() {
        List<Tag> tags = this.tagService.findAll();
        return StatusDto.buildSuccess(tags);
    }
    @RequestMapping(value = "/{id}/employee/list", method = RequestMethod.GET)
    public Object listTaggedEmployee(@PathVariable(value = "id") Long tagId,
                                     @RequestParam(required = false) String keyword,
                                     @RequestParam(required = false) String orgCode,
                                     @RequestParam(required = false) EmployeeStatus employeeStatus,
                                     @RequestParam(defaultValue = "0") int offset,
                                     @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "tagId", tagId);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "orgCode", orgCode);
        MapUtils.putNotNull(params, "employeeStatus", employeeStatus);
        PageTable page = this.tagEmployeeService.findByTagIdScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value = "/{id}/employee", method = RequestMethod.GET)
    public Object findAllTaggedEmployee(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("标签id不能为null");
        }
        List<TagEmployee> list = this.tagEmployeeService.findAllByTagId(id);
        return StatusDto.buildSuccess(list);
    }
    @RequestMapping(value = "/{id}/employee", method = RequestMethod.POST)
    public Object batchTag(@PathVariable(value = "id") Long tagId, @RequestBody List<Long> empIds) {
        return this.tagService.tagEmployees(tagId, empIds);
    }
    @RequestMapping(value = "/{id}/untag", method = RequestMethod.GET)
    public Object unTag(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        boolean b = this.tagService.untag(id);
        if (b){
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure();
    }
}
