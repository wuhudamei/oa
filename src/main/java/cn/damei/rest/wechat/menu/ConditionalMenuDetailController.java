package cn.damei.rest.wechat.menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.wechat.menu.MenuTreeDto;
import cn.damei.entity.wechat.menu.ConditionalMenu;
import cn.damei.entity.wechat.menu.ConditionalMenuDetail;
import cn.damei.entity.wechat.menu.Menu;
import cn.damei.repository.wechat.menu.ConditionalMenuDao;
import cn.damei.service.wechat.menu.ConditionalMenuDetailService;
import cn.damei.service.wechat.menu.MenuService;
import java.util.List;
@RestController
@RequestMapping("/api/wx/condition/detail")
public class ConditionalMenuDetailController extends BaseController {
    @Autowired
    private ConditionalMenuDetailService conditionalMenuDetailService;
    @RequestMapping(value = "/tree/{cid}", method = RequestMethod.GET)
    public Object tree(@PathVariable Long cid) {
        List<ConditionalMenuDetail> list = this.conditionalMenuDetailService.findByCid(cid);
        MenuTreeDto tree = MenuTreeDto.buildTree(list);
        return StatusDto.buildSuccess(tree.getChildren());
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody ConditionalMenuDetail menu) {
        MenuTreeDto treeDto = this.conditionalMenuDetailService.create(menu);
        if (treeDto != null) {
            return StatusDto.buildSuccess(treeDto);
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getById(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        return StatusDto.buildSuccess(this.conditionalMenuDetailService.getById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object edit(@RequestBody ConditionalMenuDetail menu) {
        MenuTreeDto treeDto = this.conditionalMenuDetailService.edit(menu);
        if (treeDto != null) {
            return StatusDto.buildSuccess(treeDto);
        }
        return StatusDto.buildFailure();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object deleteById(@PathVariable Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为null");
        }
        int i = this.conditionalMenuDetailService.deleteById(id);
        if (i > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("删除失败");
    }
}
