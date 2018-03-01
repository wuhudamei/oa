package cn.damei.rest.vacation;
import com.google.common.collect.Maps;
import com.rocoinfo.weixin.util.StringUtils;
import cn.damei.Constants;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.oa.ApplyBusinessAway;
import cn.damei.entity.vacation.Vacation;
import cn.damei.enumeration.ApplyStatus;
import cn.damei.enumeration.WfApplyTypeEnum;
import cn.damei.enumeration.WfNatureEnum;
import cn.damei.service.process.ProcessService;
import cn.damei.service.vacation.VacationService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
@RestController
@RequestMapping(value = "/api/vacations")
public class VacationRestController extends BaseController {
    @Autowired
    private VacationService vacationService;
    @Autowired
    private ProcessService processService;
    @RequestMapping(method = RequestMethod.GET)
    @Deprecated
    public Object list(@RequestParam(required = false) Date startTime,
                       @RequestParam(required = false) Date endTime,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "startTime", startTime);
        MapUtils.putNotNull(params, "endTime", endTime);
        MapUtils.putOrElse(params, "empId", WebUtils.getLoggedUserId(), 0l);
//        PageTable<Vacation> pageTable = this.vacationService.searchScrollPage(params, new Pagination(offset, limit));
//        PageTable<Vacation> pageTable = this.vacationService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(new PageTable<Vacation>(new ArrayList<Vacation>(), 0));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object create(@RequestBody Vacation vacation) {
    	ShiroUser shiroUser = WebUtils.getLoggedUser();
    	int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.LEAVE.name(),WfNatureEnum.APPROVAL.name(), shiroUser.getOrgId());
    	if(rowNum > 0){
			if (vacation.getId() != null && vacation.getId() > 0L) {
				if (this.vacationService.update(vacation, true) > 0) {
					return StatusDto.buildSuccess();
				} else {
					return StatusDto.buildFailure("提交失败!");
				}
			} else {
				if (this.vacationService.insert(vacation, true) > 0) {
					return StatusDto.buildSuccess();
				} else {
					return StatusDto.buildFailure("创建申请失败");
				}
			}
    	}else{
    		return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
    	}
    }
    @RequestMapping(value = "saveDraft",method = RequestMethod.POST)
    public Object saveDraft(@RequestBody Vacation vacation) {
        if( vacation.getId() != null && vacation.getId() > 0 ){
            if( this.vacationService.update(vacation,false) > 0 ){
                return StatusDto.buildSuccess();
            }else{
                return StatusDto.buildFailure("保存草稿失败");
            }
        }else{
            if( this.vacationService.insert(vacation,false) > 0 ){
                return StatusDto.buildSuccess();
            }else{
                return StatusDto.buildFailure("保存草稿失败");
            }
        }
    }
    @RequestMapping(value = "submit",method = RequestMethod.POST)
    public Object submit(Long id) {
    	ShiroUser shiroUser = WebUtils.getLoggedUser();
    	int rowNum = processService.findWfByTypeAndOrg(WfApplyTypeEnum.LEAVE.name(),WfNatureEnum.APPROVAL.name(), shiroUser.getOrgId());
		if (rowNum > 0) {
			if (this.vacationService.submit(id) > 0) {
				return StatusDto.buildSuccess();
			} else {
				return StatusDto.buildFailure("提交失败");
			}
    	}else{
    		return StatusDto.buildFailure(Constants.NO_WF_ERROR_MESSAGE);
    	}
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为null");
        return StatusDto.buildSuccess(this.vacationService.getById(id));
    }
    @RequestMapping(method = RequestMethod.PUT)
    public Object update(@RequestBody Vacation vacation) {
        if (ApplyStatus.REFUSE.equals(vacation.getStatus()))
            return StatusDto.buildFailure("只有被拒绝的申请才能编辑");
        int i = this.vacationService.update(vacation);
        return i > 0 ? StatusDto.buildSuccess() : StatusDto.buildFailure();
    }
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public Object delete(@RequestParam(value="id") Long id) {
        int i = this.vacationService.deleteById(id);
        return i > 0 ? StatusDto.buildSuccess() : StatusDto.buildFailure();
    }
}
