package wx.util;

import org.apache.shiro.SecurityUtils;
import wx.security.User;

/**
 * 用于对用户的权限和登录进行操作
 * Created by Administrator on 2018/6/15/015.
 */
public class ShiroUtils {

    /**
     * 获取当前登录的用户对象
     * @return 用户对象
     */
    public static User getUser() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        return user;
    }

    /**
     * 注销用户
     */
    public static void loginOut() {
        User user = (User) SecurityUtils.getSubject().getPrincipal();
        if(user != null) {
            SecurityUtils.getSubject().logout();
        }
    }
}
