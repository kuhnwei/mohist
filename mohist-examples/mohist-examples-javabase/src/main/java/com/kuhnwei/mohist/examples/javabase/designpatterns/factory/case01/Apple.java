package com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case01;

/**
 * 苹果
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 19:41
 */
public class Apple implements Fruit {
    @Override
    public void eat() {
        System.out.println("---------------- 吃苹果。 ------------------");
    }
}
