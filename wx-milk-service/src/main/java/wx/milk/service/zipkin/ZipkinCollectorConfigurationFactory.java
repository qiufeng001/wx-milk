package wx.milk.service.zipkin;

import brave.Tracing;
import com.google.common.base.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class ZipkinCollectorConfigurationFactory {

    private final AbstractZipkinCollectorConfiguration zipkinCollectorConfiguration;

    @Autowired
    public ZipkinCollectorConfigurationFactory(TracerConfig tracerConfig){
        if(Objects.equal("kafka", tracerConfig.getZipkinSendType())){
            zipkinCollectorConfiguration=new KafkaZipkinCollectorConfiguration(
                    tracerConfig.getApplicationName(),
                    tracerConfig.getZipkinUrl(),
                    tracerConfig.getZipkinKafkaTopic());
        }else {
            zipkinCollectorConfiguration = new HttpZipkinCollectorConfiguration(
                    tracerConfig.getApplicationName(),
                    tracerConfig.getZipkinUrl());
        }
    }

    public Tracing getTracing(){
        return this.zipkinCollectorConfiguration.getTracing();
    }

}
