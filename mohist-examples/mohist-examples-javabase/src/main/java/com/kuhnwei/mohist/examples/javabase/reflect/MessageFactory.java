package com.kuhnwei.mohist.examples.javabase.reflect;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 12:22
 */
public class MessageFactory {

    public static Message getInstance(String className) {
        try {
            return (Message) Class.forName(className).newInstance();
        } catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
