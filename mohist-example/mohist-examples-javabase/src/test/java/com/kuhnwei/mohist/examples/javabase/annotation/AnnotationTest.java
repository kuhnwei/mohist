package com.kuhnwei.mohist.examples.javabase.annotation;

import org.junit.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:08
 */
public class AnnotationTest {

    /**
     *  取得类中全部的Annotation
     */
    @Test
    public void getAnnotations() {
        Class<?> cls = Example.class;
        Annotation[] annotations = cls.getAnnotations();
        System.out.println(Arrays.toString(annotations));
    }

    /**
     * 取得指定的Annotation
     */
    @Test
    public void getAnnotation() {
        Class<?> cls = Example.class;
        ExampleAnnotation ea = cls.getAnnotation(ExampleAnnotation.class);
        System.out.println(ea.name());
        System.out.println(ea.value());
    }

    @Test
    public void factory() {
        try {
            Message message = MessageFactory.getInstance();
            message.print("明天开始春节假期！");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
