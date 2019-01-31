package wx.milk.service.dubbo;

import com.alibaba.dubbo.config.annotation.Service;
import wx.milk.api.server.ITestDubboProvider;

import javax.servlet.http.HttpServletRequest;

@Service
public class DubboTest implements ITestDubboProvider {

    @Override
    public String getStr(String val) {
        try{
            Thread.sleep(1000);
            return val;
        }catch (InterruptedException e) {
                e.printStackTrace();
        }
        return "";
    }
}
