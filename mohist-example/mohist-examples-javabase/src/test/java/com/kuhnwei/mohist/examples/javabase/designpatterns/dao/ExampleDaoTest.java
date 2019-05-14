package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.sql.SQLException;

/**
 * ExampleDao测试类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:43
 */
public class ExampleDaoTest {

    private DatabaseConnection dc;

    private ExampleDao exampleDao;

    @Before
    public void before() {
        this.dc = new DatabaseConnection();
        this.exampleDao = DaoFactory.getExampleDaoInstance(dc.getConnection());
    }

    @Test
    public void save() throws SQLException {
        ExampleDO e = new ExampleDO();
        e.setExample("TEST Example : " + System.currentTimeMillis());
        this.exampleDao.save(e);
    }

    @Test
    public void remove() throws SQLException {
        this.exampleDao.remove(0);
    }

    // TODO list
    @Test
    public void list() throws SQLException {
        System.out.println(this.exampleDao.list().size());
        System.out.println("--------------------------------------------------------------------------");
        System.out.println(this.exampleDao.list(1,2));
    }

    @Test
    public void count() throws SQLException {
        System.out.println(this.exampleDao.count());
    }

    @Test
    public void update() throws SQLException {
        ExampleDO e = new ExampleDO();
        e.setId(1);
        e.setExample("UPDATE : " + System.currentTimeMillis());
        this.exampleDao.update(e);
    }

    @After
    public void after() {
        this.dc.close();
    }

}
