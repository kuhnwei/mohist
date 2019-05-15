package com.kuhnwei.mohist.examples.javabase.designpatterns.strategy;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 15:59
 */
public class BlockEnemy implements IStrategy {
    @Override
    public void operate() {
        System.out.println("孙夫人断后，挡住追兵");
    }
}
