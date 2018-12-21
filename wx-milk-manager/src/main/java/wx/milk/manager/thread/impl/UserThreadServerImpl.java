package wx.milk.manager.thread.impl;

/**
 * auther: kiven on 2018/9/8/008 23:31
 * try it bast!`
 */

import com.framework.core.security.User;
import com.framework.core.util.ShiroUtils;
import com.framework.manager.impl.BaseManager;
import com.framework.service.IService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wx.milk.manager.thread.IUserThreadServer;
import wx.milk.service.admin.IUserService;

@Service
public class UserThreadServerImpl extends BaseManager<User, String> implements IUserThreadServer {

    @Autowired
    private IUserService service;

    @Override
    protected IService<User, String> getService() {
        return service;
    }

    @Override
    @Async("asyncServiceExecutor")
    public void setIsOnline() {
        User user = ShiroUtils.getUser();
        user.setIsOnline((short) 1);
        service.update(user);
    }

    @Override
    @Async("asyncServiceExecutor")
    public void setIsOffline() {
        User user = ShiroUtils.getUser();
        user.setIsOnline((short) 0);
        service.update(user);
    }
}
