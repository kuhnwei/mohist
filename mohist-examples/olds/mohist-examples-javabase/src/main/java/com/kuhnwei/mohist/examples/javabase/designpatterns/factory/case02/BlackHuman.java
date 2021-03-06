package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case02;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:30
 */
public class BlackHuman implements Human {
    @Override
    public void laugh() {
        System.out.println("黑人会大笑");
    }

    @Override
    public void cry() {
        System.out.println("黑人会大哭");
    }

    @Override
    public void talk() {
        System.out.println("黑人会说话");
    }
}
