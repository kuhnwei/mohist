package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.yellowhuman;

import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.Human;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:05
 */
public abstract class AbstractYellowHuman implements Human {
    @Override
    public void cry() {
        System.out.println("黄色人种会哭");
    }

    @Override
    public void laugh() {
        System.out.println("黄色人种会笑");
    }

    @Override
    public void talk() {
        System.out.println("黄色人种会说话");
    }
}
