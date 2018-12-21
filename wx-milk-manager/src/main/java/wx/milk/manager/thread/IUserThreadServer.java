package wx.milk.manager.thread;

import com.framework.core.security.User;
import com.framework.manager.IManager;


/**
 * auther: kiven on 2018/9/8/008 23:31
 * try it bast!
 */
public interface IUserThreadServer extends IManager<User, String> {

    /**
     * 设置用户在线
     */
    void setIsOnline();

    /**
     * 设置用户下线
     */
    void setIsOffline();
}
