package wx.milk.web.base.inspect;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import wx.query.Page;


public class PageArgumentResolver implements HandlerMethodArgumentResolver {
	static String[] properties = new String[] { "page", "rows", "pageNumber",
			"pageSize", "pageIndex" };

	static {

	}

	public PageArgumentResolver(){
		
	}
	
	public static boolean hasProperty(String p) {
		for (String s : properties) {
			if (s.equalsIgnoreCase(p))
				return true;
		}
		return false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
		/*
		 * page:2 rows:20 pageNumber:2 pageSize:20 pageIndex:1
		 */
		Page page = new Page();
		int pageInex = getValue(webRequest,"page",1);
		int pageSize = getValue(webRequest,"rows",10);
		page.setPageIndex(pageInex);
		page.setPageSize(pageSize);
		page.setTotal(getValue(webRequest,"total",-1));
		 
		return page;
	}

	private int getValue(NativeWebRequest webRequest, String name,int defaultvalue) {
		String val = webRequest.getParameter(name);
		if (val != null) {
			return Integer.parseInt(val);
		}
		return defaultvalue;
	}

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		if (parameter.getParameterType().equals(Page.class)) {
			return true;
		}
		return false;
	}
}
