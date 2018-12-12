package wx.milk.service.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {"wx.milk.service"})
public class ServiceAutoConifg {
	public static void main(String[] args) {
		System.out.println("init service config ...");
	}
}