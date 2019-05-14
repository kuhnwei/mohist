package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory;

/**
 * 世界上有哪些类型的人，列出来
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:15
 */
public enum HumanEnum {

    YelloMaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.yellowhuman.YellowMaleHuman"),
    YelloFemaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.yellowhuman.YellowFemaleHuman"),
    WhiteFemaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.whitehuman.WhiteFemaleHuman"),
    WhiteMaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.whitehuman.WhiteMaleHuman"),
    BlackFemaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.blackhuman.BlackFemaleHuman"),
    BlackMaleHuman("com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.human.blackhuman.BlackMaleHuman");

    private String value;

    HumanEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
