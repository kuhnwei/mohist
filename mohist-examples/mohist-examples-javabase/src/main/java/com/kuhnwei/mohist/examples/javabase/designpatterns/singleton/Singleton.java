package com.kuhnwei.mohist.examples.javabase.designpatterns.singleton;

/**
 * 单例类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:04
 */
public class Singleton {

    /**
     * 单例类实例化对象
     */
    private static Singleton INSTANCE = new Singleton();

    /**
     * 私有构造方法
     */
    private Singleton() {}

    /**
     * 获取但例类实例化对象
     * @return 单例类对象
     */
    public static Singleton getInstance() {
        return INSTANCE;
    }

    public void print() {
        System.out.println("------------------- 单例设计模式 ---------------------");
    }

}
