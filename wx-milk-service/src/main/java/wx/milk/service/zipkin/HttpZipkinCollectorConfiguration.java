package wx.milk.service.zipkin;

import zipkin2.reporter.Sender;
import zipkin2.reporter.okhttp3.OkHttpSender;

public class HttpZipkinCollectorConfiguration extends AbstractZipkinCollectorConfiguration {

    public HttpZipkinCollectorConfiguration(String serviceName,String zipkinUrl) {
        super(serviceName,zipkinUrl,null);
    }

    @Override
    public Sender getSender() {
        return OkHttpSender.create(super.getZipkinUrl());
    }
}
