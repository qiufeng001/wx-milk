package wx.milk.web;

import com.framework.core.configuration.ApplicationBootStrap;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication
@EnableTransactionManagement // 事物注解
@ImportResource(locations={"classpath:spring-redis.xml"})
@ComponentScan(basePackages = {
								"wx.milk.web.controller",
								"wx.milk.web.base",
								"wx.milk.web.utils"
							   })
public class WebApplicationBootStrap extends ApplicationBootStrap {
	public static void main(String[] args) {
		new WebApplicationBootStrap().run(args);
	}
}