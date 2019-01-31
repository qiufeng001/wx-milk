package wx.milk.service.zipkin;

public class RpcTraceContext extends AbstractContext {

    private static TracerConfig tracerConfig;

    private static ThreadLocal<Long> TRACE_ID = new InheritableThreadLocal<>();

    private static ThreadLocal<Long> SPAN_ID = new InheritableThreadLocal<>();

    private static ThreadLocal<Long> PARENT_ID = new InheritableThreadLocal<>();

    public static final String TRACE_ID_KEY = "traceId";

    public static final String SPAN_ID_KEY = "spanId";

    public static TracerConfig getTracerConfig(){
        return tracerConfig;
    }

    public static void setTracerConfig(TracerConfig config){
        tracerConfig = config;
    }

    public static void setTraceId(long traceId){
        TRACE_ID.set(traceId);
    }

    public static long getTraceId(){
        return TRACE_ID.get();
    }

    public static long getSpanId() {
        return SPAN_ID.get();
    }

    public static void setSpanId(long spanId) {
        SPAN_ID.set(spanId);
    }

    public static long getParentId() {
        return PARENT_ID.get();
    }

    public static void setParentId(long parentId) {
        PARENT_ID.set(parentId);
    }


    private static void clear(){
        TRACE_ID.remove();
        SPAN_ID.remove();
    }

    public static void init(TracerConfig tracerConfig) {
        setTracerConfig(tracerConfig);
    }

    public static void start(){
        clear();
    }

}
