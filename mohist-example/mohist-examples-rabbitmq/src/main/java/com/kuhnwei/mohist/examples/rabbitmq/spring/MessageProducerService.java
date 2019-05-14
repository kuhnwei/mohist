package com.kuhnwei.mohist.examples.rabbitmq.spring;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 13:54
 */
@Service
public class MessageProducerService {

    @Autowired
    private AmqpTemplate amqpTemplate;

    public void send(String message) {
        String routingKey = "msg-key";
        amqpTemplate.convertAndSend(routingKey, message);
    }

}
