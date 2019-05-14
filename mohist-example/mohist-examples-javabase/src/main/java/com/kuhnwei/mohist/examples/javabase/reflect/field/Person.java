package com.kuhnwei.mohist.examples.javabase.reflect.field;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/24 9:15
 **/
public class Person {

    private final String name;

    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
