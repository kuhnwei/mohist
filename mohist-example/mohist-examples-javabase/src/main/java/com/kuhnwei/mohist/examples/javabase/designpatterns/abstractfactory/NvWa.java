package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory;

import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.humanfactory.FemaleHumanFactory;
import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.humanfactory.MaleHumanFactory;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 13:53
 */
public class NvWa {
    public static void main(String[] args) {
        HumanFactory maleHumanFactory = new MaleHumanFactory();
        HumanFactory femaleHumanFactory = new FemaleHumanFactory();
        Human maleYellowHuman = maleHumanFactory.createYellowHuman();
        Human femaleYellowHuman = femaleHumanFactory.createYellowHuman();
        maleYellowHuman.sex();
        femaleYellowHuman.sex();
    }
}
