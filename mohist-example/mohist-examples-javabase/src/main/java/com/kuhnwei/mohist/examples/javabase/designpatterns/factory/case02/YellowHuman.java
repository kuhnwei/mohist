package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case02;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:28
 */
public class YellowHuman implements Human {
    @Override
    public void laugh() {
        System.out.println("黄种人会大笑");
    }

    @Override
    public void cry() {
        System.out.println("黄种人会哭");
    }

    @Override
    public void talk() {
        System.out.println("黄种人会说话");
    }
}
