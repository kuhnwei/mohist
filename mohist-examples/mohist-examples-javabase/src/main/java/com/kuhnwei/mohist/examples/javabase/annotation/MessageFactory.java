package com.kuhnwei.mohist.examples.javabase.annotation;


/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:26
 */
@FactoryClass(className = "News")
public class MessageFactory {

    public static Message getInstance() throws Exception {
        Class<?> cls = MessageFactory.class;
        FactoryClass factoryClass = cls.getAnnotation(FactoryClass.class);
        String className = factoryClass.className();
        Class<?> msgIns = Class.forName(className);
        return (Message) msgIns.newInstance();
    }

}
