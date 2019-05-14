package com.kuhnwei.mohist.dubbo.demo.consumer;

import com.kuhnwei.mohist.dubbo.demo.DemoService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 22:21
 */
public class Consumer {
    public static void main(String[] args) throws InterruptedException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"consumer.xml"});
        DemoService demoService = (DemoService) context.getBean("demoService");
        while (true) {
            Thread.sleep(1000);
            System.out.println(demoService.sayHello("KuhnWei" + Math.random()));
        }
    }
}
