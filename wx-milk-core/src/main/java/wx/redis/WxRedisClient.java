package wx.redis;

import java.io.Serializable;

/**
 * Redis统一出入口
 */
public class WxRedisClient implements Serializable {
    private static WxJedisCommands sessionJedis;
    private static WxJedisCommands commonJedis;

    public static WxJedisCommands getSessionJedis() {
        return sessionJedis;
    }

    public void setSessionJedis(WxJedisCommands sessionJedis) {
        WxRedisClient.sessionJedis = sessionJedis;
    }

    public static WxJedisCommands getCommonJedis() {
        return commonJedis;
    }

    public void setCommonJedis(WxJedisCommands commonJedis) {
        WxRedisClient.commonJedis = commonJedis;
    }
}
