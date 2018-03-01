package cn.damei.repository.account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.account.User;
import cn.damei.entity.account.UserRole;
import java.util.List;
@Repository
public interface UserRoleDao extends CrudDao<UserRole> {
    int insert(UserRole userRole);
    List<Long> getRoleIdsByUserId(Long userId);
    int deleteByUserId(Long userId);
    int deleteByRoleId(Long roleId);
    List<User> findUsersByRoleId(@Param(value = "roleId") Long roleId);
}
