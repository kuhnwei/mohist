package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:20
 */
public interface HumanFactory {
    Human createYellowHuman();
    Human createWhiteHuman();
    Human createBlackHuman();
}
