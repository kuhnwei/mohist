package com.kuhnwei.mohist.examples.javabase.designpatterns.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/1/26 16:36
 */
public class DatabaseConnection {

    private static final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost:3306/examples?true&characterEncoding=utf-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    /*
    private static final String DB_DRIVER = "oracle.jdbc.driver.OracleDriver";
    private static final String DB_URL = "jdbc:oracle:thin:@localhost:1521:orcl";
    private static final String DB_USER = "scott";
    private static final String DB_PASSWORD = "tiger";
    */

    /** 数据库连接对象 */
    private Connection conn;

    /**
     * 构造函数，初始化连接数据库
     */
    public DatabaseConnection() {

        try {
            Class.forName(DB_DRIVER);
            try {
                this.conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    /**
     * 获取数据库连接对象
     * @return 数据库连接对象
     */
    public Connection getConnection() {
        return this.conn;
    }

    /**
     * 断开数据库的连接
     */
    public void close() {
        if (this.conn != null) {
            try{
                this.conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
