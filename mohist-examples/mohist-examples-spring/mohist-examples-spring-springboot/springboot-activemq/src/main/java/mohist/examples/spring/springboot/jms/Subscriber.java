package mohist.examples.spring.springboot.jms;

import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Slf4j
@Component
public class Subscriber {

    @JmsListener(destination = "topic-A", containerFactory = "jmsTopicListenerContainerFactory")
    public void topicA(Object message) {
        if (message instanceof ActiveMQTextMessage) {
            try {
                log.info("[ topicA ] message: {}", ((ActiveMQTextMessage) message).getText());
            } catch (JMSException e) {
                log.error("[ topicA ] error message: {}", e.getMessage());
            }
        }
    }

    @JmsListener(destination = "queue-A", containerFactory = "jmsQueueListenerContainerFactory")
    public void queueA(Object message) {
        if (message instanceof ActiveMQTextMessage) {
            try {
                log.info("[ queueA ] message: {}", ((ActiveMQTextMessage) message).getText());
            } catch (JMSException e) {
                log.error("[ queueA ] error message: {}", e.getMessage());
            }
        }
    }

    @JmsListener(destination = "queue-B", containerFactory = "jmsQueueListenerContainerFactory")
    public void queueB(Object message) {
        if (message instanceof ActiveMQTextMessage) {
            try {
                log.info("[ queueB ] message: {}", ((ActiveMQTextMessage) message).getText());
            } catch (JMSException e) {
                log.error("[ queueB ] error message: {}", e.getMessage());
            }
        }
    }

}
