package cn.damei.service.account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.Permission;
import cn.damei.entity.account.Role;
import cn.damei.entity.account.User;
import cn.damei.repository.account.RoleDao;
import cn.damei.repository.account.RolePermissionDao;
import cn.damei.repository.account.UserDao;
import cn.damei.utils.WebUtils;
import java.util.ArrayList;
import java.util.List;
@SuppressWarnings("all")
@Service
public class RoleService extends CrudService<RoleDao, Role> {
    @Autowired
    private UserDao adminUserDao;
    @Autowired
    private RolePermissionDao rolePermissionDao;
    @Autowired
    private PermissionHandler permissionHandler;
    public List<Role> getUserRoles(final Long userId) {
        return entityDao.getRolesByUserId(userId);
    }
    @Transactional(rollbackFor = Exception.class)
    public StatusDto insertUserRoles(final Long userId, final List<Long> roles) {
        User user = adminUserDao.getById(userId);
        if (user == null)
            return StatusDto.buildFailure("用户信息有误！");
        entityDao.deleteUserRolesByUserId(userId);
        for (Long roleid : roles) {
            entityDao.insertUserRoles(userId, roleid);
        }
        permissionHandler.clearCacheByUserId(userId);
        return StatusDto.buildSuccess("角色设置成功！");
    }
    @Transactional(rollbackFor = Exception.class)
    public StatusDto insertRolePermission(final Long roleId, final List<Long> permissions) {
        Role role = entityDao.getById(roleId);
        if (role == null)
            return StatusDto.buildFailure("无此角色！");
        rolePermissionDao.deleteByRoleId(roleId);
        for (Long ids : permissions) {
            rolePermissionDao.insert(roleId, ids);
        }
        permissionHandler.clearCacheByRoleId(roleId);
        return StatusDto.buildSuccess("权限设置成功！");
    }
    @Transactional(rollbackFor = Exception.class)
    public StatusDto saveOrUpdate(Role role) {
        Role tempRole = entityDao.checkRoleExistByName(role.getId(), role.getName());
        if (tempRole != null) {
            return StatusDto.buildFailure("角色名称已存在！");
        }
        if (role.getId() != null) {
            entityDao.update(role);
        } else {
            entityDao.insert(role);
        }
        return StatusDto.buildSuccess("保存角色成功！");
    }
    @Transactional(rollbackFor = Exception.class)
    public StatusDto deleteRole(Long id) {
        entityDao.deleteById(id);
        entityDao.deleteUserRolesByRoleId(id);
        rolePermissionDao.deleteByRoleId(id);
//        permissionHandler.clearCacheByRoleId(id); 
        return StatusDto.buildSuccess("删除角色成功！");
    }
    public List<Permission> findRolePermission(Long roleId) {
        return rolePermissionDao.findRolePermission(roleId);
    }
    @Transactional(rollbackFor = Exception.class)
    public void setSubjects(List<Long> pers, Long roleId) {
        rolePermissionDao.deleteByRoleId(roleId);
        for (Long ids : pers) {
            rolePermissionDao.insert(roleId, ids);
        }
    }
    public List<Long> findSubjectIdsByRoleId(Long roleId) {
        List<Long> subjectRoles = null;
        if (roleId == -1)
            subjectRoles = WebUtils.getLoggedUser().getSubjectPers();
        else {
            subjectRoles = new ArrayList<Long>();
            subjectRoles.add(roleId);
        }
        return rolePermissionDao.findSubjectIdsByRoleId(subjectRoles);
    }
}
