package cn.damei.repository.account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.account.Permission;
import java.util.List;
@Repository
public interface RolePermissionDao extends CrudDao {
    void insert(@Param("roleId") Long roleId, @Param("permissionId") Long permissionId);
    void deleteByRoleId(@Param("roleId") Long roleId);
    List<Permission> findRolePermission(@Param("roleId") Long roleId);
    List<Long> findSubjectIdsByRoleId(@Param("permIdList") List<Long> roleIds);
}
