package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import wx.milk.api.server.IAdministratorDubboProvider;

@Service
public class DubboTest implements IAdministratorDubboProvider {

    @Override
    public String getStr(String val) {
        return val;
    }
}
