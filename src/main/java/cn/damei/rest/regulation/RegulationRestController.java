package cn.damei.rest.regulation;
import com.baidu.ueditor.ActionEnter;
import com.google.common.collect.Maps;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.dto.page.Sort;
import cn.damei.entity.regulation.Regulation;
import cn.damei.enumeration.Status;
import cn.damei.service.noticeboard.NoticeBoardService;
import cn.damei.service.regulation.RegulationService;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@SuppressWarnings("all")
@RestController
@RequestMapping(value = "/api/regulations")
public class RegulationRestController extends BaseController {
    @Autowired
    private RegulationService regulationService;
    @Autowired
    NoticeBoardService noticeBoardService;
    @RequestMapping(value = "/config")
    public Object config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        try {
            request.setCharacterEncoding("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        response.setHeader("Content-Type","text/html");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        String exec = new ActionEnter(request, rootPath).exec();
        return exec;
    }
    @RequestMapping(method = RequestMethod.GET)
    public StatusDto search(@RequestParam(value = "keyword", required = false) String keyword,
                            @RequestParam(value = "type", required = false) Long type,
                            @RequestParam(value = "offset", defaultValue = "0") int offset,
                            @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUser().getId();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "userId", userId);
        MapUtils.putNotNull(params, "keyword", keyword);
        MapUtils.putNotNull(params, "type", type);
        PageTable<Regulation> pageTable = regulationService.searchScrollPage(params, new Pagination(offset, limit, new Sort(new Sort.Order(Sort.Direction.DESC, "id"))));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(method = RequestMethod.POST)
    public StatusDto createOrUpdate(@RequestBody Regulation regulation) {
        String checkResult = checkRequiredParams(regulation);
        if (checkResult != null) {
            return StatusDto.buildFailure(checkResult);
        }
        Long userId = WebUtils.getLoggedUserId();
        List<Long> orgIdList = new ArrayList<>();
        String orgId = regulation.getOrgId();
        String[] split = orgId.split(",");
        StringBuffer familyCode = new StringBuffer();
        for (String orgid : split) {
            orgIdList.add(Long.parseLong(orgid));
        }
        List<String> list = noticeBoardService.findFamilyCodeByOrgId(orgIdList);
        if (!list.isEmpty() && list.size() > 0) {
            list.forEach(str -> {
                familyCode.append(str).append(",");
            });
        }
        if (familyCode.length() > 1) {//删除最后一个逗号
            familyCode.delete(familyCode.length() - 1, familyCode.length());
        }
        regulation.setOrgFamilyCode(familyCode.toString());
        if (regulation.getId() == null) {
            regulation.setCreateUser(userId);
            regulation.setCreateTime(new Date());
            regulation.setStatus(Status.OPEN);
        }
        regulationService.createOrUpdate(regulation);
        return StatusDto.buildSuccess();
    }
    private String checkRequiredParams(Regulation regulation) {
        return null;
    }
    @RequestMapping(value = "/{id}/get")
    public StatusDto getById(@PathVariable(value = "id") Long id) {
        return StatusDto.buildSuccess(regulationService.getById(id));
    }
    @RequestMapping(value = "/detail/{id}")
    public StatusDto detail(@PathVariable(value = "id") Long id) {
        return StatusDto.buildSuccess(regulationService.getById(id));
    }
    @RequestMapping(value = "/{id}/del")
    public StatusDto delete(@PathVariable(value = "id") Long id) {
        regulationService.deleteById(id);
        return StatusDto.buildSuccess();
    }
}