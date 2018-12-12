package wx.milk.web.controller.test.thread;

import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;

/**
 * auther: kiven on 2018/8/15/015 22:38
 * try it bast!
 */
public class ProduceAndConsume implements Runnable {

    @Override
    public void run() {
        WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();
        String projectId = commonJedis.get("projectId");
        try {
            Thread.sleep(5000);
            System.out.println(projectId);
            System.out.println("多线程！");

        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
