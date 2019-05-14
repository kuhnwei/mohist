package com.kuhnwei.mohist.examples.javabase.reflect.field;

import java.lang.reflect.Field;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/24 9:16
 **/
public class FiledTestMain {
    public static void main(String[] args) throws IllegalAccessException, NoSuchFieldException {
        Person person = new Person("weikun", 25);
        System.out.println(person);
        Field[] fields = Person.class.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true); // 设置true 可操作私有成员
            System.out.println(field.getName() + ":" + field.get(person));
        }
        // 获取私有成员 name
        Field nameField = Person.class.getDeclaredField("name");
        // 在获取私有成员的值 或者 修改私有成员的值 都必须先设置accessible为 true，不然会报异常
        nameField.setAccessible(true);
        // 修改person对象的name 值
        nameField.set(person, "kuhnwei");
        System.out.println(person);

        Field ageField = Person.class.getDeclaredField("age");
        ageField.setAccessible(true);
        ageField.set(person, 18);
        System.out.println(person);

    }
}
