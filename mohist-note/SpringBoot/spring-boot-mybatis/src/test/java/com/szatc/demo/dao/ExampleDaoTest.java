package com.szatc.demo.dao;

import com.szatc.demo.BaseTest;
import com.szatc.demo.domain.Example;
import org.junit.Test;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/21 21:02
 * @see ExampleDao
 */
public class ExampleDaoTest extends BaseTest {
    @Resource
    private ExampleDao exampleDao;

    @Test
    public void save() {
        Example e = new Example();
        e.setText("CSZ" + ((int)((Math.random()*9+1)*1000)));
        e.setGmtCreate(new Date());
        e.setGmtModified(new Date());
        e.setDeleted(false);
        System.out.println(this.exampleDao.save(e));
    }

    @Test
    public void remove() {
        System.out.println(this.exampleDao.remove(new Example(2L)));
    }

    @Test
    public void get() {
        System.out.println(this.exampleDao.get(new Example(1L)));
    }

    @Test
    public void list() {
        System.out.println(this.exampleDao.list(new Example()));
    }

    @Test
    public void count() {
        System.out.println(this.exampleDao.count(new Example()));
    }

    @Test
    public void update() {
        Example e = new Example();
        e.setId(1L);
        e.setText("ExampleDaoTest[update]测试数据。");
        e.setGmtModified(new Date());
        System.out.println(this.exampleDao.update(e));
    }
}
