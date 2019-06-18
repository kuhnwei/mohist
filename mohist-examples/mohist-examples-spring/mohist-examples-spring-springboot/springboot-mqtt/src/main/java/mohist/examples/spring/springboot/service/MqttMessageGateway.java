package mohist.examples.spring.springboot.service;

import org.springframework.integration.annotation.MessagingGateway;
import org.springframework.messaging.Message;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
public interface MqttMessageGateway {
    void sendMessage(Message<?> message);
}
