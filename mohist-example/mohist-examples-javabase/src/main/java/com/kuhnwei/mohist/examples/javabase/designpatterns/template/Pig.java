package com.kuhnwei.mohist.examples.javabase.designpatterns.template;

/**
 * 猪
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:18
 */
public class Pig extends AbstractAction {
    @Override
    void eat() {
        System.out.println("--->>> 猪在进食 ");
    }

    @Override
    void sleep() {
        System.out.println("--->>> 猪在睡觉 ");
    }

    @Override
    void work() {

    }
}
