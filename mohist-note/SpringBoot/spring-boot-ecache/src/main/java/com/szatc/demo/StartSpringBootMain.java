package com.szatc.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/6/11 8:43
 **/
@SpringBootApplication
@EnableCaching
public class StartSpringBootMain {
    public static void main(String[] args) {
        SpringApplication.run(StartSpringBootMain.class, args);
    }
}
