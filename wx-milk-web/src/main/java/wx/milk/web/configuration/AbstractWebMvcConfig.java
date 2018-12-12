package wx.milk.web.configuration;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import wx.milk.web.base.inspect.LogInterceptor;
import wx.milk.web.base.inspect.PageArgumentResolver;
import wx.milk.web.base.inspect.SecurityInterceptor;

/**
 * Created by LF on 2017/5/4.
 */
@Configuration
@EnableWebMvc
public abstract class AbstractWebMvcConfig extends WebMvcConfigurerAdapter {
	
	@Autowired
	Environment environment;

	@Override
	public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
		argumentResolvers.add(new PageArgumentResolver());
		super.addArgumentResolvers(argumentResolvers);
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		SecurityInterceptor securityInterceptor = new SecurityInterceptor();

		registry.addInterceptor(securityInterceptor).addPathPatterns("/**").excludePathPatterns("/login")
				.excludePathPatterns("/").excludePathPatterns("/signin").excludePathPatterns("/validatecode.jpg")
				.excludePathPatterns("/signout").excludePathPatterns("/error").excludePathPatterns("/druid/**")
				.excludePathPatterns("/resources/**");
		
		String env = System.getProperty("env");
		if (!("online".equals(env) || "train".equals(env))) {
			registry.addInterceptor(new LogInterceptor());
		}
	}
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/resources/**").addResourceLocations(
				environment.getProperty("spring.resources.static-locations", "classpath:/static/"));

		super.addResourceHandlers(registry);
	}
}
