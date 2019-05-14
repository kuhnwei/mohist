package com.kuhnwei.mohist.examples.rabbitmq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 13:56
 */
@ContextConfiguration(value = {"classpath:spring-producer.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageProducerTest {

    @Autowired
    private MessageProducerService messageService;

    @Test
    public void send() {
        messageService.send("... 消息发送测试 --- " + System.currentTimeMillis());
    }

}
