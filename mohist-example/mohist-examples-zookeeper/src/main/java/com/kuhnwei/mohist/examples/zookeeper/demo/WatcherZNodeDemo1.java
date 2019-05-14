package com.kuhnwei.mohist.examples.zookeeper.demo;

import org.apache.zookeeper.*;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 17:51
 */
public class WatcherZNodeDemo1 {
    public static final String CONNECTS = "192.168.127.134:2181,192.168.127.135:2181,192.168.127.136:2181";
    public static final int SESSION_TIMEOUT = 2000;
    public static final String SCHEME = "digest";
    public static final String AUTH_INFO = "zkuser:650901";
    public static final String GROUP_NODE = "/mohist";
    private static ZooKeeper zkClient = null;
    public static void main(String[] args) throws Exception {
        zkClient = new ZooKeeper(CONNECTS, SESSION_TIMEOUT, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("[监听事件处理] path = " + event.getPath() + "、type = " + event.getType() + "、state = " + event.getState());
                try {
                    zkClient.exists(GROUP_NODE, true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        zkClient.addAuthInfo(SCHEME, AUTH_INFO.getBytes());
        zkClient.exists(GROUP_NODE, true);
        System.in.read();
        zkClient.close();
    }
}
