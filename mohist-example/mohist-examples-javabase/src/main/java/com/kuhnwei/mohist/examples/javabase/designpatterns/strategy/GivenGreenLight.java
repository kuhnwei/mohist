package com.kuhnwei.mohist.examples.javabase.designpatterns.strategy;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 15:59
 */
public class GivenGreenLight implements IStrategy {
    @Override
    public void operate() {
        System.out.println("求吴国太开个绿灯，放行");
    }
}
