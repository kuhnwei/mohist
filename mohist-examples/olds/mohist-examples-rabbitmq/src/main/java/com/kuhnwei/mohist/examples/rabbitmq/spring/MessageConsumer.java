package com.kuhnwei.mohist.examples.rabbitmq.spring;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.utils.SerializationUtils;

import java.nio.charset.Charset;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 11:27
 */
public class MessageConsumer implements MessageListener {

    private static final String ENCODING = Charset.defaultCharset().name();

    @Override
    public void onMessage(Message message) {
        System.out.println(">> message = [" + getBodyContentAsString(message.getBody(), message.getMessageProperties()) + "]");
    }

    private String getBodyContentAsString(byte[] body, MessageProperties messageProperties) {
        if (body == null) {
            return null;
        } else {
            try {
                String contentType = messageProperties != null ? messageProperties.getContentType() : null;
                if ("application/x-java-serialized-object".equals(contentType)) {
                    return SerializationUtils.deserialize(body).toString();
                }

                if ("text/plain".equals(contentType) || "application/json".equals(contentType) || "application/xml".equals(contentType)) {
                    return new String(body, ENCODING);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return toString() + "(byte[" + body.length + "])";
        }
    }
}
