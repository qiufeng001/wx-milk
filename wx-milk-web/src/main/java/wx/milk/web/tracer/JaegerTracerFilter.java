/*
package wx.milk.web.tracer;

import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;

import com.alibaba.dubbo.rpc.*;
import com.framework.core.redis.WxJedisCommands;
import com.framework.core.redis.WxRedisClient;
import io.opentracing.Scope;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

import org.apache.commons.lang.StringUtils;
import wx.milk.service.zipkin.TracerContext;
//import wx.milk.web.dubbo_test.*;

import java.util.Map;

@Activate(group = Constants.PROVIDER)
public class JaegerTracerFilter implements Filter {

    private WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {

//        startTrace(invoker, invocation);
        io.opentracing.Span span = setSpan(invocation);
        Result result = invoker.invoke(invocation);
        span.finish();
        return result;
    }

    private io.opentracing.Span setSpan(Invocation invocation) {
        Tracer tracer;
        io.opentracing.Span parentSpan;
        io.opentracing.Span childSpan = null;
        Map<String, String> context = RpcContext.getContext().getAttachments();
        String tracerIdKey = context.get(TracerContext.TRACE_ID_KEY);
        if(StringUtils.isEmpty(tracerIdKey)) {

//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put(TracerContext.TRACE, tracer);
//            map.put(TracerContext.SPAN, childSpan);
//            commonJedis.set(UUIDUtils.getUUID(), new String(SerializeUtil.serialize(map)));
        }
        tracer = GlobalTracer.get();

        childSpan = tracer.buildSpan("findByParam").start();
        childSpan.setTag("methodName", "IUserService.findByParam");
        childSpan.log("第一次调用！");
        Scope scope = tracer.scopeManager().activate(childSpan, false);

        Object tracerStr = context.get(TracerContext.TRACE);

*/
/*
        if(tracerStr != null) {
            tracer = (Tracer) tracerStr;
            parentSpan = (io.opentracing.Span) attaches.get(TracerContext.SPAN);
            childSpan = tracer.buildSpan("findByParam").asChildOf(parentSpan).start();
            childSpan.setTag("methodName", "IUserService.findByParam");
            childSpan.log("第n次调用！");
        }else {

        }


        context.put(TracerContext.TRACE, "lllllllll");
        context.put(TracerContext.SPAN, "222222222");*//*



        return childSpan;

    }

}
*/
