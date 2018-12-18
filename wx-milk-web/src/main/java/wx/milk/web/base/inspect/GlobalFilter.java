/*
package wx.milk.web.base.inspect;

import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@EnableWebMvc
@WebFilter(filterName = "druidWebStatFilter", urlPatterns = "/*"
// , initParams = { @WebInitParam(name = "exclusions", value =
// "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*")} // 忽略资源
)
@Order(Integer.MAX_VALUE)
public class GlobalFilter implements Filter {
	static ThreadLocal<ServletRequest> _request = new ThreadLocal<>();
	static ThreadLocal<ServletResponse> _response = new ThreadLocal<>();

	public static ServletResponse getResponse() {
		return _response.get();
	}

	public static void setResponse(ServletResponse response) {
		_response.set(response);
	}

	public static void clear() {
		_response.remove();
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		StatHttpServletRequestWrapper req = new StatHttpServletRequestWrapper(httpRequest, httpResponse);

		// httpResponse.setHeader("Access-Control-Allow-Origin", "*");
		// httpResponse.setHeader("Access-Control-Allow-Methods", "*");
		// httpResponse.setHeader("Access-Control-Max-Age", "3600");
		// httpResponse.setHeader("Access-Control-Allow-Headers", "*");
		// httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
		_response.set(response);
		chain.doFilter(req, response);
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public final static class StatHttpServletRequestWrapper extends HttpServletRequestWrapper {
		HttpServletResponse reponse;

		public HttpServletResponse getReponse() {
			return reponse;
		}

		public StatHttpServletRequestWrapper(HttpServletRequest request, HttpServletResponse reponse) {
			super(request);
			this.reponse = reponse;
		}

	}
}*/
