package wx.milk.service.zipkin2;

import brave.Tracing;
import brave.sampler.Sampler;
import brave.servlet.TracingFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import zipkin2.Span;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Reporter;
import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

import javax.servlet.Filter;

@Configuration
public class TracingConfig {

    /**
     * 配置zipkin服务地址
     */

    @Value("${zipkin.tracing.zipkinUrl}")
    private String zipkinEndPoint;

    @Value("${zipkin.tracing.serverName}")
    private String serviceName;


    /**
     * 配置sender
     *
     * @return
     */

    @Bean
    public Sender sender() {
        OkHttpSender sender = OkHttpSender
                .newBuilder()
                .endpoint(zipkinEndPoint)
                .build();
        return sender;
    }

    /**
     * 配置reporter
     *
     * @param sender
     * @return
     */
    @Bean
    public Reporter<Span> reporter(Sender sender) {
        return AsyncReporter
                .builder(sender)
                .build();
    }

    /**
     * 配置dubbo-consumer tracing
     *
     * @param reporter
     * @return
     */

    @Bean
    public Tracing tracing(Reporter reporter) {
        return Tracing.newBuilder()
                .localServiceName(serviceName)
                .spanReporter(reporter)
                .sampler(Sampler.create(1))
                .build();
    }

    @Bean
    public AsyncReporter<Span> spanReporter() {
        return AsyncReporter.create(sender());
    }


    /**
     * 配置http tracing
     *
     * @param reporter
     * @return
     */
    @Bean
    public Tracing tracing2(Reporter reporter) {
        return Tracing.newBuilder()
                .localServiceName(serviceName + "_http")
                .spanReporter(reporter)
                .build();
    }


    /**
     * 配置servlet filter
     *
     * @param tracing2
     * @return
     */

    @Bean
    public Filter filter(Tracing tracing2) {
        return TracingFilter.create(tracing2);
    }

    /**
     * 注册filter
     *
     * @param filter
     * @return
     */

    @Bean
    public FilterRegistrationBean filterRegistration(Filter filter) {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(filter);
        registration.addUrlPatterns("/*");
        registration.setName("zipkin-filter");
        registration.setOrder(1);
        return registration;
    }
}
