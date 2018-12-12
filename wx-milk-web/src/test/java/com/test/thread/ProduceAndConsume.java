package com.test.thread;

import wx.redis.WxJedisCommands;
import wx.redis.WxRedisClient;

import java.util.HashMap;
import java.util.Map;

/**
 * auther: kiven on 2018/8/15/015 22:38
 * try it bast!
 */
public class ProduceAndConsume implements Runnable {

    private int a;


    @Override
    public void run() {
        WxJedisCommands commonJedis = WxRedisClient.getCommonJedis();
        String projectId = commonJedis.get("projectId");
        try {
            System.out.println("projectId");
            System.out.println("多线程！");
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
