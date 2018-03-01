package cn.damei.rest.sign;
import com.google.common.collect.Maps;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.sign.SignSite;
import cn.damei.service.sign.SignSiteService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/signsite")
public class SignSiteController {
    @Autowired
    SignSiteService signSiteService;
    @RequestMapping(value = "/list")
    public Object list(@RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        PageTable pagetable = signSiteService.searchScrollPage(params,new Pagination(offset,limit));
        return StatusDto.buildSuccess(pagetable);
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object createOrUpdate(@RequestBody SignSite signsite){
        Integer signSiterecord = signSiteService.createOrUpdate(signsite);
        if (signSiterecord < 1) {
            return  StatusDto.buildFailure();
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/delete/{id}")
    public Object deleteSignByid(@PathVariable Long id){
        Integer signSiterecord = signSiteService.deleteById(id);
        if (signSiterecord < 1) {
            return StatusDto.buildFailure("删除失败！");
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/getByid/{id}",method = RequestMethod.GET)
    public Object getByid(@PathVariable Long id){
        SignSite signSite = signSiteService.getById(id);
        if (signSite == null) {
            return StatusDto.buildFailure("未查询到此职场");
        }
        return StatusDto.buildSuccess(signSite);
    }
    @RequestMapping(value = "/signscope")
    public Object signScope(){
        List<SignSite> signSiteList = signSiteService.findSignScope();
        if (signSiteList != null && signSiteList.size() > 0) {
            return StatusDto.buildSuccess(signSiteList);
        }
        return StatusDto.buildFailure();
    }
}
