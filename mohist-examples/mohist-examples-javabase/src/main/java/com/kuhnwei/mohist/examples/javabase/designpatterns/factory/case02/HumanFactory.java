package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case02;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:30
 */
public class HumanFactory {

    /**
     * 定义一个MAP，初始化过的Human对象都存放在该map中
     */
    private static Map<String, Human> humans = new HashMap<>();

    /**
     * 创造人类
     * @param c
     * @return
     */
    public static Human createHuman(Class c) {
        Human human = null;
        try {
            // 创造人类
            if (humans.containsKey(c.getSimpleName())) {
                human = humans.get(c.getSimpleName());
            } else {
                human = (Human) Class.forName(c.getName()).newInstance();
                humans.put(c.getSimpleName(), human);
            }
        } catch (InstantiationException e) {
            System.out.println("必须指定类型, " + e);
        } catch (IllegalAccessException e) {
            System.out.println("类型错误, " + e);
        } catch (ClassNotFoundException e) {
            System.out.println("找不到指定的类，" + e);
        }
        return human;
    }

    public static Human createHuman() {
        Human human = null;
        List<Class> concreteHumanList = ClassUtils.getAllClassByInterface(Human.class);
        Random random = new Random();
        int rand = random.nextInt(concreteHumanList.size());
        human = createHuman(concreteHumanList.get(rand));
        return human;
    }
}
