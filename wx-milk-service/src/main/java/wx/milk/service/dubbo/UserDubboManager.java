package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import com.framework.core.query.Query;
import com.framework.core.security.User;
import org.springframework.beans.factory.annotation.Autowired;
import wx.milk.api.server.IUserDubboProvider;
import wx.milk.service.admin.IUserService;


@Service
public class UserDubboManager implements IUserDubboProvider {

    @Autowired
    private IUserService userService;

    @Override
    public User findByParam(Query query) {
        return userService.findByParam(query);
    }
}
