package com.kuhnwei.mohist.examples.javabase.designpatterns.template;

import org.junit.Test;

/**
 * 模板设计模式测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 20:23
 */
public class TemplateTest {

    @Test
    public void person() {
        new Person().execute();
    }

    @Test
    public void robot() {
        new Robot().execute();
    }

    @Test
    public void pig() {
        new Pig().execute();
    }
}
