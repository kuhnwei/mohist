package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case01;

/**
 * 工厂类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 19:43
 */
public class Factory {

    /** 苹果 */
    private static final String APPLE = "apple";

    /** 樱桃 */
    private static final String CHERRY = "cherry";

    /**
     * 根据水果名称 获取水果实例对象
     * @param className 水果名称
     * @return 水果对象
     */
    public static Fruit getInstance(String className) {
        if (APPLE.equalsIgnoreCase(className)) {
            return new Apple();
        } else if (CHERRY.equalsIgnoreCase(className)) {
            return new Cherry();
        } else {
            return null;
        }
    }
}
