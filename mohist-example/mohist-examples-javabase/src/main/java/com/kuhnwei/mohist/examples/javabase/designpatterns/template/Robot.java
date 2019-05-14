package com.kuhnwei.mohist.examples.javabase.designpatterns.template;

/**
 * 机器人
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:17
 */
public class Robot extends AbstractAction {
    @Override
    void eat() {
        System.out.println("--->>> 机器人补充能量 ");
    }

    @Override
    void sleep() {

    }

    @Override
    void work() {
        System.out.println("--->>> 机器人正在工作 ");
    }
}
