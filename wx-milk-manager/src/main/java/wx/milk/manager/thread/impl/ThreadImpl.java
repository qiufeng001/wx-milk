package wx.milk.manager.thread.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import wx.milk.manager.thread.IThread;

import javax.xml.ws.ServiceMode;

/**
 * auther: kiven on 2018/8/16/016 17:58
 * try it bast!
 */
@Service
public class ThreadImpl implements IThread {

    protected Log logger = LogFactory.getLog(getClass());

    @Override
    @Async("asyncServiceExecutor")
    public void test(int count) {
        try {
            Thread.sleep(10000);
        }catch (InterruptedException e) {
            logger.error(e);
        }
        System.out.println("测试" + count);
    }
}
