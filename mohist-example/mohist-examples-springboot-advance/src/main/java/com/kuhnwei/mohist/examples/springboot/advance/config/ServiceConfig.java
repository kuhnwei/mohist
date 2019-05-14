package com.kuhnwei.mohist.examples.springboot.advance.config;

import com.kuhnwei.mohist.examples.springboot.advance.service.impl.MessageServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 16:41
 */
@Configuration
public class ServiceConfig {

    // 此处返回的是一个Spring的配置Bean,与xml的“<bean>”等价
    @Bean
    public MessageServiceImpl getMessageService() {
        return new MessageServiceImpl();
    }

}
