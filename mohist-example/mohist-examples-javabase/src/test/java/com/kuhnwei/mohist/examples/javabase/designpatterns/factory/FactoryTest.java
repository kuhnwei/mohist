package com.kuhnwei.mohist.examples.javabase.designpatterns.factory;

import com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case01.Factory;
import com.kuhnwei.mohist.examples.javabase.designpatterns.factory.case01.Fruit;
import org.junit.Test;

/**
 * 工厂设计模式测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 19:49
 */
public class FactoryTest {

    @Test
    public void apple() {
        Fruit fruit = Factory.getInstance("apple");
        if (fruit != null) {
            fruit.eat();
        }
    }

    @Test
    public void cherry() {
        Fruit fruit = Factory.getInstance("cherry");
        if (fruit != null) {
            fruit.eat();
        }
    }

}
