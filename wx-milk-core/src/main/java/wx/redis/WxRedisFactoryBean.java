//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package wx.redis;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.aop.framework.ProxyFactory;
import org.springframework.beans.factory.FactoryBean;
import redis.clients.jedis.JedisCommands;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.exceptions.JedisConnectionException;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;

public class WxRedisFactoryBean implements MethodInterceptor, FactoryBean<JedisCommands>, Serializable {
    private WxJedisPool jedisPool;
    private WxJedis jedis;
    private WxJedisCommands proxy;
    private String host;
    private int port;

    public WxRedisFactoryBean(String host, int port) {
        this.host = host;
        this.port = port;
        this.jedis = new WxJedis(host, port);
        this.proxy = (WxJedisCommands) (new ProxyFactory(WxJedisCommands.class, this)).getProxy();
    }

    public WxRedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host, int port) {
        this.host = host;
        this.port = port;
        this.jedisPool = new WxJedisPool(jedisPoolConfig, host, port);
        this.proxy = (WxJedisCommands) (new ProxyFactory(WxJedisCommands.class, this)).getProxy();
    }

    public WxRedisFactoryBean(JedisPoolConfig jedisPoolConfig, String host, int port, int dbIndex) {
        this.host = host;
        this.port = port;
        this.jedisPool = new WxJedisPool(jedisPoolConfig, host, port, dbIndex);
        this.proxy = (WxJedisCommands) (new ProxyFactory(WxJedisCommands.class, this)).getProxy();
    }

    WxJedisCommands getJedis() {
        return this.proxy;
    }
    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return this.jedisPool == null ? this.invokeInternal(invocation) : this.invokeInternalWithPool(invocation);
    }

    public synchronized Object invokeInternal(MethodInvocation invocation) throws Throwable {
        try {
            return invocation.getMethod().invoke(this.jedis, invocation.getArguments());
        } catch (InvocationTargetException var6) {
            Throwable targetException = var6.getTargetException();
            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    try {
                        this.jedis.disconnect();
                    } catch (Exception ignored) {
                    }

                    this.jedis = new WxJedis(this.host, this.port);
                }

                throw targetException;
            } else {
                throw var6;
            }
        }
    }

    public Object invokeInternalWithPool(MethodInvocation invocation) throws Throwable {
        WxJedis jedisFromPool = this.jedisPool.getResource();
        Object result;

        try {
            result = invocation.getMethod().invoke(jedisFromPool, invocation.getArguments());
        } catch (InvocationTargetException var6) {
            Throwable targetException = var6.getTargetException();
            if (targetException != null) {
                if (targetException instanceof JedisConnectionException) {
                    this.returnBrokenJedis(jedisFromPool);
                } else {
                    this.returnJedis(jedisFromPool);
                }

                throw targetException;
            }

            this.returnJedis(jedisFromPool);
            throw var6;
        }

        this.returnJedis(jedisFromPool);
        return result;
    }

    void returnBrokenJedis(WxJedis jedisFromPool) {
        try {
            this.jedisPool.returnBrokenResource(jedisFromPool);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    void returnJedis(WxJedis jedisFromPool) {
        try {
            this.jedisPool.returnResource(jedisFromPool);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    @Override
    public WxJedisCommands getObject() throws Exception {
        return this.getJedis();
    }
    @Override
    public Class<WxJedisCommands> getObjectType() {
        return WxJedisCommands.class;
    }
    @Override
    public boolean isSingleton() {
        return true;
    }
}
