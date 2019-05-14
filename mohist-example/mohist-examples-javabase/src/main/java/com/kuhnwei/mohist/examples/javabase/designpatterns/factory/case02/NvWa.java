package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case02;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:35
 */
public class NvWa {
    public static void main(String[] args) {
        Human whiteHuman = HumanFactory.createHuman(WhiteHuman.class);
        whiteHuman.cry();
        whiteHuman.laugh();
        whiteHuman.talk();

        Human blackHuman = HumanFactory.createHuman(BlackHuman.class);
        blackHuman.cry();
        blackHuman.laugh();
        blackHuman.talk();

        Human yellowHuman = HumanFactory.createHuman(YellowHuman.class);
        yellowHuman.cry();
        yellowHuman.laugh();
        yellowHuman.talk();
        System.out.println("\n\n--------------------------------------------------");

        for (int i=0; i < 10000000; i ++) {
            Human human = HumanFactory.createHuman();
            human.cry();
            human.laugh();
            human.talk();
        }

    }
}
