package com.kuhnwei.mohist.examples.zookeeper.lock;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 21:15
 */
public class LockThread implements Runnable {
    private DistributedLock lock;

    public LockThread(int threadId, CountDownLatch latch) throws Exception {
        this.lock = new DistributedLock(threadId, latch);
    }

    @Override
    public void run() {
        try {
            // 每个线程对象启动之后都应该创建有一个临时的节点数据
            this.lock.createChildrenNode();
            this.lock.handle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
class TestDistributedLock {
    public static void main(String[] args) throws Exception {
        CountDownLatch latch = new CountDownLatch(10);
        for (int i = 0; i < 10; i ++) {
            new Thread(new LockThread(i, latch)).start();
        }
        latch.await();
        System.out.println("[所有的线程对象操作完毕]");
    }
}
