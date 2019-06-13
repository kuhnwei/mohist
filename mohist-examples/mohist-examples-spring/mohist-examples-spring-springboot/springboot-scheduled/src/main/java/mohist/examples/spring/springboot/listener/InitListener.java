package mohist.examples.spring.springboot.listener;

import lombok.extern.slf4j.Slf4j;
import mohist.examples.spring.springboot.config.ScheduledConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * @author Kuhn, email@kuhnwei.com
 * @date 2019/6/13
 **/
@Slf4j
@Component
public class InitListener implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private ScheduledConfig scheduledConfig;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        log.info("[ onApplicationEvent ]");
        this.scheduledConfig.taskC();
    }
}
