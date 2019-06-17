package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.humanfactory;

import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.Human;
import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.HumanEnum;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:27
 */
public class FemaleHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloFemaleHuman);
    }

    @Override
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteFemaleHuman);
    }

    @Override
    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BlackFemaleHuman);
    }
}
