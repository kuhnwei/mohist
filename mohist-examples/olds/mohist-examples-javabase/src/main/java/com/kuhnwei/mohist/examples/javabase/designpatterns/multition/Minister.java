package com.kuhnwei.mohist.examples.javabase.designpatterns.multition;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/20 16:18
 */
public class Minister {
    public static void main(String[] args) {
        int ministerNum = 10;
        for (int i = 0; i < ministerNum; i ++) {
            Emperor emperor = Emperor.getInstance();
            System.out.print(i + " -> ");
            emperor.emperorInfo();
        }
    }
}
