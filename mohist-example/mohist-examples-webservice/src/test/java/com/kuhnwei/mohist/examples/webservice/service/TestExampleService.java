package com.kuhnwei.mohist.examples.webservice.service;

import com.kuhnwei.mohist.examples.webservice.service.impl.ExampleServiceImpl;
import org.junit.Test;

import javax.xml.ws.Endpoint;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 13:39
 */
public class TestExampleService {
    @Test
    public void publish() {
        String address = "http://127.0.0.1:8080/webservice/example";
        Endpoint.publish(address, new ExampleServiceImpl());
        System.out.println("发布成功...");
        try {
            Thread.sleep(Long.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
