package cn.damei.shiro;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.cache.Cache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.damei.shiro.cache.ShiroCacheManager;
import cn.damei.utils.WebUtils;
import java.util.Optional;
public class ShiroCacheHelper {
    private ShiroCacheManager cacheManager;
    private static Logger log = LoggerFactory.getLogger(ShiroCacheHelper.class);
    public void clearAuthorizationInfo(String username) {
        if (log.isDebugEnabled()) {
            log.debug("clear the " + username + " authorizationInfo");
        }
        Cache<Object, Object> dbCache = cacheManager.getCache(ShiroDbAdminRealm.REAL_NAME);
        dbCache.remove(username);
        Cache<Object, Object> ssoCache = cacheManager.getCache(ShiroSSORealm.REAM_NAME);
        ssoCache.remove(username);
    }
    public void clearAuthorizationInfo() {
        if (SecurityUtils.getSubject().isAuthenticated()) {
            String username = Optional.ofNullable(WebUtils.getLoggedUser()).map(ShiroUser::getUsername).orElse(null);
            if (username != null) {
                clearAuthorizationInfo(username);
            }
        }
    }
    public ShiroCacheManager getCacheManager() {
        return cacheManager;
    }
    public void setCacheManager(ShiroCacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }
}