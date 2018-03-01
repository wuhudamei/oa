package cn.damei.shiro.filter;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.filter.authc.UserFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springside.modules.utils.Encodes;
import cn.damei.Constants;
import cn.damei.common.PropertyHolder;
import cn.damei.service.account.UserService;
import cn.damei.shiro.ShiroUser;
import cn.damei.utils.WebUtils;
@Component
public class MultipleViewUserFilter extends UserFilter {
	private static Logger logger = LoggerFactory.getLogger(MultipleViewUserFilter.class);// 日志
    @Autowired
    private UserService userService;
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
        ShiroUser loggedUser = WebUtils.getLoggedUser();
        if (loggedUser == null)
            return false;
        return true;
    }
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        String ctx = req.getSession().getServletContext().getContextPath();
        String path = req.getRequestURI().substring(ctx.length());
        if (path.startsWith("/") || path.startsWith("/api") || path.startsWith("/index") || path.startsWith("/admin")) {
//            StringBuilder loginUrl = new StringBuilder();
//            loginUrl.append(ctx);
//            loginUrl.append("/login");
//            loginUrl.append("?successUrl=").append(getRedirectUrlOnLoginSuccess(req));
//            resp.sendRedirect(loginUrl.toString());
        	if(!path.startsWith("/api/logout") && !path.startsWith("/api/wx/template/sendScheduleTemplateMessage")){
    			StringBuilder redirectUrl = new StringBuilder();
    			redirectUrl.append(PropertyHolder.getOauthCenterUrl()).append(Constants.OAUTH_CENTER_CODE_URL);
    			redirectUrl.append("?appid=").append(PropertyHolder.getOauthCenterAppid());
    			redirectUrl.append("&redirect_url=").append(PropertyHolder.getBaseurl() + Constants.OAUTH_CALL_BACK);
    			redirectUrl.append("&state=state");
    			resp.sendRedirect(redirectUrl.toString());
        	}
        } else {
            return super.onAccessDenied(request, response);
        }
        return false;
    }
    private String getRedirectUrlOnLoginSuccess(HttpServletRequest req) {
        StringBuilder requestUrl = new StringBuilder(req.getRequestURL().toString());
        final String queryString = req.getQueryString();
        if (StringUtils.isNotBlank(queryString)) {
            requestUrl.append("?").append(queryString);
        }
        return Encodes.urlEncode(requestUrl.toString());
    }
}