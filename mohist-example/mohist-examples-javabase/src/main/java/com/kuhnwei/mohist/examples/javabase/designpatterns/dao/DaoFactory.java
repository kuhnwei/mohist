package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import java.sql.Connection;

/**
 * DAO工厂类
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:39
 */
public class DaoFactory {

    /**
     * 私有构造方法
     */
    private DaoFactory() {}

    /**
     * 获取ExampleDAO接口的实例化对象
     * @param connection 数据库连接对象
     * @return ExampleDao
     */
    public static ExampleDao getExampleDaoInstance(Connection connection) {
        return new ExampleDaoImpl(connection);
    }

}
