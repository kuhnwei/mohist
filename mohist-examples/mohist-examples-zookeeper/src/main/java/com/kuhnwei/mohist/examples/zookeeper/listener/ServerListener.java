package com.kuhnwei.mohist.examples.zookeeper.listener;

import org.apache.zookeeper.*;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 18:25
 */
public class ServerListener {
    /**
     * zookeeper连接地址
     */
    public static final String CONNECTION_URL = "192.168.127.134:2181,192.168.127.135:2181,192.168.127.136:2181";

    /**
     * 连接超时时间
     */
    public static final int SESSION_TIMEOUT = 3000;

    /**
     * zookeeper授权方式
     */
    public static final String SCHEME = "digest";

    /**
     * zookeeper授权信息
     */
    public static final String AUTH_INFO = "zkuser:650901";

    /**
     * 父节点
     */
    public static final String GROUP_NODE = "/mohist-server";

    /**
     * 子节点
     */
    public static final String CHILDREN_NODE = GROUP_NODE + "/server-";

    /**
     * zookeeper客户端处理对象
     */
    private ZooKeeper zkClient;

    public ServerListener(String serverName) throws Exception {
        // zookeeper连接控制，同时进行各个节点的而创建
        this.connectZooKeeperServer(serverName);
        // 连接准备好之后，要自动进行制定任务的调用
        this.handle();
    }

    /**
     * 定义真正要实现的一些具体的操作业务代码。
     * 例如：在服务器启动的时候又可能要通过某些系统记录出服务器的启动次数
     * @throws Exception
     */
    public void handle() throws Exception {
        System.out.print("[按回车结束]");
        System.in.read();
        this.zkClient.close();
    }

    /**
     * zookeeper连接处理
     * @param serverName 服务器名称
     * @throws Exception Exception
     */
    public void connectZooKeeperServer(String serverName) throws Exception {
        this.zkClient = new ZooKeeper(CONNECTION_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent watchedEvent) {
            }
        });
        this.zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        if (this.zkClient.exists(GROUP_NODE, false) == null) {
            this.zkClient.create(GROUP_NODE, "SERVER-LIST".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        if (this.zkClient.exists(CHILDREN_NODE, false) == null) {
            this.zkClient.create(CHILDREN_NODE, serverName.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);
        }
    }
}
