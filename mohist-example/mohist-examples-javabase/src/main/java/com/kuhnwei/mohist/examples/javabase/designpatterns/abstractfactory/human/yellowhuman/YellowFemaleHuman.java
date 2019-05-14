package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.yellowhuman;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:08
 */
public class YellowFemaleHuman extends AbstractYellowHuman {
    @Override
    public void sex() {
        System.out.println("该黄种人性别为女...");
    }
}
