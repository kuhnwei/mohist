package com.szatc.demo.service;

import com.szatc.demo.BaseTest;
import com.szatc.demo.domain.Example;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/6/11 9:25
 **/
public class ExampleServiceTest extends BaseTest {
    @Resource
    private ExampleService exampleService;

    @Test
    public void save() {
        for (long i = 1; i <= 10; i ++) {
            this.exampleService.save(new Example(i, "" + System.currentTimeMillis()));
        }
    }

    @Test
    public void remove() {
        for (long i = 1; i <= 10; i ++) {
            this.exampleService.save(new Example(i, "" + System.currentTimeMillis()));
        }
        this.exampleService.remove(7L);
    }

    @Test
    public void get() throws InterruptedException {
        for (long i = 1; i <= 10; i ++) {
            this.exampleService.save(new Example(i, "" + System.currentTimeMillis()));
        }
        for (int j = 0; j < 10; j ++) {
            System.out.println(this.exampleService.get(8L));
            Thread.sleep(10000);
        }
    }
}
