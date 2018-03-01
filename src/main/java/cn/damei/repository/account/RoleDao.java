package cn.damei.repository.account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.account.Role;
import java.util.List;
import java.util.Map;
@Repository
public interface RoleDao extends CrudDao<Role> {
    Role checkRoleExistByName(@Param("id") Long id, @Param("name") String name);
    List<Role> getRolesByUserId(Long id);
    void deleteUserRolesByUserId(Long id);
    void deleteUserRolesByRoleId(Long id);
    void insertUserRoles(@Param("userId") Long userId, @Param("roleId") Long roleid);
    List<Role> search(Map<String, Object> parameters);
}
