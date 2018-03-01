package cn.damei.shiro.filter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.filter.DelegatingFilterProxy;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
public class MyDelegatingShiroFilterProxy extends DelegatingFilterProxy {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        HttpServletRequest req = (HttpServletRequest) request;
        String ctx = StringUtils.trimToEmpty(req.getContextPath());
        String path = req.getRequestURI().substring(ctx.length());
        if (StringUtils.isNotEmpty(path) && (path.startsWith("/static/"))
                || path.startsWith("/api/generatejs") || path.contains("/favicon.ico")) {
            filterChain.doFilter(request, response);
        } else {
            super.doFilter(request, response, filterChain);
//			WebUtils.threadSession.remove();
        }
    }
}
