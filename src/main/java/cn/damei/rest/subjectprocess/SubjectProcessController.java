package cn.damei.rest.subjectprocess;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.subjectprocess.SubjectProcess;
import cn.damei.service.subjectprocess.SubjectProcessService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.Date;
import java.util.Map;
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/subpro")
public class SubjectProcessController extends BaseController {
    @Autowired
    private SubjectProcessService subjectProcessService;
    @RequestMapping(method = RequestMethod.GET)
    public Object list(@RequestParam(required = false) String keyword,
                       @RequestParam(defaultValue = "0") int offset,
                       @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "keyword", keyword);
        PageTable page = this.subjectProcessService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(page);
    }
    @RequestMapping(value = "/saveorudpate", method = RequestMethod.POST)
    public Object saveOrUpdate(@RequestBody SubjectProcess subjectProcess) {
        if (subjectProcess.getWfId() == null || subjectProcess.getProcessTypeId() == null || subjectProcess.getSubjectId() == null) {
            return StatusDto.buildFailure("参数不齐");
        }
        if (subjectProcessService.checkExistForSubAndPro(subjectProcess.getWfType(), subjectProcess.getProcessTypeId(), subjectProcess.getSubjectId())) {
            return StatusDto.buildFailure("该流程类型和费用科目已存在了！");
        }
        if (subjectProcess.getId() == null) {
            subjectProcess.setCreateUser(WebUtils.getLoggedUserId());
            subjectProcess.setCreateTime(new Date());
            subjectProcessService.insert(subjectProcess);
        }
        else {
            subjectProcessService.update(subjectProcess);
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public Object delete(@PathVariable Long id) {
        subjectProcessService.deleteById(id);
        return StatusDto.buildSuccess();
    }
}
