package mohist.examples.spring.springboot.config;

import lombok.extern.slf4j.Slf4j;
import mohist.examples.spring.springboot.handler.ExampleWebSocketHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.web.socket.TextMessage;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Slf4j
@EnableAsync
@EnableScheduling
@Configuration
public class ScheduledConfig {

    @Autowired
    private ExampleWebSocketHandler exampleHandler;

    @Bean
    public TaskScheduler taskScheduler() {
        /*
         * SpringBoot WebSocket 模块导致  原先默认声明的 ThreadPoolTaskScheduler Bean 冲突
         * org.springframework.beans.factory.BeanNotOfRequiredTypeException: Bean named 'defaultSockJsTaskScheduler' is expected to be of type 'org.springframework.scheduling.TaskScheduler' but was actually of type 'org.springframework.beans.factory.support.NullBean'
         */
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(10);
        scheduler.initialize();
        return scheduler;
    }

    @Async
    @Scheduled(fixedRate = 10 * 1000)
    public void taskSendMessageToAll() {
        log.debug("[ taskSendMessageToAll ] every 10 seconds. ");
        this.exampleHandler.sendMessageToSessions(new TextMessage(System.currentTimeMillis() + ""));
    }
}
