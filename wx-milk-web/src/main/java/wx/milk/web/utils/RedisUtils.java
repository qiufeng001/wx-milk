package wx.milk.web.utils;

import wx.milk.manager.IWxCommonManager;
import wx.query.Query;
import wx.query.Statement;
import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;
import wx.security.User;
import wx.util.JsonUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 操作redis的一些操作，放到这里，提高代码简介性
 */
public class RedisUtils {

    private static WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();

    /**
     * 保存用户登录时的sessionId
     * @param user 当前登录的用户
     * @param loginToken 根据规则产生的 token
     */
    public static void setLoginToken(User user,String loginToken) {
        commonJedis.set(loginToken, JsonUtils.toJson(user));
    }

    /**
     * 用户退出时，删除登陆的 token
     * @param request http请求，其中携带保存用户 token 的 Cookie
     */
    public static void deleteLoginToken(HttpServletRequest request) {
        commonJedis.del(CookieUtils.getLoginToken(request));
    }

    /**
     * 通过token获取登录用户的json对象
     *
     * @param request
     * @return
     */
    public static String getUserJsonByToken(HttpServletRequest request) {
        return commonJedis.get(CookieUtils.getLoginToken(request));
    }

    /**
     * 保存每个模块对应的sequence
     * @param sequenceType 模块类型，例如菜单模块：MENU_SEQUENCE
     * @param sequence 排序数
     */
    public static void setSequence(String sequenceType, String sequence) {
        commonJedis.set(sequenceType, sequence);
    }

    /**
     * 获取模块对应的排序数,并且保存到redis中
     *
     * @param query 查询条件
     * @param sequenceType  模块类型，例如菜单模块：MENU_SEQUENCE
     * @param tableName 模块表：比如是菜单，为：t_menu
     * @return 排序数
     */
    public static String getAndUpdateSequence(Query query, IWxCommonManager commonManager, String sequenceType, String tableName) {
        Statement statement = new Statement();
        query.and(statement);

        String sequence = "";
        String sequenceRedis = commonJedis.get(sequenceType);
        if(sequenceRedis == null || sequenceRedis.equalsIgnoreCase("")) {
            statement.setName("tableName");
            statement.setValue(tableName);
            sequence = (Integer.valueOf(commonManager.findMaxSequence(query).getSequence()) + 1) + "";
        }else {
            sequence = (Integer.valueOf(sequenceRedis) + 1) + "";
        }

        setSequence(sequenceType, sequence);
        return sequence;
    }
}
