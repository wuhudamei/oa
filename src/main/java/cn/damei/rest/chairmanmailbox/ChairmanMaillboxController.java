package cn.damei.rest.chairmanmailbox;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.chairmanMailbox.ChairmanMailbox;
import cn.damei.service.chairmanMailBox.ChairmanMailboxService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping("/api/chairmanMaibox")
public class ChairmanMaillboxController extends BaseController {
    @Autowired
    private ChairmanMailboxService chairmanMailBoxService;
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "readStatus", required = false) Boolean readStatus,
                       @RequestParam(value = "importantDegree", required = false) Integer importantDegree,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "10") int limit) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if(loggedUser == null){
            return StatusDto.buildSuccess();
        }
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "title", title);
        MapUtils.putNotNull(params, "readStatus", readStatus);
        MapUtils.putNotNull(params, "importantDegree", importantDegree);
        PageTable pageTable = chairmanMailBoxService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/add")
    public Object add(ChairmanMailbox chairmanMailbox) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if(loggedUser == null){
            return StatusDto.buildFailure("回话失效,请重新登录!");
        }
        boolean result = chairmanMailBoxService.insertChairmanMailbox(chairmanMailbox);
        if (result) {
            return StatusDto.buildSuccess("操作成功!");
        }
        return StatusDto.buildFailure("操作失败!");
    }
    @RequestMapping(value = "/delete/{id}")
    public Object delete(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为空！");
        Integer integer = chairmanMailBoxService.deleteById(id);
        if (integer < 1)
            return StatusDto.buildFailure("删除失败！");
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/findMailById/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("信件id不能为空!");
        ChairmanMailbox chairmanMailbox = chairmanMailBoxService.getById(id);
        if (chairmanMailbox == null)
            return StatusDto.buildFailure("未查询到该信件详情!");
        return StatusDto.buildSuccess(chairmanMailbox);
    }
    @RequestMapping(value = "/update")
    public Object update(ChairmanMailbox chairmanMailbox) {
        if (chairmanMailbox.getId() == null){
            return StatusDto.buildFailure("信件id不能为空!");
        }
        try{
            int count = chairmanMailBoxService.update(chairmanMailbox);
            if(count == 0){
                return StatusDto.buildFailure("操作失败!");
            }
            return StatusDto.buildSuccess("操作成功!");
        }catch (Exception e){
            e.printStackTrace();
            return StatusDto.buildFailure("操作失败!");
        }
    }
}
