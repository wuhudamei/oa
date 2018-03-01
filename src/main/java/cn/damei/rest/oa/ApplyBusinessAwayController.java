package cn.damei.rest.oa;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.google.common.collect.Maps;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.oa.ApplyBusinessAway;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.service.oa.ApplyBusinessAwayService;
import cn.damei.service.process.ProcessService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
@RestController
@RequestMapping(value ="/api/apply/applyBusinessAway")
public class ApplyBusinessAwayController  extends BaseController {
    @Autowired
    private ApplyBusinessAwayService service;
    @Autowired
    private ProcessService processService;
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @Deprecated
    public Object listWithOrder(@RequestParam(required = false) String keyword,
                                @RequestParam(required = false) String status,
                                @RequestParam(defaultValue = "id") String orderColumn,
                                @RequestParam(defaultValue = "DESC") String orderSort,
                                @RequestParam(required = false, defaultValue = "0") int offset,
                                @RequestParam(required = false, defaultValue = "10") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "status", status);
        ShiroUser loginUser = WebUtils.getLoggedUser();
        MapUtils.putNotNull(params, "userId", loginUser.getId());
        Sort sort = new Sort(new Sort.Order(Sort.Direction.valueOf(orderSort), orderColumn));
        PageTable<ApplyBusinessAway> pageTable = this.service.searchScrollPage(params,new Pagination(offset,limit,sort));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "saveSubmit",method = RequestMethod.POST)
    public Object saveSubmit(ApplyBusinessAway businessAway) {
    	ShiroUser shiroUser = WebUtils.getLoggedUser();
    	int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.BUSINESS.name(),WfNatureEnum.APPROVAL.name(), shiroUser.getOrgId());
    	if(rowNum > 0){
            if( businessAway.getId() != null && businessAway.getId() > 0 ){
                if( this.service.update(businessAway,true) > 0 ){
                    return StatusDto.buildSuccess();
                }else{
                    return StatusDto.buildFailure("更新申请失败");
                }
            }else{
                if( this.service.insert(businessAway,true) > 0 ){
                    return StatusDto.buildSuccess();
                }else{
                    return StatusDto.buildFailure("创建申请失败");
                }
            }
    	}else{
    		return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
    	}
    }
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public Object submit(Long id) {
    	ShiroUser shiroUser = WebUtils.getLoggedUser();
    	int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.BUSINESS.name(),WfNatureEnum.APPROVAL.name(), shiroUser.getOrgId());
    	if(rowNum > 0){
            if( this.service.submit(id) > 0 ){
                return StatusDto.buildSuccess();
            }else{
                return StatusDto.buildFailure("提交失败");
            }
    	}else{
    		return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
    	}
    }
    @RequestMapping(value = "saveDraft",method = RequestMethod.POST)
    public Object saveDraft(ApplyBusinessAway businessAway) {
        if( businessAway.getId() != null && businessAway.getId() > 0 ){
            if( this.service.update(businessAway,false) > 0 ){
                return StatusDto.buildSuccess();
            }else{
                return StatusDto.buildFailure("保存草稿失败");
            }
        }else{
            if( this.service.insert(businessAway,false) > 0 ){
                return StatusDto.buildSuccess();
            }else{
                return StatusDto.buildFailure("保存草稿失败");
            }
        }
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        return StatusDto.buildSuccess(this.service.getById(id));
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="id") Long id) {
        int i = this.service.deleteById(id);
        return i > 0 ? StatusDto.buildSuccess() : StatusDto.buildFailure();
    }
}