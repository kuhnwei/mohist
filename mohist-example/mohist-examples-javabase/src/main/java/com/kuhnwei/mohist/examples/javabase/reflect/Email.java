package com.kuhnwei.mohist.examples.javabase.reflect;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:25
 */
public class Email implements Message {
    @Override
    public void print(String str) {
        System.out.println("邮件消息，内容  ： [" + str + "]");
    }
}
