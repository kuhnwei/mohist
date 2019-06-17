package com.kuhnwei.mohist.examples.zookeeper.lock;

import org.apache.zookeeper.*;

import java.util.concurrent.CountDownLatch;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 21:03
 */
public class DistributedLock {
    public static final String CONNECTION_URL = "192.168.127.134:2181,192.168.127.135:2181,192.168.127.136:2181";
    public static final int SESSION_TIMEOUT = 3000;
    public static final String SCHEME = "digest";
    public static final String AUTH_INFO = "zkuser:650901";
    public static final String GROUP_NODE = "/mohist-lock";
    public static final String CHILDREN_NODE = GROUP_NODE + "/lockthread-";
    private CountDownLatch latch;
    private ZooKeeper zkClient;
    private String selfPath; // 保存每次创建的临时节点的路径信息
    private int threadId = 0;

    public DistributedLock(int threadId, CountDownLatch latch) throws Exception {
        this.threadId = threadId;
        this.latch = latch;
        this.connectZooKeeperServer();
    }

    public void handle() throws Exception {
        this.handlCallback(); // 执行具体的业务操作
        this.releaseZooKeeper(); // 释放掉当前的zookeeper连接信息
        this.latch.countDown(); // 进行减减的操作
    }

    public void handlCallback() {
        // 取得分布式锁之后的目的是要进行具体的业务操作
        System.out.println("[Thread - " + this.threadId + "]获得操作权，执行具体的业务处理操作。");
    }

    public void createChildrenNode() throws Exception {
        this.selfPath = this.zkClient.create(CHILDREN_NODE, ("Thread-" + this.threadId).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("[Thread - " + this.threadId + "]创建新的临时节点 " + this.selfPath);
    }

    public void connectZooKeeperServer() throws Exception {
        this.zkClient = new ZooKeeper(CONNECTION_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
        this.zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        if (this.zkClient.exists(GROUP_NODE, false) == null) {
            this.zkClient.create(GROUP_NODE, "LOCKDEMO".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void releaseZooKeeper() {
        if (this.zkClient != null) {
            try {
                this.zkClient.close();
                System.out.println("[Thread - " + this.threadId + "]销毁临时节点 " + this.selfPath);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
