package com.kuhnwei.mohist.examples.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 20:46
 */
public class TestCountDownLatch {
    public static void main(String[] args) throws InterruptedException {
        // 表示当前启动的线程数量
        CountDownLatch latch = new CountDownLatch(2);
        // 将CountDownLatch对象传递给MyThread类
        MyThread mt = new MyThread(latch);
        new Thread(mt).start();
        new Thread(mt).start();
        // 等待
        latch.await();
        System.out.println("[程序执行完毕]");
    }
}
class MyThread implements Runnable {
    private CountDownLatch latch;

    public MyThread(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + "执行。");
        // 向下减
        latch.countDown();
    }
}
