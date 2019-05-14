package com.kuhnwei.mohist.examples.javabase.designpatterns.proxy;

/**
 * 主题核心操作
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:01
 */
public class RealSubject implements Subject {
    @Override
    public void get() {
        System.out.println("[主题核心操作] --->>> ......");
    }
}
