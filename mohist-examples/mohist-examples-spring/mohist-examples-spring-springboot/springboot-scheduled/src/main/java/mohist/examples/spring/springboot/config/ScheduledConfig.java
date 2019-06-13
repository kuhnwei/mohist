package mohist.examples.spring.springboot.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.TriggerContext;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import java.util.Date;
import java.util.concurrent.ScheduledFuture;

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
    private ThreadPoolTaskScheduler threadPoolTaskScheduler;

    private ScheduledFuture<?> scheduledFuture;

    @Async
    @Scheduled(cron = "0/5 * * * * ?")
    public void taskA() {
        log.info("[ taskA ] every 5 seconds. ");
    }

    @Async
    @Scheduled(fixedRate = 3000)
    public void taskB() {
        log.info("[ taskB ] every 3 seconds. ");
    }

    public void taskC() {
        if (!this.isDone()) {
            return;
        }
        this.scheduledFuture = this.threadPoolTaskScheduler.schedule(new Runnable() {
            @Override
            public void run() {
                log.info("[ taskC ] in a seconds. ");
            }
        }, new Trigger() {
            @Override
            public Date nextExecutionTime(TriggerContext triggerContext) {
                /*
                 * 控制定时器 下一次执行的时间
                 */
                CronTrigger trigger = new CronTrigger("* * * * * ?");
                return trigger.nextExecutionTime(triggerContext);
            }
        });
    }

    private boolean isDone() {
        return this.scheduledFuture == null || this.scheduledFuture.isDone();
    }

}
