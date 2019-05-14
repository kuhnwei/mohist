package com.kuhnwei.mohist.examples.rabbitmq.spring;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 13:55
 */
@ContextConfiguration(value = {"classpath:spring-consumer.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
public class MessageConsumerTest {

    @Test
    public void receive() {
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
