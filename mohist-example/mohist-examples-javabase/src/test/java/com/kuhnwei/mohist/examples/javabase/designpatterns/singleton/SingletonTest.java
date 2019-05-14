package com.kuhnwei.mohist.examples.javabase.designpatterns.singleton;

import org.junit.Test;

/**
 * 单例设计模式测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:07
 */
public class SingletonTest {

    @Test
    public void print() {
        Singleton singleton = Singleton.getInstance();
        singleton.print();
    }

}
