package wx.milk.web.tracer;

import brave.Span;
import brave.Tracer;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import wx.milk.service.zipkin.IdUtils;
import wx.milk.service.zipkin.RpcTraceContext;
import wx.milk.service.zipkin.SpringContextUtils;
import wx.milk.service.zipkin.ZipkinCollectorConfigurationFactory;

import java.util.Map;

@Activate(
        group = {Constants.PROVIDER, Constants.CONSUMER}
)
public class ZipKinTracerFilter implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass().getName());

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!RpcTraceContext.getTracerConfig().isEnabled()) {
            return invoker.invoke(invocation);
        }
        RpcContext rpcContext = RpcContext.getContext();

        Map<String, String> attaches = invocation.getAttachments();
        if (!attaches.containsKey(RpcTraceContext.TRACE_ID_KEY)) {
            String id = (String) invocation.getArguments()[0];
            if (StringUtils.isNotEmpty(id + "")) {
                try {
                    long traceId = Long.parseLong(id, 16);
                    RpcTraceContext.start();
                    RpcTraceContext.setParentId(traceId);
                    RpcTraceContext.setTraceId(traceId);
                    RpcTraceContext.setSpanId(IdUtils.get());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            long traceId = Long.valueOf(attaches.get(RpcTraceContext.TRACE_ID_KEY));
            Long spanId = Long.valueOf(attaches.get(RpcTraceContext.SPAN_ID_KEY));
            attaches.remove(RpcTraceContext.TRACE_ID_KEY);
            attaches.remove(RpcTraceContext.SPAN_ID_KEY);
            RpcTraceContext.start();
            RpcTraceContext.setTraceId(traceId);
            RpcTraceContext.setParentId(spanId);
            RpcTraceContext.setSpanId(IdUtils.get());
        }
        Tracer tracer = SpringContextUtils.getApplicationContext().getBean(ZipkinCollectorConfigurationFactory.class).getTracing().tracer();

        Span span = ZipKinHelper.buildSpan(tracer, invocation, rpcContext);

        rpcContext.setAttachment(RpcTraceContext.TRACE_ID_KEY, String.valueOf(span.context().traceId()));
        rpcContext.setAttachment(RpcTraceContext.SPAN_ID_KEY, String.valueOf(span.context().spanId()));

        return ZipKinHelper.spanTracing(span, tracer, invoker, invocation, rpcContext);
    }
}
