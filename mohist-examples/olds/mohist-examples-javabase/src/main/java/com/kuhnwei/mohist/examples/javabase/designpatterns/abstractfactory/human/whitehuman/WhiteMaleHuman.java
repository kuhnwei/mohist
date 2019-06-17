package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.whitehuman;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:13
 */
public class WhiteMaleHuman extends AbstractWhiteHuman {
    @Override
    public void sex() {
        System.out.println("该白种人的性别为男...");
    }
}
