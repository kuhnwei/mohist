package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.blackhuman;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:12
 */
public class BlackFemaleHuman extends AbstractBlackHuman {
    @Override
    public void sex() {
        System.out.println("该黑种人性别为女...");
    }
}
