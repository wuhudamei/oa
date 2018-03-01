package cn.damei.service.account;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
@Service
public class LogoutService {
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject.getPrincipal() != null) {
            subject.logout();
        }
    }
}
