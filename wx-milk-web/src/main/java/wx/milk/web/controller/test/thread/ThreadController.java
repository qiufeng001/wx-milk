package wx.milk.web.controller.test.thread;

import com.framework.core.redis.WxJedisCommands;
import com.framework.core.redis.WxRedisClient;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * auther: kiven on 2018/8/16/016 0:10
 * try it bast!
 */
@RequestMapping(value = "/thread/*")
@Controller
public class ThreadController {
    protected Log logger = LogFactory.getLog(this.getClass());

    @RequestMapping(value = "/test")
    @ResponseBody
    public String test() {
        try{
            int i = 1 / 0;
            return "1111";
        }catch (Exception e) {
            logger.error(e.getMessage());
            return "false";
        }
       /* WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();
        commonJedis.set("projectId", "123");
        ProduceAndConsume p = new ProduceAndConsume();
        Thread t = new Thread(p);
        t.start();
        System.out.println("单线程！");*/


    }
}
