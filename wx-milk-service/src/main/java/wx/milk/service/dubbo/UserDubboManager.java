package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import wx.milk.api.server.IUserDubboProvider;
import wx.milk.service.admin.IUserService;
import wx.query.Query;
import wx.security.User;

@Service
public class UserDubboManager implements IUserDubboProvider {

    @Autowired
    private IUserService userService;

    @Override
    public User findByParam(Query query) {
        return userService.findByParam(query);
    }
}
