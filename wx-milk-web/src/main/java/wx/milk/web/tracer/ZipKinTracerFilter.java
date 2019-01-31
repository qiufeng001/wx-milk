package wx.milk.web.tracer;

import brave.Span;
import brave.Tracer;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.*;
import wx.milk.service.zipkin.RpcTraceContext;
import wx.milk.service.zipkin.SpringContextUtils;
import wx.milk.service.zipkin.ZipkinCollectorConfigurationFactory;


@Activate(
        group = {Constants.PROVIDER, Constants.CONSUMER}
)
public class ZipKinTracerFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (!RpcTraceContext.getTracerConfig().isEnabled()) {
            return invoker.invoke(invocation);
        }
        RpcContext rpcContext = RpcContext.getContext();

        ZipKinHelper.setRpcContext(invocation);
        Tracer tracer = SpringContextUtils.getApplicationContext().getBean(ZipkinCollectorConfigurationFactory.class).getTracing().tracer();
        Span span = ZipKinHelper.buildSpan(tracer, invoker, invocation, rpcContext);
        return ZipKinHelper.spanTracing(span, tracer, invoker, invocation, rpcContext);
    }
}
