package wx.milk.service.zipkin;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@ConfigurationProperties(prefix = "zipkin.tracing")
@Component
public class TracerConfig {

    @PostConstruct
    public void initMethod(){
        RpcTraceContext.setTracerConfig(this);
    }

//    @Value("${zipkin.tracing.enabled}")
    private boolean enabled;

//    @Value("${zipkin.tracing.connectTimeout}")
    private int connectTimeout;

//    @Value("${zipkin.tracing.readTimeout}")
    private int readTimeout;

    private int flushInterval=0;

    private boolean compressionEnabled=true;

//    @Value("${zipkin.tracing.zipkinUrl}")
    private String zipkinUrl;

    private String zipkinKafkaTopic;

    private String zipkinSendType="http";

    @Value("${server.port}")
    private int serverPort;

    @Value("${zipkin.tracing.serverName}")
    private String applicationName;

    public int getServerPort(){
        return this.serverPort;
    }

    public String getApplicationName(){
        return this.applicationName;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getConnectTimeout() {
        return connectTimeout;
    }

    public void setConnectTimeout(int connectTimeout) {
        this.connectTimeout = connectTimeout;
    }

    public int getReadTimeout() {
        return readTimeout;
    }

    public void setReadTimeout(int readTimeout) {
        this.readTimeout = readTimeout;
    }

    public int getFlushInterval() {
        return flushInterval;
    }

    public void setFlushInterval(int flushInterval) {
        this.flushInterval = flushInterval;
    }

    public boolean isCompressionEnabled() {
        return compressionEnabled;
    }

    public void setCompressionEnabled(boolean compressionEnabled) {
        this.compressionEnabled = compressionEnabled;
    }

    public String getZipkinUrl() {
        return zipkinUrl;
    }

    public void setZipkinUrl(String zipkinUrl) {
        this.zipkinUrl = zipkinUrl;
    }

    public String getZipkinKafkaTopic() {
        return zipkinKafkaTopic;
    }

    public void setZipkinKafkaTopic(String zipkinKafkaTopic) {
        this.zipkinKafkaTopic = zipkinKafkaTopic;
    }

    public String getZipkinSendType() {
        return zipkinSendType;
    }

    public void setZipkinSendType(String zipkinSendType) {
        this.zipkinSendType = zipkinSendType;
    }
}
