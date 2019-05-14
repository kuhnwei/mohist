package com.kuhnwei.mohist.examples.springboot.advance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 17:48
 */
@SpringBootApplication
@ImportResource(locations = {"classpath*:spring-common.xml"})
public class StartSpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(StartSpringBootMain.class, args);
    }
}
