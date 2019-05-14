package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.humanfactory;


import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.Human;
import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.HumanEnum;
import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.HumanFactory;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:22
 */
public  abstract class AbstractHumanFactory implements HumanFactory {
    protected Human createHuman(HumanEnum humanEnum) {
        Human human = null;
        if (!humanEnum.getValue().equals("")) {
            try {
                human = (Human) Class.forName(humanEnum.getValue()).newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return human;
    }
}
