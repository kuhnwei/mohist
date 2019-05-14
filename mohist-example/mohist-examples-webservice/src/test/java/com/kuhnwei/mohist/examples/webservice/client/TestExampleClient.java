package com.kuhnwei.mohist.examples.webservice.client;

import org.junit.Test;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 13:47
 */
public class TestExampleClient {

    @Test
    public void client() {
        ExampleFacotry facotry = new ExampleFacotry();
        ExampleService service = facotry.getExampleServiceInstance();
        String result = service.print("message:" + System.currentTimeMillis());
        System.out.println(result);
    }

}
