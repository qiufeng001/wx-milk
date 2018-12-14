package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import wx.milk.api.server.ITestDubboProvider;

@Service
public class DubboTest implements ITestDubboProvider {

    @Override
    public String getStr(String val) {
        return val;
    }
}
