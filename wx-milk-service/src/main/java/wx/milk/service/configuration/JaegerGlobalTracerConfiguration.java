/*
package wx.milk.service.configuration;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class JaegerGlobalTracerConfiguration {
    private static Integer FLUSH_INTERVAL = 100;
    private static Integer AGENT_PORT = 5775;
    private static String END_POINT = "http://192.168.186.144:14268/api/traces";
    private static String AGENT_HOST = "127.0.0.1";
    private static String SERVICE_NAME = "wx-test-agent";
    private static String TYPE = "const";

    @Bean
    public String grobalProducer(){
        Configuration config;
        config = new Configuration(SERVICE_NAME);
        Configuration.SenderConfiguration sender = new Configuration.SenderConfiguration();
//        sender.withAgentHost(AGENT_HOST);
//        sender.withAgentPort(AGENT_PORT);
        sender.withEndpoint(END_POINT);
        config.withReporter(new io.jaegertracing.Configuration.ReporterConfiguration().withSender(sender).withFlushInterval(FLUSH_INTERVAL).withLogSpans(false));
        config.withSampler(new io.jaegertracing.Configuration.SamplerConfiguration().withType(TYPE).withParam(1));
        Tracer tracer = config.getTracer();
        GlobalTracer.register(tracer);
        return "success";
    }
}
*/
