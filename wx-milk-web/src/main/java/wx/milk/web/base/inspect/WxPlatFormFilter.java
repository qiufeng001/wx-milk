package wx.milk.web.base.inspect;

import org.aspectj.lang.JoinPoint;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;
import wx.milk.web.configuration.WxConfig;
import wx.milk.web.utils.CookieUtils;
import wx.milk.web.utils.DomainUtils;
import wx.milk.web.utils.RedisUtils;
import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;


/**
 * auther: kiven on 2018/9/7/007 16:11
 * try it bast!
 */
@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*", filterName = "wxPlatFormFilter")
public class WxPlatFormFilter implements Filter {
    private WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HashMap<String, String> contextMap = new HashMap<>();

        HttpServletRequest request = (HttpServletRequest) req;
        request.setAttribute(WxConfig.COOKIE_DOMAIN, getCookieDomain(request.getServerName()));

        // 如果是登录的话，获取登录的时间，设置登录时间，超过时间则退出登录，直接跳转到登录页面
        String requestURI = request.getRequestURI();

        if (requestURI.contains("/signout")) {
            ExecutionContext.setContextMap(null);
        }else{
            long startTime = System.currentTimeMillis();
            String currentThreadId = Thread.currentThread().getId() + "_" + CookieUtils.getLoginToken(request);
            contextMap.put(WxConfig.LOGIN_START_TIME, startTime + "");
            contextMap.put(WxConfig.WX_SESSION_ID, "wx_session_id_" + CookieUtils.getValue(request));
            contextMap.put(WxConfig.USER_IP, request.getRemoteAddr());
            contextMap.put(WxConfig.CONTEXT_PATH, request.getContextPath());
            contextMap.put(WxConfig.CURRENT_THEAD_ID, currentThreadId);
            contextMap.put(currentThreadId, "");
            ExecutionContext.setContextMap(contextMap);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

    private String getCookieDomain(String serverName) {
        String baseDomain = DomainUtils.getBaseDomain(serverName);
        return "." + baseDomain;
    }
}
