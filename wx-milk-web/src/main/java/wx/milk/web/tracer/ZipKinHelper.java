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
import wx.milk.service.zipkin.RpcTraceContext;
import zipkin2.Endpoint;

import java.net.InetSocketAddress;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.Future;

/**
 * @author wenj91
 * @Description:
 * @date 2018/6/22 10:44
 */
public class ZipKinHelper {

    static final Propagation.Setter<Map<String, String>, String> SETTER =
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

    static final Propagation.Getter<Map<String, String>, String> GETTER =
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

    static Span buildSpan(Tracer tracer, Invocation invocation, RpcContext rpcContext) {
        TraceContext traceContext = TraceContext.newBuilder()
                .traceId(RpcTraceContext.getTraceId())
                .parentId(RpcTraceContext.getParentId())
                .spanId(RpcTraceContext.getSpanId())
                .sampled(true)
                .build();

        InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();
        String localHost = rpcContext.getLocalHost();

        Endpoint.Builder remoteEndpoint = Endpoint.newBuilder().port(remoteAddress.getPort());
        remoteEndpoint.serviceName(RpcTraceContext.getTracerConfig().getApplicationName());
        remoteEndpoint.parseIp("");
        Span span = tracer.joinSpan(traceContext).start();
        span.annotate("serverRemoteStartTime");
        span.tag("tag", "hahah");
        span.name(RpcUtils.getMethodName(invocation));
        span.remoteIpAndPort(Platform.get().getHostString(remoteAddress), remoteAddress.getPort());
        span.remoteServiceName(RpcTraceContext.getTracerConfig().getApplicationName());
        span.remoteEndpoint(remoteEndpoint.build());

        return span;
    }

    static Result spanTracing(Span span, Tracer tracer, Invoker<?> invoker, Invocation invocation, RpcContext rpcContext) {
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

    static void onError(Throwable error, Span span) {
        span.error(error);
        if (error instanceof RpcException) {
            span.tag("dubbo.error_code", Integer.toString(((RpcException) error).getCode()));
        }
    }

    static final class FinishSpanCallback implements ResponseCallback {
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