package wx.milk.web.tracer;

import brave.Span;
import brave.Tracer;
import brave.internal.Platform;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.protocol.dubbo.FutureAdapter;
import com.alibaba.dubbo.rpc.support.RpcUtils;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang.StringUtils;
import wx.milk.service.zipkin.IdUtils;
import wx.milk.service.zipkin.RpcTraceContext;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * 帮助类
 */
public class ZipKinHelper {

    public static final Propagation.Setter<Map<String, String>, String> SETTER =
            new Propagation.Setter<Map<String, String>, String>() {
                @Override
                public void put(Map<String, String> carrier, String key, String value) {
                    carrier.put(key, value);
                }

                @Override
                public String toString() {
                    return JSON.toJSONString(this);
                }
            };

    public static final Propagation.Getter<Map<String, String>, String> GETTER =
            new Propagation.Getter<Map<String, String>, String>() {
                @Override
                public String get(Map<String, String> carrier, String key) {
                    return carrier.get(key);
                }

                @Override
                public String toString() {
                    return JSON.toJSONString(this);
                }
            };

    /**
     * RpcTraceContext 添加必要属性值
     * @param invocation dubbo远程调用
     */
    public static void setRpcContext(Invocation invocation) {
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
    }

    /**
     * 创建span
     * @param tracer 调用链对象
     * @param invoker
     * @param invocation dubbo远程调用
     * @param rpcContext dubbo上下文
     * @return span对象
     */
    public static Span buildSpan(Tracer tracer, Invoker<?> invoker, Invocation invocation, RpcContext rpcContext) {
        TraceContext traceContext = TraceContext.newBuilder()
                .traceId(RpcTraceContext.getTraceId())
                .parentId(RpcTraceContext.getParentId())
                .spanId(RpcTraceContext.getSpanId())
                .sampled(true)
                .build();
        InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();

        Span span = tracer.toSpan(traceContext).start();
        span.annotate("serverRemoteStartTime");
        span.name(RpcUtils.getMethodName(invocation));
        span.remoteIpAndPort(Platform.get().getHostString(remoteAddress), remoteAddress.getPort());

        span.tag("http.path", invoker.getInterface().getSimpleName() + "/" + rpcContext.getMethodName());

        rpcContext.setAttachment(RpcTraceContext.TRACE_ID_KEY, String.valueOf(span.context().traceId()));
        rpcContext.setAttachment(RpcTraceContext.SPAN_ID_KEY, String.valueOf(span.context().spanId()));
        return span;
    }

    /**
     * 执行上下文
     * @param span span对象
     * @param tracer tace 对象
     * @param invoker
     * @param invocation dubbo远程调用
     * @param rpcContext dubbo上下文
     * @return 服务运行结果
     */
    public static Result spanTracing(Span span, Tracer tracer, Invoker<?> invoker, Invocation invocation, RpcContext rpcContext) {
        boolean isOneway = false;
        boolean deferFinish = false;
        try (Tracer.SpanInScope scope = tracer.withSpanInScope(span)) {
            Result result = invoker.invoke(invocation);
            if (result.hasException()) {
                onError(result.getException(), span);
            }
            isOneway = RpcUtils.isOneway(invoker.getUrl(), invocation);
            Future<Object> future = rpcContext.getFuture(); // the case on async client invocation
            if (future instanceof FutureAdapter) {
                deferFinish = true;
                ((FutureAdapter) future).getFuture().setCallback(new ZipKinHelper.FinishSpanCallback(span));
            }
            return result;
        } catch (Exception e) {
            onError(e, span);
            throw e;
        } finally {
            if (isOneway) {
                span.flush();
            } else if (!deferFinish) {
                span.annotate("serverRemoteFinishTime");
                span.finish();
            }
        }
    }

    private static void onError(Throwable error, Span span) {
        span.error(error);
        if (error instanceof RpcException) {
            span.tag("dubbo.error_code", Integer.toString(((RpcException) error).getCode()));
        }
    }

    private static final class FinishSpanCallback implements ResponseCallback {
        final Span span;

        FinishSpanCallback(Span span) {
            this.span = span;
        }

        @Override
        public void done(Object response) {
            span.finish();
        }

        @Override
        public void caught(Throwable exception) {
            onError(exception, span);
            span.finish();
        }
    }
}