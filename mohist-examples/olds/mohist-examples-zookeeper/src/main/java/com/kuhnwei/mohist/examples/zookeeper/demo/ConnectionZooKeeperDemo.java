package com.kuhnwei.mohist.examples.zookeeper.demo;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

import java.util.List;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 16:27
 */
public class ConnectionZooKeeperDemo {
    public static void main(String[] args) throws Exception {
        String connectString = "118.24.19.196:2181";
        int sessionTimeout = 2000;
        ZooKeeper zkClient = new ZooKeeper(connectString, sessionTimeout, new Watcher() {
            @Override
            public void process(WatchedEvent event) {
                System.out.println("[监听事件处理] path = " + event.getPath() + "、type = " + event.getType() + "、state = " + event.getState());
            }
        });
        zkClient.addAuthInfo("digest", "zkuser:650901".getBytes());
        byte[] data = zkClient.getData("/mohist", false, new Stat());
        System.out.println(new String(data));
        zkClient.close();
    }
}
