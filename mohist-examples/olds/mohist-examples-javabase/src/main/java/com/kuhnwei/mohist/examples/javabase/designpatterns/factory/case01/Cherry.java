package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case01;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 19:43
 */
public class Cherry implements Fruit {
    @Override
    public void eat() {
        System.out.println("---------------- 吃樱桃。 ------------------");
    }
}
