package wx.milk.web.controller.test.threadpool;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import wx.milk.manager.thread.IThread;

/**
 * auther: kiven on 2018/8/16/016 17:56
 * try it bast!
 */
@Controller
@RequestMapping("/login")
public class ThreadPoolExecutorController {

    protected Log logger = LogFactory.getLog(getClass());

    @Autowired
    private IThread thread;

    @RequestMapping("/pool")
    @ResponseBody
    public String testPool() {
        /*for(int i = 0;i<100;i++) {
            thread.test(i);
        }*/
        thread.test(100);
        return "success";
    }
}
