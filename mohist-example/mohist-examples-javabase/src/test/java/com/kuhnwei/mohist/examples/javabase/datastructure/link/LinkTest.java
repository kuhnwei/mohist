package com.kuhnwei.mohist.examples.javabase.datastructure.link;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

/**
 * 链表测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 15:13
 */
public class LinkTest {

    private Link link;

    @Before
    public void before() {
        this.link = new LinkList();
        this.link.add("A");
        this.link.add("B");
        this.link.add("C");
        this.link.add("D");
        this.link.add("E");
        this.link.add("F");
        this.link.add("G");
    }

    @Test
    public void add() {
        this.link.add(null);
        print(link);
    }

    @Test
    public void set() {
        this.link.set(3, "3");
        print(link);
    }

    @Test
    public void remove() {
        this.link.remove("G");
        print(link);
    }

    @Test
    public void get() {
        System.out.println(this.link.get(1));
    }

    @Test
    public void contains() {
        System.out.println(this.link.contains("F"));
    }

    @After
    public void clear() {
        this.link.clear();
        print(this.link);
    }

    private static void print(Link link) {
        System.out.println("链表是否为空：" + link.isEmpty()
                + " 链表长度：" + link.size()
                + " 链表内容：" + Arrays.toString(link.toArray()));
    }

}
