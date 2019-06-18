package mohist.examples.spring.springboot.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Slf4j
@Component
public class Publisher {

    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    public void setJmsMessagingTemplate(JmsMessagingTemplate jmsMessagingTemplate) {
        this.jmsMessagingTemplate = jmsMessagingTemplate;
    }

    public void publish(String destName, Object message) {
        JmsTemplate jmsTemplate = this.jmsMessagingTemplate.getJmsTemplate();
        assert jmsTemplate != null;
        jmsTemplate.setPubSubDomain(true);
        ActiveMQTopic topic = new ActiveMQTopic(destName);
        jmsMessagingTemplate.convertAndSend(topic, message);
        log.debug("[ publish ] destName: {}, message: {}", destName, message);
    }

    public void publishByQueue(String destName, Object message) {
        JmsTemplate jmsTemplate = this.jmsMessagingTemplate.getJmsTemplate();
        assert jmsTemplate != null;
        jmsTemplate.setPubSubDomain(false);
        ActiveMQQueue queue = new ActiveMQQueue(destName);
        jmsMessagingTemplate.convertAndSend(queue, message);
        log.debug("[ publish ] destName: {}, message: {}", destName, message);
    }
}
