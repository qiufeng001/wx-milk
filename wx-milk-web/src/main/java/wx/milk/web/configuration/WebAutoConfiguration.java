package wx.milk.web.configuration;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;
import wx.milk.service.configuration.ServiceModelAutoConfiguration;


@Configuration
@AutoConfigureAfter(ServiceModelAutoConfiguration.class)
public class WebAutoConfiguration {
	public WebAutoConfiguration() {

	}
	
	public static void main(String[] args) {
		System.out.println("init framework config...");
	}
}
