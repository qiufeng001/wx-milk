package wx.configuration;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

/**
 * 此类的作用是在项目启动的时候，在控制台打印出端口号为多少的项目的启动时间
 *
 * @author Kevin
 *
 * try it,do it best!
 */
public abstract class ApplicationBootStrap {

	protected Log logger = LogFactory.getLog(getClass());

	/*@Value("${http.server.port}")
	private int httpPort;

	@Value("${https.server.port}")
	private int httpsPort;
*/
	public ApplicationBootStrap() {
	}

	public ApplicationContext run(String[] args) {
		long takes = System.currentTimeMillis();
		ApplicationContext ctx = SpringApplication.run(this.getClass(), args);
		String port = ctx.getEnvironment().getProperty("server.port");
		String[] activeProfiles = ctx.getEnvironment().getActiveProfiles();
		System.err.println("profile:" + StringUtils.join(activeProfiles, ","));
		takes = System.currentTimeMillis() - takes;
		System.err.println(String.format("##Application started at %s. takes:%sms", port, takes));
		return ctx;
	}

/*	@Bean
	public EmbeddedServletContainerFactory servletContainer() {
		TomcatEmbeddedServletContainerFactory tomcat = new TomcatEmbeddedServletContainerFactory() {
			@Override
			protected void postProcessContext(Context context) {
				SecurityConstraint constraint = new SecurityConstraint();
				constraint.setUserConstraint("CONFIDENTIAL");
				SecurityCollection collection = new SecurityCollection();
				collection.addPattern("*//*");
				constraint.addCollection(collection);
				context.addConstraint(constraint);
			}
		};
		tomcat.addAdditionalTomcatConnectors(httpConnector());
		return tomcat;
	}

	@Bean
	public Connector httpConnector() {
		Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
		connector.setScheme("http");
		//Connector监听的http的端口号
		connector.setPort(httpPort);
		connector.setSecure(false);
		//监听到http的端口号后转向到的https的端口号
		connector.setRedirectPort(httpsPort);
		return connector;
	}*/
}
