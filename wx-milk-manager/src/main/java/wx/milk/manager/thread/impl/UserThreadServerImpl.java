package wx.milk.manager.thread.impl;

/**
 * auther: kiven on 2018/9/8/008 23:31
 * try it bast!`
 */

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wx.base.service.IService;
import wx.base.manager.impl.BaseManager;
import wx.milk.manager.thread.IUserThreadServer;
import wx.milk.service.admin.IUserService;
import wx.security.User;
import wx.util.ShiroUtils;

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
