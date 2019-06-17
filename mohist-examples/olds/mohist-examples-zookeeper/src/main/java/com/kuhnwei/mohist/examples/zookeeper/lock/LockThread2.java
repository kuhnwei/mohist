package com.kuhnwei.mohist.examples.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 21:15
 */
public class LockThread2 implements Runnable {
    private DistributedLock2 lock2;

    public LockThread2(int threadId, CountDownLatch latch) throws Exception {
        this.lock2 = new DistributedLock2(threadId, latch);
    }

    @Override
    public void run() {
        try {
            this.lock2.handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

class TestDistributedLock2 {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i ++) {
            new Thread(new LockThread2(i, latch)).start();
        }
        latch.await();
        System.out.println("[所有的线程对象操作完毕]");
    }
}
