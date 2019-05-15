package com.kuhnwei.mohist.examples.zookeeper.demo;

import org.apache.zookeeper.*;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 16:27
 */
public class ConnectionZooKeeperDemo4 {
    public static final String CONNECTS = "192.168.127.134:2181,192.168.127.135:2181,192.168.127.136:2181";
    public static final int SESSION_TIMEOUT = 2000;
    public static final String SCHEME = "digest";
    public static final String AUTH_INFO = "zkuser:650901";
    public static final String GROUP_NODE = "/mohist";
    public static final String CHILDREN_NODE = GROUP_NODE + "/cluster-";
    public static void main(String[] args) throws Exception {
        ZooKeeper zkClient = new ZooKeeper(CONNECTS, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("[监听事件处理] path = " + event.getPath() + "、type = " + event.getType() + "、state = " + event.getState());
            }
        });
        zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        if (zkClient.exists(GROUP_NODE, false) == null) {
            zkClient.create(GROUP_NODE, "HelloData".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        for (int i = 0; i < 10; i ++) {
            if (zkClient.exists(CHILDREN_NODE, false) == null) {
                String createPath = zkClient.create(CHILDREN_NODE, "HelloChildren".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
                System.out.println("新的节点路径" + createPath);
            }
        }
        System.out.print("按回车结束本次运行：");
        System.in.read();
        zkClient.close();
    }
}
