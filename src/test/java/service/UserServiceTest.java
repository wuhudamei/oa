package service;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import cn.damei.entity.account.User;
import cn.damei.service.account.UserService;
import java.util.List;
public class UserServiceTest extends SpringTestCase {
    @Autowired
    private UserService userService;
    @Test
    public void testGetAllInfoByUsername() {
        User user = this.userService.getAllInfoByUsername("bj000002");
        System.out.println(user);
    }
    @Test
    public void testFindByRoleId() {
        List<User> users = userService.findByRoleId(9L);
        System.out.println(users.size());
    }
}
