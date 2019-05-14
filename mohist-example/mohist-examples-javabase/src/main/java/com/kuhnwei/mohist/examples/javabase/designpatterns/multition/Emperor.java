package com.kuhnwei.mohist.examples.javabase.designpatterns.multition;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:12
 */
public class Emperor {
    private static int maxNumOfEmperor = 2;
    private static List<String> emperorInfoList = new ArrayList<String>(maxNumOfEmperor);
    private static List<Emperor> emperorList = new ArrayList<Emperor>(maxNumOfEmperor);
    private static int countNumOfEmperor = 0;
    static {
        for (int i = 0; i < maxNumOfEmperor; i ++) {
            emperorList.add(new Emperor(i + ""));
        }
    }

    private Emperor() {}

    private Emperor(String info) {
        emperorInfoList.add(info);
    }

    public static Emperor getInstance() {
        Random random = new Random();
        countNumOfEmperor = random.nextInt(maxNumOfEmperor);
        return emperorList.get(countNumOfEmperor);
    }

    public void emperorInfo() {
        System.out.println(emperorInfoList.get(countNumOfEmperor));
    }
}
