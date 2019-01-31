package wx.milk.web.tracer;

import brave.Span;
import brave.SpanCustomizer;
import brave.Tracer;
import brave.Tracing;
import brave.internal.Platform;
import brave.propagation.Propagation;
import brave.propagation.TraceContext;
import brave.propagation.TraceContextOrSamplingFlags;
import com.alibaba.dubbo.common.Constants;
import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.remoting.exchange.ResponseCallback;
import com.alibaba.dubbo.rpc.*;
import com.alibaba.dubbo.rpc.protocol.dubbo.FutureAdapter;
import com.alibaba.dubbo.rpc.support.RpcUtils;

import java.net.InetSocketAddress;
import java.util.Map;
import java.util.concurrent.Future;

@Activate(
        group = {Constants.PROVIDER, Constants.CONSUMER}
)
public class ZipKinTracerFilter2 implements Filter {

    Tracer tracer;
    TraceContext.Extractor<Map<String, String>> extractor;
    TraceContext.Injector<Map<String, String>> injector;
    static final Propagation.Getter<Map<String, String>, String> GETTER = new Propagation.Getter<Map<String, String>, String>() {
        public String get(Map<String, String> carrier, String key) {
            return (String)carrier.get(key);
        }

        public String toString() {
            return "Map::get";
        }
    };
    static final Propagation.Setter<Map<String, String>, String> SETTER = new Propagation.Setter<Map<String, String>, String>() {
        public void put(Map<String, String> carrier, String key, String value) {
            carrier.put(key, value);
        }

        public String toString() {
            return "Map::set";
        }
    };

    public ZipKinTracerFilter2() {
    }

    public void setTracing(Tracing tracing) {
        this.tracer = tracing.tracer();
        this.extractor = tracing.propagation().extractor(GETTER);
        this.injector = tracing.propagation().injector(SETTER);
    }

    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        if (this.tracer == null) {
            return invoker.invoke(invocation);
        } else {
            RpcContext rpcContext = RpcContext.getContext();


            Span.Kind kind = rpcContext.isProviderSide() ? Span.Kind.SERVER : Span.Kind.CLIENT;
            Span span;
            if (kind.equals(Span.Kind.CLIENT)) {
                span = this.tracer.nextSpan();
                this.injector.inject(span.context(), invocation.getAttachments());
            } else {
                TraceContextOrSamplingFlags extracted = this.extractor.extract(invocation.getAttachments());
                span = extracted.context() != null ? this.tracer.joinSpan(extracted.context()) : this.tracer.nextSpan(extracted);
            }

            if (!span.isNoop()) {
                span.start();
                span.kind(kind);
                String service = invoker.getInterface().getSimpleName();
                String method = RpcUtils.getMethodName(invocation);
                span.name(service + "/" + method);
                parseRemoteAddress(rpcContext, span);

            }

            boolean isOneway = false;
            boolean deferFinish = false;

            Result var12;
            try {
                Tracer.SpanInScope scope = this.tracer.withSpanInScope(span);
                Throwable var9 = null;

                try {
                    Result result = invoker.invoke(invocation);
                    if (result.hasException()) {
                        onError(result.getException(), span);
                    }

                    isOneway = RpcUtils.isOneway(invoker.getUrl(), invocation);
                    Future<Object> future = rpcContext.getFuture();
                    if (future instanceof FutureAdapter) {
                        deferFinish = true;
                        ((FutureAdapter)future).getFuture().setCallback(new ZipKinTracerFilter2.FinishSpanCallback(span));
                    }

                    var12 = result;
                } catch (Throwable var30) {
                    var9 = var30;
                    throw var30;
                } finally {
                    if (scope != null) {
                        if (var9 != null) {
                            try {
                                scope.close();
                            } catch (Throwable var29) {
                                ;
                            }
                        } else {
                            scope.close();
                        }
                    }

                }
            } catch (RuntimeException | Error var32) {
                onError(var32, span);
                throw var32;
            } finally {
                if (isOneway) {
                    span.flush();
                } else if (!deferFinish) {
                    span.finish();
                }

            }

            return var12;
        }
    }

    static void parseRemoteAddress(RpcContext rpcContext, Span span) {
        InetSocketAddress remoteAddress = rpcContext.getRemoteAddress();
        if (remoteAddress != null) {
//            span.remoteIpAndPort(Platform.get().getHostString(remoteAddress), remoteAddress.getPort());
        }
    }

    static void onError(Throwable error, Span span) {
//        span.error(error);
        if (error instanceof RpcException) {
            span.tag("dubbo.error_code", Integer.toString(((RpcException)error).getCode()));
        }

    }

    static final class FinishSpanCallback implements ResponseCallback {
        final Span span;

        FinishSpanCallback(Span span) {
            this.span = span;
        }

        public void done(Object response) {
            this.span.finish();
        }

        public void caught(Throwable exception) {
            ZipKinTracerFilter2.onError(exception, this.span);
            this.span.finish();
        }
    }
}
