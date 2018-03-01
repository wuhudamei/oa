package cn.damei.rest.oa;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.oa.InsideMessageTarget;
import cn.damei.service.oa.IndexPageService;
import cn.damei.service.oa.InsideMessageService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/index")
public class IndexController extends BaseController {
    @Autowired
    IndexPageService indexPageService;
    @Autowired
    InsideMessageService insideMessageService;
    @RequestMapping(value = "/mineReadingMes", method = RequestMethod.GET)
    public Object mineReadingMes() {
        return  null;
    }
    @RequestMapping(value = "/messageList", method = RequestMethod.GET)
    public Object messageList(  @RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String beginTime,
                                @RequestParam(required = false) String endTime,
                                @RequestParam(required = false) String status,
                                @RequestParam(required = false) String messageLevel,
                                @RequestParam(defaultValue = "create_time") String orderColumn,
                                @RequestParam(defaultValue = "DESC") String orderSort,
                                @RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "10") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "beginTime", beginTime);
        MapUtils.putNotNull(params, "endTime", endTime);
        MapUtils.putNotNull(params, "status", status);
        MapUtils.putNotNull(params, "messageLevel", messageLevel);
        ShiroUser loginUser = WebUtils.getLoggedUser();
        MapUtils.putNotNull(params, "userId", loginUser.getId());
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
        PageTable<Map<String,String>> pageTable = indexPageService.mesSearchPageForIndexPage(params,new Pagination(offset,limit,sort));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/getMineReadingMsg", method = RequestMethod.GET)
    public Object getMineReadingMsg(){
        ShiroUser loginUser = WebUtils.getLoggedUser();
        return StatusDto.buildSuccess( indexPageService.indexPageMsg( loginUser.getId() ) );
    }
    @RequestMapping(value = "/changeMsgStatus", method = RequestMethod.PUT)
    public Object changeMsgStatus(@RequestParam(required = true) Integer messageId){
        ShiroUser loginUser = WebUtils.getLoggedUser();
        InsideMessageTarget entity = new InsideMessageTarget();
        entity.setId(messageId.longValue());
        entity.setMessageId( messageId );
        entity.setUserId(  Integer.parseInt( loginUser.getId().toString() ) );
        entity.setStatus(1);
        indexPageService.changeMsgStatus(entity);
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object getMsgDetail(@PathVariable Long id){
        ShiroUser loginUser = WebUtils.getLoggedUser();
        InsideMessageTarget entity = new InsideMessageTarget();
        entity.setMessageId( Integer.parseInt( id.toString() ) );
        entity.setUserId(  Integer.parseInt( loginUser.getId().toString() ) );
        entity.setStatus(1);
        indexPageService.changeMsgStatus(entity);
        return StatusDto.buildSuccess( insideMessageService.getById( id ) );
    }
}
