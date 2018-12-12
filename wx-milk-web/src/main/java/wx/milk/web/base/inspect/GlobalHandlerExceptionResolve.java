package wx.milk.web.base.inspect;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
import wx.milk.web.ErrorInfo;
import wx.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;


public class GlobalHandlerExceptionResolve extends SimpleMappingExceptionResolver {

	private static Log logger = LogFactory.getLog(GlobalHandlerExceptionResolve.class);

	@Override
	protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {

		String viewName = determineViewName(ex, request);
		HandlerMethod method = (HandlerMethod) handler;
		logger.error(ex.getMessage(), ex);

		ResponseBody bd = method.getMethod().getAnnotation(ResponseBody.class);

		if (bd == null) {

			Integer statusCode = determineStatusCode(request, viewName);
			if (statusCode != null) {
				applyStatusCodeIfPossible(request, response, statusCode);
			}
			return getModelAndView("error/500", ex, request);
		} else {// JSON格式返回
			PrintWriter writer = null;
			try {
				response.setHeader("Content-type", "text/html;charset=UTF-8");
				ErrorInfo<String> r = new ErrorInfo<String>();
				r.setMessage(ex.getMessage());
				r.setCode(ErrorInfo.ERROR);
				r.setData(getErrorInfoFromException(ex));
				r.setUrl(request.getRequestURL().toString());
				String content = JsonUtils.toJson(r);
				response.setCharacterEncoding("UTF-8");
				response.setStatus(500);
				writer = response.getWriter();
				
				writer.write(content);
				writer.flush();
				writer.close();
				// response.reset();
			} catch (IOException e) {
				logger.error(e.getMessage(), e);
			} finally {

			}
			return null;
		}
	}

	private static String getErrorInfoFromException(Exception e) {
		try {
			Throwable ex = e;
			while (ex.getCause() != null && !e.equals(e)) {
				ex = e.getCause();
			}

			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			return "\r\n" + sw.toString() + "\r\n";
		} catch (Exception e2) {
			return "bad getErrorInfoFromException";
		}
	}
}
