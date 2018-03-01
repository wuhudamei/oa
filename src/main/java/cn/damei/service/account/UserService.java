package cn.damei.service.account;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AccountException;
import org.apache.shiro.authc.LockedAccountException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.damei.common.service.CrudService;
import cn.damei.dto.StatusDto;
import cn.damei.entity.account.User;
import cn.damei.entity.wechat.WechatUser;
import cn.damei.enumeration.Status;
import cn.damei.enumeration.employee.EmployeeStatus;
import cn.damei.repository.account.UserDao;
import cn.damei.repository.account.UserRoleDao;
import cn.damei.service.wechat.WechatUserService;
import cn.damei.shiro.PasswordUtil;
import java.util.Collections;
import java.util.List;
@Service
public class UserService extends CrudService<UserDao, User> {
    @Autowired
    private WechatUserService wechatUserService;
    @Autowired
    private UserRoleDao userRoleDao;
    public User getByUsername(String username) {
        if (StringUtils.isBlank(username))
            return null;
        return this.entityDao.getByUsername(username);
    }
    public User getByUsernameOrMobile(String username) {
        if (StringUtils.isBlank(username))
            return null;
        return this.entityDao.getByUsernameOrMobile(username);
    }
    public User getAllInfoById(Long id) {
        if (id != null) {
            return this.entityDao.getAllInfoById(id);
        }
        return null;
    }
    public User getAllInfoByUsername(String username) {
        if (StringUtils.isNotBlank(username)) {
            return this.entityDao.getAllInfoByUsername(username);
        }
        return null;
    }
    public StatusDto modifyPwd(Long id, String oldPwd, String newPwd) {
        User user = this.entityDao.getById(id);
        try {
            if (user.getPassword().equals(PasswordUtil.hashPassword(oldPwd, user.getSalt()))) {
                User u = new User(id);
                String salt = PasswordUtil.generateSalt();
                u.setSalt(salt);
                u.setPassword(PasswordUtil.entryptUserPassword(newPwd, salt));
                this.entityDao.update(u);
                return StatusDto.buildSuccess("修改密码成功！");
            } else {
                return StatusDto.buildFailure("原密码输入错误！");
            }
        } catch (Exception e) {
            return StatusDto.buildFailure("修改密码失败！");
        }
    }
    public StatusDto modifyPwd(Long id, String newPwd) {
        User user = this.entityDao.getById(id);
        if (user == null) {
            return StatusDto.buildFailure("查询不到此用户");
        }
        try {
            User u = new User(id);
            String salt = PasswordUtil.generateSalt();
            u.setSalt(salt);
            u.setPassword(PasswordUtil.entryptUserPassword(newPwd, salt));
            this.entityDao.update(u);
            return StatusDto.buildSuccess("修改密码成功！");
        } catch (Exception e) {
            return StatusDto.buildFailure("修改密码失败！");
        }
    }
    private static final String DEFAULT_PASSWORD = "123456";
    public Object resetPwd(Long id) {
        if (id == null)
            return StatusDto.buildFailure();
        User user = new User(id);
        String salt = PasswordUtil.generateSalt();
        user.setSalt(salt);
        user.setPassword(PasswordUtil.entryptUserPassword(DEFAULT_PASSWORD, salt));
        this.update(user);
        return StatusDto.buildSuccess("修改密码成功！");
    }
    public List<User> findByRoleId(Long roleId) {
        if (roleId == null) {
            return Collections.EMPTY_LIST;
        }
        return userRoleDao.findUsersByRoleId(roleId);
    }
    public void validateUser(User user) {
        if (user == null) {
            throw new AccountException("用户不存");
        }
        if (EmployeeStatus.DIMISSION.equals(user.getEmployeeStatus())) {
            throw new AccountException("用户已离职");
        }
        if (Status.LOCK.equals(user.getAccountStatus()) || Status.DELETE.equals(user.getAccountStatus())) {
            throw new LockedAccountException("账户被锁定：" + user.getUsername());
        }
    }
    public void buildUserWechatHeadImg(User user) {
        if (user == null || user.getId() == null)
            return;
        WechatUser wechatUser = this.wechatUserService.getByUserId(user.getId());
        if (wechatUser != null)
            user.setHeadImg(wechatUser.getHeadimgurl());
    }
    public List<Long> getRoleIdsByUserId(Long userId) {
        return this.userRoleDao.getRoleIdsByUserId(userId);
    }
}
