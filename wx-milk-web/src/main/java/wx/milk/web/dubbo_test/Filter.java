package wx.milk.web.dubbo_test;

import com.alibaba.dubbo.common.extension.SPI;

/**
 * Filter. (SPI, Singleton, ThreadSafe)
 *
 * @author william.liangf
 */
public interface Filter extends com.alibaba.dubbo.rpc.Filter {

    /**
     * do invoke filter.
     *
     * <code>
     * // before filter
     * Result result = invoker.invoke(invocation);
     * // after filter
     * return result;
     * </code>
     *
     * @see com.alibaba.dubbo.rpc.Invoker#invoke(com.alibaba.dubbo.rpc.Invocation)
     * @param invoker service
     * @param invocation invocation.
     * @return invoke result.
     * @throws RpcException
     */
    Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException;

}
