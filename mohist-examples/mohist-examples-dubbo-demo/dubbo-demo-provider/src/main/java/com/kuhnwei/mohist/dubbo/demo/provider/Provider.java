package com.kuhnwei.mohist.dubbo.demo.provider;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 22:17
 */
public class Provider {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"provider.xml"});
        context.start();
        System.in.read();
    }
}
