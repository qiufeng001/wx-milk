package wx.milk.manager.dubbo.server;

import com.alibaba.dubbo.config.annotation.Service;
import wx.milk.api.server.IAdministratorDubboProvider;
import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;

@Service
public class AdministratorDubboProvider implements IAdministratorDubboProvider {

    @Override
    public WxJedisCommands getCommonJedis() {
        return WxRedisClient.getCommonJedis();
    }
}
