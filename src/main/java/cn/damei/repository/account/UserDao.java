package cn.damei.repository.account;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import cn.damei.common.persistence.CrudDao;
import cn.damei.entity.account.User;
@Repository
public interface UserDao extends CrudDao<User> {
    User getByUsername(@Param("username") String username);
    User getAllInfoById(@Param("id") Long id);
    User getAllInfoByUsername(@Param("username") String username);
    User getByUsernameOrMobile(@Param("username") String username);
}
