package com.szatc.demo.dao;

import com.szatc.demo.BaseTest;
import com.szatc.demo.domain.Example;
import org.junit.Test;

import javax.annotation.Resource;

/**
 * 操作数据表 example 的单元测试
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/17 14:35
 * @see ExampleDao
 **/
public class ExampleDaoTest extends BaseTest {

    @Resource
    private ExampleDao exampleDao;

    @Test
    public void save() {
        Example example = new Example();
        example.setText("ExampleDaoTest[save]测试数据。");
        System.out.println(this.exampleDao.save(example));
    }

    @Test
    public void delete() {
        System.out.println(this.exampleDao.delete(2));
    }

    @Test
    public void remove() {
        System.out.println(this.exampleDao.remove(3));
    }

    @Test
    public void get() {
        System.out.println(this.exampleDao.get(1));
    }

    @Test
    public void list() {
        System.out.println(this.exampleDao.list());
    }

    @Test
    public void count() {
        System.out.println(this.exampleDao.count());
    }

    @Test
    public void update() {
        Example example = new Example();
        example.setId(1L);
        example.setText("ExampleDaoTest[update]测试数据");
        System.out.println(this.exampleDao.update(example));
    }
}
