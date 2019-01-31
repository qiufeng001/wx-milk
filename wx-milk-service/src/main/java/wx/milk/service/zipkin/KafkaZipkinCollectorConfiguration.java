package wx.milk.service.zipkin;

import zipkin2.codec.Encoding;
import zipkin2.reporter.Sender;
import zipkin2.reporter.kafka11.KafkaSender;


public class KafkaZipkinCollectorConfiguration extends AbstractZipkinCollectorConfiguration {

    public KafkaZipkinCollectorConfiguration(String serviceName,String zipkinUrl,String topic) {
        super(serviceName,zipkinUrl,topic);
    }

    @Override
    public Sender getSender() {

        return KafkaSender
                .newBuilder()
                .bootstrapServers(super.getZipkinUrl())
                .topic(super.getTopic())
                .encoding(Encoding.JSON)
                .build();
    }
}
