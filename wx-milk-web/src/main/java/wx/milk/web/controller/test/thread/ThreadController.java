package wx.milk.web.controller.test.thread;

import com.framework.core.redis.WxJedisCommands;
import com.framework.core.redis.WxRedisClient;
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

    @RequestMapping(value = "/test")
    @ResponseBody
    public void test() {
        WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();
        commonJedis.set("projectId", "123");
        ProduceAndConsume p = new ProduceAndConsume();
        Thread t = new Thread(p);
        t.start();
        System.out.println("单线程！");
    }
}
