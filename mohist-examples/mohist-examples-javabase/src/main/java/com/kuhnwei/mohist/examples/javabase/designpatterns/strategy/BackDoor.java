package com.kuhnwei.mohist.examples.javabase.designpatterns.strategy;

/**
 * 找乔国老帮忙，使孙权不能杀刘备
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 15:58
 */
public class BackDoor implements IStrategy {
    @Override
    public void operate() {
        System.out.println("找乔国老帮忙，让吴国太给孙权施压");
    }
}
