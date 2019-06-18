package mohist.examples.spring.springboot.web;

import mohist.examples.spring.springboot.service.MqttMessageGateway;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.integration.mqtt.support.MqttHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@RestController
public class MqttController {

    @Resource
    private MqttMessageGateway gateway;

    @PostMapping(value = "sendToMqtt")
    public ResponseEntity<String> send(String message) {
        Message<String> msg = MessageBuilder.withPayload(message).setHeader(MqttHeaders.TOPIC, "mqtt-test").build();
        this.gateway.sendMessage(msg);
        return new ResponseEntity<>("ok", HttpStatus.OK);
    }

}
