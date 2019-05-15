package com.kuhnwei.mohist.examples.zookeeper.lock;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 21:03
 */
public class DistributedLock2 {
    public static final String CONNECTION_URL = "192.168.127.134:2181,192.168.127.135:2181,192.168.127.136:2181";
    public static final int SESSION_TIMEOUT = 3000;
    public static final String SCHEME = "digest";
    public static final String AUTH_INFO = "zkuser:650901";
    public static final String GROUP_NODE = "/mohist-lock";
    public static final String CHILDREN_NODE = GROUP_NODE + "/lockthread-";
    private CountDownLatch latch;
    // 本操作的主要目的是为了在取得zookeeper连接之后才能够进行后续处理
    private CountDownLatch connectLatch = new CountDownLatch(1);
    private ZooKeeper zkClient;
    private String selfPath; // 保存每次创建的临时节点的路径信息
    private String waitPath; // 保存下一个要处理的节点
    private int threadId = 0;

    public DistributedLock2(int threadId, CountDownLatch latch) throws Exception {
        this.threadId = threadId;
        this.latch = latch;
        this.connectZooKeeperServer();
    }

    public void handle() throws Exception {
        this.createChildrenNode();
    }

    public void handldSuccess() throws Exception {// 表示取得锁之后进行的处理
        if (this.zkClient.exists(this.selfPath, false) == null) {
            return;
        }
        this.handlCallback();
        this.zkClient.delete(this.selfPath, -1);
        this.releaseZooKeeper();
        this.latch.countDown();
    }

    public void handlCallback() throws InterruptedException {
        Thread.sleep(200);
        // 取得分布式锁之后的目的是要进行具体的业务操作
        System.out.println("[Thread - " + this.threadId + "]获得操作权，执行具体的业务处理操作。");
    }

    public void createChildrenNode() throws Exception {
        this.selfPath = this.zkClient.create(CHILDREN_NODE, ("Thread-" + this.threadId).getBytes(),
                ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        System.out.println("[Thread - " + this.threadId + "]创建新的临时节点 " + this.selfPath);
        if (this.checkMinPath()) {
            this.handldSuccess();
        }
    }

    public boolean checkMinPath() throws Exception { // 进行最小节点的判断
        List<String> children = this.zkClient.getChildren(GROUP_NODE, false);
        Collections.sort(children);
        int index = children.indexOf(this.selfPath.substring(GROUP_NODE.length() + 1));
        switch (index) {
            case 0 : {
                // 已经确定好当前的节点为最小节点
                return true;
            }
            case -1 : {
                // 该节点可能已经消失了
                return false;
            }
            default:{
                // 表示现在该节点不属于最小节点，那么久需要继续向后排查
                this.waitPath = GROUP_NODE + "/" + children.get(index - 1);
                try {
                    this.zkClient.getData(waitPath, true, new Stat());
                    return false;
                }catch (Exception e) {
                    if (this.zkClient.exists(this.waitPath, false) == null) {
                        return this.checkMinPath();
                    } else {
                        throw e;
                    }
                }
            }
        }
    }

    public void connectZooKeeperServer() throws Exception {
        this.zkClient = new ZooKeeper(CONNECTION_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getType() == Event.EventType.None) {
                    DistributedLock2.this.connectLatch.countDown();
                } else {
                    if (event.getType() == Event.EventType.NodeDeleted && event.getPath().equals(DistributedLock2.this.waitPath)) {
                        try {
                            if (DistributedLock2.this.checkMinPath()) {
                                DistributedLock2.this.handldSuccess();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        if (this.zkClient.exists(GROUP_NODE, false) == null) {
            this.zkClient.create(GROUP_NODE, "LOCKDEMO".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        this.connectLatch.await();
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
