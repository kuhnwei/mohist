package com.kuhnwei.mohist.examples.javabase.reflect;

import org.junit.Test;

/**
 * 反射的基本操作原理测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/8 11:58
 */
public class ReflectTest {


    @Test
    public void _getClass() {
        String str = "Hello World .";
        Class<?> cls = str.getClass();
        System.out.println(cls);
        /*
        * 以上的方式是不会使用到的，在所有的开发环境里面这种凡是可以使用的几率是很低的。
        * */
    }

    @Test
    public void setClass() {
        Class<?> cls = String.class;
        System.out.println(cls);
        /*
        * 此操作方式不需要得到指定操作类的实例化对象，而直接通过类的名称就可以完成了。
        * 少了一个对象的空间了。但是以上的方式虽然严谨，那么却属于一个明确得结构。
        * 即：定义的时候类必须存在。
        * */
    }

    @Test
    public void forName() {
        try {
            Class<?> cls = Class.forName("java.lang.String");
            System.out.println(cls);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void newInstance() {
        try {
            Class<?> cls = Class.forName("Example");
            System.out.println(cls.newInstance());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            // 构造方法私有化
            e.printStackTrace();
        } catch (InstantiationException e) {
            // 没有无参构造方法
            e.printStackTrace();
        }
        /*
        * 使用"newInstance()"方法只能够调用类中的无参构造方法，这个时候的功能相当于使用new实现对象的实例化操作。
        * */
    }

    @Test
    public void factory() {
        Message message = MessageFactory.getInstance("Email");
        message.print("啦啦啦啦 ！！！！");
    }

}
