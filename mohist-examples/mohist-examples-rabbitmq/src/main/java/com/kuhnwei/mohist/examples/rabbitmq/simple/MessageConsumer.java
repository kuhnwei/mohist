package com.kuhnwei.mohist.examples.rabbitmq.simple;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * 消息消费者
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 9:21
 */
public class MessageConsumer {

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

        receive(channel, EXCHANGE_NAME);

    }

    public static void receive(Channel channel, String exchange) throws IOException {
        // 通过通道获取队列名称
        String queueName = channel.queueDeclare().getQueue();
        // 进行绑定处理
        channel.queueBind(queueName, exchange, "msg-key-1");

        // 在RabbitMQ里面所有的消费者的信息时通过一个回调函数完成的
        Consumer consumer = new DefaultConsumer(channel) {

            // 需要覆写指定的方法实现消息消接收处理
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                String message = new String(body);
                System.out.println("-->> [消息 msg-key-1]" + message);
            }
        };
        // 消息消费
        channel.basicConsume(queueName, consumer);
    }

}
