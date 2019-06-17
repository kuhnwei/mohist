package com.kuhnwei.mohist.examples.javabase.designpatterns.adapter;

/**
 * @author Kuhn Wei ,email@kuhnwei.com
 * @version 2018/4/21 14:28
 */
public class App {
    public static void main(String[] args) {
        IUserInfo youngGirl = new UserInfo();
        for (int i = 0; i < 101; i ++) {
            youngGirl.getMobileNumber();
        }
        IUserInfo outer = new OuterUserInfo();
        outer.getMobileNumber();
    }
}
