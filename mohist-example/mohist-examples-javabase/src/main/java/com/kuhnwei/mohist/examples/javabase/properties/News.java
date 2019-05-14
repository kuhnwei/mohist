package com.kuhnwei.mohist.examples.javabase.properties;

import com.kuhnwei.mohist.examples.javabase.annotation.Message;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:24
 */
public class News implements Message {
    @Override
    public void print(String str) {
        System.out.println("新闻消息，内容 ： [" + str + "]");
    }
}
