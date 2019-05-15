package com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.humanfactory;

import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.Human;
import com.kuhnwei.mohist.examples.javabase.designpatterns.abstractfactory.HumanEnum;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 11:26
 */
public class MaleHumanFactory extends AbstractHumanFactory {
    @Override
    public Human createYellowHuman() {
        return super.createHuman(HumanEnum.YelloMaleHuman);
    }

    @Override
    public Human createWhiteHuman() {
        return super.createHuman(HumanEnum.WhiteMaleHuman);
    }

    @Override
    public Human createBlackHuman() {
        return super.createHuman(HumanEnum.BlackMaleHuman);
    }
}
