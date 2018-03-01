package cn.damei.rest.noticeboard;
import com.google.common.collect.Maps;
import cn.damei.dto.StatusDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.noticeboard.NoticeBoard;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.service.message.MessageManagerService;
import cn.damei.service.noticeboard.NoticeBoardService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.MapUtils;
import cn.damei.utils.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/noticeboard")
public class NoticeBoardController {
    @Autowired
    NoticeBoardService noticeBoardService;
    @Autowired
    MdniDictionaryService mdniDictionaryService;
    @Autowired
    MessageManagerService messageManagerService;
    @RequestMapping("/list")
    public Object list(@RequestParam(value = "title", required = false) String title,
                       @RequestParam(value = "status", required = false) String status,
                       @RequestParam(value = "offset", defaultValue = "0") int offset,
                       @RequestParam(value = "limit", defaultValue = "20") int limit) {
        Long userId = WebUtils.getLoggedUser().getId();
        Long orgId = WebUtils.getLoggedUser().getOrgId();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "userId", userId);
        MapUtils.putNotNull(params, "orgId", orgId);
        MapUtils.putNotNull(params, "title", title);
        MapUtils.putNotNull(params, "status", status);
        PageTable pagetable = noticeBoardService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pagetable);
    }
    @RequestMapping(value = "/add")
    public Object add(@RequestParam(value = "content") String content,
                      @RequestParam(value = "noticeStatus") String noticeStatus,
                      @RequestParam(value = "orgId") String orgId,
                      @RequestParam(value = "title") String title) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setContent(content);
        noticeBoard.setTitle(title);
        noticeBoard.setNoticeStatus(noticeStatus);
        List<Long> orgIdList = new ArrayList<>();
        if ("1".equals(orgId)) {
            messageManagerService.sendMessage(title, null);
            noticeBoard.setOrgId("9999");
            noticeBoard.setOrgFamilyCode("1");
        } else {
            noticeBoard.setOrgId(orgId);
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
            messageManagerService.sendMessage(title, orgIdList);
            if (familyCode.length() > 1) {//删除最后一个逗号
                familyCode.delete(familyCode.length() - 1, familyCode.length());
            }
            noticeBoard.setOrgFamilyCode(familyCode.toString());
        }
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        noticeBoard.setCreateNameId(loggedUser.getId());
        noticeBoard.setCreateName(loggedUser.getName());
        try {
            Date date = new Date();
            noticeBoard.setCreateTime(date);
        } catch (Exception e) {
            e.printStackTrace();
        }
        Integer integer = noticeBoardService.insert(noticeBoard);
        if (integer == 0) {
            return StatusDto.buildFailure("保存失败");
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/delete/{id}")
    public Object delete(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("id不能为空！");
        Integer integer = noticeBoardService.deleteById(id);
        if (integer < 1)
            return StatusDto.buildFailure("删除失败！");
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/findNoticeById/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable Long id) {
        if (id == null)
            return StatusDto.buildFailure("公告id不能为null");
        NoticeBoard noticeBoard = noticeBoardService.getById(id);
        if (noticeBoard == null)
            return StatusDto.buildFailure("未查询到此公告");
        return StatusDto.buildSuccess(noticeBoard);
    }
    @RequestMapping(value = "/editNotice/{id}")
    public Object editNotice(@PathVariable Long id,
                             @RequestParam(value = "content") String content,
                             @RequestParam(value = "noticeStatus") String noticeStatus,
                             @RequestParam(value = "orgId") String orgId,
                             @RequestParam(value = "title") String title) {
        NoticeBoard noticeBoard = new NoticeBoard();
        noticeBoard.setId(id);
        noticeBoard.setContent(content);
        noticeBoard.setNoticeStatus(noticeStatus);
        noticeBoard.setTitle(title);
        List<Long> orgIdList = new ArrayList<>();
        if ("1".equals(orgId)) {
            noticeBoard.setOrgId("9999");
            noticeBoard.setOrgFamilyCode("1");
        } else {
            noticeBoard.setOrgId(orgId);
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
            if (familyCode.length() > 1) {
                familyCode.delete(familyCode.length() - 1, familyCode.length());
            }
            noticeBoard.setOrgFamilyCode(familyCode.toString());
        }
        Integer integer = noticeBoardService.update(noticeBoard);
        if (integer == 0) {
            return StatusDto.buildFailure("更新失败");
        }
        return StatusDto.buildSuccess();
    }
    @RequestMapping(value = "/allList")
    public Object allList() {
        Long userId = WebUtils.getLoggedUser().getId();
        Long orgId = WebUtils.getLoggedUser().getOrgId();
        Map<String, Object> params = Maps.newHashMap();
        MapUtils.putNotNull(params, "orgId", orgId);
        MapUtils.putNotNull(params, "userId", userId);
        return StatusDto.buildSuccess(noticeBoardService.findAllNotice(params));
    }
}
