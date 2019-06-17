package com.kuhnwei.mohist.examples.javabase.designpatterns.proxy;

import org.junit.Test;

/**
 * 代理设计模式测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:03
 */
public class ProxyTest {

    @Test
    public void proxy() {
        Subject subject = new SubjectProxy(new RealSubject());
        subject.get();
    }

}
