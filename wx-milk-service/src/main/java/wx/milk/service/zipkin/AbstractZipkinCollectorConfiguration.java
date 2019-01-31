package wx.milk.service.zipkin;

import brave.Tracing;
import brave.sampler.Sampler;
import org.springframework.beans.factory.annotation.Value;
import zipkin2.Span;
import zipkin2.codec.SpanBytesEncoder;
import zipkin2.reporter.AsyncReporter;
import zipkin2.reporter.Sender;

import java.util.concurrent.TimeUnit;

public abstract class AbstractZipkinCollectorConfiguration {

    private Tracing tracing;

    @Value("${zipkin.tracing.zipkinUrl}")
    private String zipkinUrl;

    @Value("${zipkin.tracing.serverName}")
    private String serviceName;

    private String topic;

    public String getTopic() {
        return topic;
    }

    protected String getZipkinUrl() {
        return this.zipkinUrl;
    }

    public AbstractZipkinCollectorConfiguration(String serviceName,String zipkinUrl,String topic){
        this.zipkinUrl=zipkinUrl;
        this.serviceName=serviceName;
        this.topic=topic;
        this.tracing=this.tracing();
    }

    public abstract Sender getSender();

    protected AsyncReporter<Span> spanReporter() {
        return AsyncReporter
                .builder(getSender())
                .closeTimeout(500, TimeUnit.MILLISECONDS)
                .build(SpanBytesEncoder.JSON_V1);
    }

    protected Tracing tracing() {
        this.tracing= Tracing
                .newBuilder()
                .localServiceName(this.serviceName)
                .sampler(Sampler.ALWAYS_SAMPLE)
                .spanReporter(spanReporter())
                .build();
        return this.tracing;
    }

    protected Tracing getTracing(){
        return this.tracing;
    }
}
