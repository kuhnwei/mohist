package mohist.examples.spring.springboot.config;

import mohist.examples.spring.springboot.domain.Mqtt;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.messaging.MessageChannel;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/18
 **/
@Configuration
public class MqttConfig {

    @Autowired
    private MohistProperties mohist;

    @Bean
    public MqttPahoClientFactory mqttPahoClientFactory() {
        Mqtt mqtt = mohist.getMqtt();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(mqtt.getServerUris());
        options.setUserName(mqtt.getUsername());
        options.setPassword(mqtt.getPassword().toCharArray());
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        factory.setConnectionOptions(options);
        return factory;
    }

    @Bean
    public MessageChannel mqttOutboundChannel() {
        return new DirectChannel();
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    public MqttPahoMessageHandler mqttOutbound(MqttPahoClientFactory factory) {
        MqttPahoMessageHandler handler = new MqttPahoMessageHandler("springboot-mqtt", factory);
        handler.setAsync(true);
        handler.setDefaultQos(2);
        handler.setDefaultRetained(false);
        handler.setAsyncEvents(false);
        return handler;
    }
}
