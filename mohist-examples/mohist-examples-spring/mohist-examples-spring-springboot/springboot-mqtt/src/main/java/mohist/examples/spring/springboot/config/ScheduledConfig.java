package mohist.examples.spring.springboot.config;

import lombok.extern.slf4j.Slf4j;
import mohist.examples.spring.springboot.service.MqttMessageGateway;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
public class ScheduledConfig {

    @Resource
    private MqttMessageGateway gateway;

    @Async
    @Scheduled(cron = "0 0 * * * ?")
    public void taskSendSms() {
        String datetime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        String str = "[ " + datetime + " ] 测试：通过MQTT协议 给APP推送消息， 并发送短信。";
        Message<String> message = MessageBuilder.withPayload(str)
                .setHeader(MqttHeaders.TOPIC, "mqtt-test")
                .build();
        this.gateway.sendMessage(message);
        log.debug("[ taskSendSms ] message: {}", message);
    }
}
