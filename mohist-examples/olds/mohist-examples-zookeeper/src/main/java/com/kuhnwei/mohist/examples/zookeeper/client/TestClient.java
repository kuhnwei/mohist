package com.kuhnwei.mohist.examples.zookeeper.client;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 19:34
 */
public class TestClient {
    public static void main(String[] args) throws Exception {
        ServerListClientListener client = new ServerListClientListener();
        System.in.read();
        client.close();
    }
}
