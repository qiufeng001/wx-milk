package wx.milk.web.dubbo_test;

import java.util.Map;

/**
 * Invocation. (API, Prototype, NonThreadSafe)
 *
 * @serial Don't change the class name and package name.
 * @see com.alibaba.dubbo.rpc.RpcInvocation
 * @author qian.lei
 * @author william.liangf
 */
public interface Invocation {

    /**
     * get method name.
     *
     * @serial
     * @return method name.
     */
    String getMethodName();

    /**
     * get parameter types.
     *
     * @serial
     * @return parameter types.
     */
    Class<?>[] getParameterTypes();

    /**
     * get arguments.
     *
     * @serial
     * @return arguments.
     */
    Object[] getArguments();

    /**
     * get attachments.
     *
     * @serial
     * @return attachments.
     */
    Map<String, Object> getAttachments();

    /**
     * get attachment by key.
     *
     * @serial
     * @return attachment value.
     */
    Object getAttachment(String key);

    /**
     * get attachment by key with default value.
     *
     * @serial
     * @return attachment value.
     */
    Object getAttachment(String key, String defaultValue);

    /**
     * get the invoker in current context.
     *
     * @transient
     * @return invoker.
     */
    Invoker<?> getInvoker();

}