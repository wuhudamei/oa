package cn.damei.rest.account;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import cn.damei.common.BaseController;
import cn.damei.dto.StatusDto;
import cn.damei.dto.dict.MdnidictionaryDto;
import cn.damei.dto.page.PageTable;
import cn.damei.dto.page.Pagination;
import cn.damei.entity.account.Permission;
import cn.damei.entity.account.Role;
import cn.damei.entity.dict.MdniDictionary;
import cn.damei.service.account.RoleService;
import cn.damei.service.dict.MdniDictionaryService;
import cn.damei.utils.MapUtils;
import java.util.*;
@RestController
@RequestMapping("/api/roles")
public class RoleRestController extends BaseController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private MdniDictionaryService mdniDictionaryService;
    @RequestMapping(method = RequestMethod.GET)
    public Object search(@RequestParam(required = false) String keyword,
                         @RequestParam(defaultValue = "0") int offset,
                         @RequestParam(defaultValue = "20") int limit) {
        Map<String, Object> params = new HashMap<>();
        MapUtils.putNotNull(params, "keyword", keyword);
        PageTable<Role> pageTable = this.roleService.searchScrollPage(params, new Pagination(offset, limit));
        return StatusDto.buildSuccess(pageTable);
    }
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Object get(@PathVariable("id") Long id) {
        if (id == null) {
            return StatusDto.buildFailure("id不能为空！");
        }
        return StatusDto.buildSuccess(this.roleService.getById(id));
    }
    @RequestMapping(method = RequestMethod.POST)
    public Object saveRole(Role role) {
        if (StringUtils.isBlank(role.getName())) {
            return StatusDto.buildFailure("角色名称不能为空！");
        }
        try {
            return roleService.saveOrUpdate(role);
        } catch (Exception e) {
            e.printStackTrace();
            return StatusDto.buildFailure("保存失败！");
        }
    }
    @RequestMapping(value = "/{id}/del", method = RequestMethod.GET)
    public Object delete(@PathVariable("id") Long id) {
        try {
            return this.roleService.deleteRole(id);
        } catch (Exception e) {
            return StatusDto.buildFailure("删除失败！");
        }
    }
    @RequestMapping(value = "/findRolePermission/{id}", method = RequestMethod.GET)
    public Object findRolePermission(@PathVariable("id") Long id) {
        Map<String, List<Permission>> modulePermListMap = new LinkedHashMap<>();
        List<Permission> allPermission = this.roleService.findRolePermission(id);
        for (Permission perm : allPermission) {
            String module = perm.getModule();
            List<Permission> permList = modulePermListMap.get(module);
            if (permList == null) {
                permList = new ArrayList<>();
                modulePermListMap.put(module, permList);
            }
            permList.add(perm);
        }
        return StatusDto.buildSuccess("success", modulePermListMap);
    }
    @RequestMapping(value = "/rolepermission", method = RequestMethod.POST)
    public Object setUserRole(@RequestParam("roleId") Long roleId, @RequestParam("permissions[]") List<Long> permissions) {
        if (roleId == null)
            return StatusDto.buildFailure("角色id为空！");
        if (permissions == null || permissions.size() == 0)
            return StatusDto.buildFailure("角色为空！");
        return roleService.insertRolePermission(roleId, permissions);
    }
    @RequestMapping(value = "/subjectTree")
    public Object findTree(@RequestParam("roleId") Long roleId) {
        List<MdniDictionary> mdniDictionaries = mdniDictionaryService.findTree();
        List<MdnidictionaryDto> list = new ArrayList<MdnidictionaryDto>();
        List<Long> subs = roleService.findSubjectIdsByRoleId(roleId);
        MdnidictionaryDto m = null;
        for (MdniDictionary md : mdniDictionaries) {
            m = new MdnidictionaryDto(md.getId(), md.getParentCode(), md.getName(), true, checked(subs, md.getId()));
            list.add(m);
        }
        return StatusDto.buildSuccess(list);
    }
    @RequestMapping(value = "/setSubject")
    public Object setSubjectPermission(@RequestParam("list[]") List<Long> subs,
                                       @RequestParam("roleId") Long roleId) {
        roleService.setSubjects(subs, roleId);
        return StatusDto.buildSuccess();
    }
    private boolean checked(List<Long> list, long id) {
        for (Long _id : list) {
            if (_id == id) return true;
        }
        return false;
    }
}
