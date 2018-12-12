package wx.redis;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import redis.clients.jedis.Protocol;
import redis.clients.util.JedisURIHelper;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.net.URI;

public class WxJedisPool extends Pool<WxJedis> implements Serializable {

    public WxJedisPool() {
        this(Protocol.DEFAULT_HOST, Protocol.DEFAULT_PORT);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host) {
        this(poolConfig, host, Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public WxJedisPool(String host, int port) {
        this(new GenericObjectPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, null,
                Protocol.DEFAULT_DATABASE, null);
    }

    public WxJedisPool(final String host) {
        URI uri = URI.create(host);
        if (JedisURIHelper.isValid(uri)) {
            String h = uri.getHost();
            int port = uri.getPort();
            String password = JedisURIHelper.getPassword(uri);
            int database = JedisURIHelper.getDBIndex(uri);
            this.internalPool = new GenericObjectPool<WxJedis>(new WxJedisFactory(h, port,
                    Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, password, database, null),
                    new GenericObjectPoolConfig());
        } else {
            this.internalPool = new GenericObjectPool<WxJedis>(new WxJedisFactory(host,
                    Protocol.DEFAULT_PORT, Protocol.DEFAULT_TIMEOUT, Protocol.DEFAULT_TIMEOUT, null,
                    Protocol.DEFAULT_DATABASE, null), new GenericObjectPoolConfig());
        }
    }

    public WxJedisPool(final URI uri) {
        this(new GenericObjectPoolConfig(), uri, Protocol.DEFAULT_TIMEOUT);
    }

    public WxJedisPool(final URI uri, final int timeout) {
        this(new GenericObjectPoolConfig(), uri, timeout);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                       int timeout, final String password) {
        this(poolConfig, host, port, timeout, password, Protocol.DEFAULT_DATABASE, null);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, Protocol.DEFAULT_DATABASE, null);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, final int port,
                       final int dbIndex) {
        this(poolConfig, host, port, Protocol.DEFAULT_TIMEOUT, null, dbIndex, null);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                       int timeout, final String password, final int database) {
        this(poolConfig, host, port, timeout, password, database, null);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                       int timeout, final String password, final int database, final String clientName) {
        this(poolConfig, host, port, timeout, timeout, password, database, clientName);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final String host, int port,
                       final int connectionTimeout, final int soTimeout, final String password, final int database,
                       final String clientName) {
        super(poolConfig, new WxJedisFactory(host, port, connectionTimeout, soTimeout, password,
                database, clientName));
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri) {
        this(poolConfig, uri, Protocol.DEFAULT_TIMEOUT);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri, final int timeout) {
        this(poolConfig, uri, timeout, timeout);
    }

    public WxJedisPool(final GenericObjectPoolConfig poolConfig, final URI uri,
                       final int connectionTimeout, final int soTimeout) {
        super(poolConfig, new WxJedisFactory(uri, connectionTimeout, soTimeout, null));
    }

    @Override
    public WxJedis getResource() {
        WxJedis jedis = super.getResource();
        jedis.setWxsDataSource(this);
        return jedis;
    }
}
