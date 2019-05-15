package com.kuhnwei.mohist.examples.zookeeper.client;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 19:25
 */
public class ServerListClientListener {
    /**
     * 进行计数统计
     */
    public static int count = 0;
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
     * zookeeper客户端处理对象
     */
    private ZooKeeper zkClient;

    public ServerListClientListener() throws Exception {
        this.connectZooKeeperServer();
        System.out.println("[第 " + (count ++) + " 次取得服务列表] " + ServerListClientListener.this.updateServerList());
    }

    /**
     * 编写一个方法实现所有的服务端信息列表的更新获得
     * @return 最新的服务列表信息集合
     * @throws Exception
     */
    public Set<String> updateServerList() throws Exception {
        Set<String> allServers = new LinkedHashSet<>();
        List<String> children = this.zkClient.getChildren(GROUP_NODE, true);
        for (String c : children) {
            String path = GROUP_NODE + "/" + c;
            allServers.add(new String(this.zkClient.getData(path, false, new Stat())));
        }
        return allServers;
    }

    /**
     * zookeeper连接处理
     * @throws Exception Exception
     */
    public void connectZooKeeperServer() throws Exception {
        this.zkClient = new ZooKeeper(CONNECTION_URL, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                if (event.getPath() != null) {
                    if (event.getType() == Event.EventType.NodeChildrenChanged) {
                        try {
                            System.out.println("[第 " + (count ++) + " 次取得服务列表] " + ServerListClientListener.this.updateServerList());
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        this.zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        if (this.zkClient.exists(GROUP_NODE, false) == null) {
            this.zkClient.create(GROUP_NODE, "SERVER-LIST".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
    }

    public void close() throws InterruptedException {
        this.zkClient.close();
    }
}
