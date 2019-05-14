package com.kuhnwei.mohist.examples.javabase.designpatterns.template;

/**
 * 人
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:19
 */
public class Person extends AbstractAction {
    @Override
    void eat() {
        System.out.println("--->>> 人在吃饭 ");
    }

    @Override
    void sleep() {
        System.out.println("--->>> 人在睡觉 ");
    }

    @Override
    void work() {
        System.out.println("--->>> 人在工作 ");
    }
}
