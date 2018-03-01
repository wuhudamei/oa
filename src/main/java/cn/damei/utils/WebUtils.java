package cn.damei.utils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import cn.damei.common.PropertyHolder;
import cn.damei.shiro.ShiroUser;
import javax.servlet.http.HttpServletRequest;
public class WebUtils {
//    public static final ThreadLocal threadSession = new ThreadLocal();
    private WebUtils() {
    }
    public static String getBaseSiteUrl(HttpServletRequest request) {
        final StringBuilder basePath = new StringBuilder();
        basePath.append(request.getScheme()).append("://").append(request.getServerName());
        if (request.getServerPort() != 80) {
            basePath.append(":").append(request.getServerPort());
        }
        basePath.append(request.getContextPath());
        return basePath.toString();
    }
    public static boolean isLogin(HttpServletRequest req) {
        if (getLoggedUser() != null)
            return true;
        return false;
    }
    public static ShiroUser getLoggedUser() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Object principal = subject.getPrincipal();
            return (ShiroUser) principal;
        } catch (UnavailableSecurityManagerException ex) {
            return null;
        }
    }
    public static Long getLoggedUserId() {
        ShiroUser user = getLoggedUser();
        return user == null ? null : user.getId();
    }
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }
    public static String getUploadFilePath(String path) {
        return PropertyHolder.getUploadBaseUrl() + path;
    }
    public static String getFullUploadFilePath(String imagePath) {
        return PropertyHolder.getBaseurl() + PropertyHolder.getUploadBaseUrl() + imagePath;
    }
}
