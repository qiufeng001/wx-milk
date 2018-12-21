package com.test.thread;


import java.util.concurrent.CountDownLatch;

/**
 * auther: kiven on 2018/8/15/015 23:10
 * try it bast!
 */
public class ThreadTest {
    public static void main(String[] args) {
        final CountDownLatch latch = new CountDownLatch(1);
        new Thread() {
            public void run() {
                try {
                    System.out.println("1-0子线程" + Thread.currentThread().getName() + "正在执行");
                    latch.countDown();
                    Thread.sleep(3000);
                    System.out.println("1-1-子线程" + Thread.currentThread().getName() + "执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();

        new Thread() {

            public void run() {
                try {
                    System.out.println("2-0-子线程" + Thread.currentThread().getName() + "正在执行");
                    latch.countDown();
                    Thread.sleep(3000);
                    System.out.println("2-1-子线程" + Thread.currentThread().getName() + "执行完毕");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            ;
        }.start();

        try {
            latch.await();
            System.out.println("等待2个子线程执行完毕...");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
