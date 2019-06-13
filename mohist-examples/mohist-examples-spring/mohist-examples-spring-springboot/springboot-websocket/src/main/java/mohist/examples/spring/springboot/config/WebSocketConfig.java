package mohist.examples.spring.springboot.config;

import lombok.extern.slf4j.Slf4j;
import mohist.examples.spring.springboot.handler.ExampleWebSocketHandler;
import mohist.examples.spring.springboot.interceptor.WebSocketInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Slf4j
@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    @Autowired
    private ExampleWebSocketHandler exampleHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(exampleHandler, "/ws/example")
                .addInterceptors(new WebSocketInterceptor())
                .setAllowedOrigins("*");
    }
}
