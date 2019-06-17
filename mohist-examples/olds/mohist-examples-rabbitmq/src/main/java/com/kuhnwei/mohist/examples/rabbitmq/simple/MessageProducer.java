package com.kuhnwei.mohist.examples.rabbitmq.simple;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息生产者
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 9:21
 */
public class MessageProducer {

    private static final String EXCHANGE_NAME = "examples.msg.topic01";

    private static final String HOST = "118.24.19.196";

    private static final Integer PORT = 5672;

    private static final String USERNAME = "mquser";

    private static final String PASSWORD = "650901";

    private static final String VIRTUAL_HOST = "mohist";

    private static final String TYPE = "topic";

    public static void main(String[] args) throws IOException, TimeoutException {
        // 建立一个连接工厂, 所有的连接信息在此配置
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(HOST);
        factory.setPort(PORT);
        factory.setUsername(USERNAME);
        factory.setPassword(PASSWORD);
        factory.setVirtualHost(VIRTUAL_HOST);

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, TYPE);
        send(channel, EXCHANGE_NAME);
        channel.close();
        connection.close();
    }

    public static void send(Channel channel,String exchange) throws IOException {
        long start = System.currentTimeMillis();
        for (int x = 0; x < 100; x ++) {
            String msg = "RabbitMQ - " + x;
            if (x % 2 == 0) {
                channel.basicPublish(exchange, "msg-key-1", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            } else {
                channel.basicPublish(exchange, "msg-key-2", MessageProperties.PERSISTENT_TEXT_PLAIN, msg.getBytes());
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("本次操作所花费的时间：" + (end - start));
    }

}
