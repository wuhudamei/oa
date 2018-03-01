package cn.damei.shiro.listener;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.SessionListener;
import cn.damei.shiro.session.ShiroSessionRepository;
public class CustomSessionListener implements SessionListener {
    private ShiroSessionRepository shiroSessionRepository;
    @Override
    public void onStart(Session session) {
    }
    @Override
    public void onStop(Session session) {
    }
    @Override
    public void onExpiration(Session session) {
        shiroSessionRepository.deleteSession(session.getId());
    }
    public ShiroSessionRepository getShiroSessionRepository() {
        return shiroSessionRepository;
    }
    public void setShiroSessionRepository(ShiroSessionRepository shiroSessionRepository) {
        this.shiroSessionRepository = shiroSessionRepository;
    }
}
