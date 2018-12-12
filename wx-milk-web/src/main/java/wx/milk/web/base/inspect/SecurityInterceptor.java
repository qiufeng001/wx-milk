package wx.milk.web.base.inspect;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import wx.milk.manager.admin.IDictionsManager;
import wx.milk.web.utils.RedisUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Enumeration;

public class SecurityInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		String url = request.getRequestURI();
		GlobalFilter.setResponse(response);
		request.setAttribute("rootpath", request.getContextPath().replaceAll("//", "/"));

		if (url.indexOf("sys/") >= 0 || url.indexOf(".ig") > 0 || url.indexOf("/druid/") > 0) {
			return true;
		}

		String userJson = RedisUtils.getUserJsonByToken(request);

		String xrequest = request.getHeader("x-requested-with");
		if (userJson == null || userJson.equalsIgnoreCase("")) {
			if ("XMLHttpRequest".equalsIgnoreCase(xrequest)) {// 拦截AJAX请求
				response.setHeader("sessionstatus", "timeout");
			} else {
				response.sendRedirect("/login");
			}
			return false;
		} else {
			//moduleVerify(url, handler, request);
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
						   ModelAndView modelAndView) throws Exception {
		Enumeration<String> names = request.getAttributeNames();
		ArrayList<String> vals = new ArrayList<>();
		while(names.hasMoreElements()){
			vals.add(names.nextElement());
		}
		for (String string : vals) {
			if(string.startsWith("@"))
				request.removeAttribute(string);
		}
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		GlobalFilter.clear();
	}
}
