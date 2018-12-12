package wx.milk.manager.admin;

import wx.base.manager.IManager;
import wx.security.User;


public interface IUserManager extends IManager<User, String> {

    /*
    * 根据shiro中的账号用户登录信息，获取用户账号，再用账号获取用户信息
    * @return 用户对象
    */
    User getByAccount(String account);

    /**
     * 获取在线人数
     * @return
     */
    Integer getOnlineNumber();
}
