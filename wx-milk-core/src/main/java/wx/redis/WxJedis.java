package wx.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisShardInfo;
import redis.clients.util.Pool;

import java.io.Serializable;
import java.net.URI;
import java.util.Map;


public class WxJedis extends Jedis implements WxJedisCommands, Serializable {
    protected Pool<WxJedis> dataSource = null;

    public WxJedis() {
    }

    public WxJedis(String host) {
        super(host);
    }

    public WxJedis(String host, int port) {
        super(host, port);
    }

    public WxJedis(String host, int port, int timeout) {
        super(host, port, timeout);
    }

    public WxJedis(String host, int port, int connectionTimeout, int soTimeout) {
        super(host, port, connectionTimeout, soTimeout);
    }

    public WxJedis(JedisShardInfo shardInfo) {
        super(shardInfo);
    }

    public WxJedis(URI uri) {
        super(uri);
    }

    public WxJedis(URI uri, int timeout) {
        super(uri, timeout);
    }

    public WxJedis(URI uri, int connectionTimeout, int soTimeout) {
        super(uri, connectionTimeout, soTimeout);
    }

    public void setWxsDataSource(Pool<WxJedis> jedisPool) {
        this.dataSource = jedisPool;
    }

    @Override
    public String set(Long key, String value) {
        return super.set(key.toString(), value);
    }

    @Override
    public String get(Long key) {
        return super.get(key.toString());
    }

    @Override
    public Boolean exists(Long key) {
        return super.exists(key.toString());
    }

    @Override
    public String hget(Long key, String field) {
        return super.hget(key.toString(), field);
    }

    @Override
    public Long hset(String key, String field, Long value) {
        return super.hset(key, field, value.toString());
    }

    @Override
    public Long hset(Long key, String field, String value) {
        return super.hset(key.toString(), field, value);
    }

    @Override
    public Long hset(Long key, String field, Long value) {
        return super.hset(key.toString(), field, value.toString());
    }

    @Override
    public Long hdel(Long key, String... field) {
        return super.hdel(key.toString(), field);
    }

    @Override
    public Long del(Long key) {
        return super.del(key.toString());
    }

    @Override
    public String hmset(Long key, Map<String, String> hash) {
        return super.hmset(key.toString(), hash);
    }

    @Override
    public Long setnx(String key, Long value) {
        return super.setnx(key, value.toString());
    }
}