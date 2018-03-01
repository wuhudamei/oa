package cn.damei.rest.wechat.menu;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.wechat.menu.MenuTreeDto;
import cn.damei.entity.wechat.menu.Menu;
import cn.damei.service.wechat.menu.MenuService;
import java.util.List;
@RestController
@RequestMapping("/api/wx/menu")
public class MenuController extends BaseController {
    @Autowired
    private MenuService menuService;
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Object tree() {
        List<Menu> list = this.menuService.findAll();
        MenuTreeDto tree = MenuTreeDto.buildTree(list);
        return StatusDto.buildSuccess(tree.getChildren());
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Menu menu) {
        MenuTreeDto treeDto = this.menuService.create(menu);
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
        return StatusDto.buildSuccess(this.menuService.getById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object edit(@RequestBody Menu menu) {
        MenuTreeDto treeDto = this.menuService.edit(menu);
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
        int i = this.menuService.deleteById(id);
        if (i > 0) {
            return StatusDto.buildSuccess();
        }
        return StatusDto.buildFailure("删除失败");
    }
}
