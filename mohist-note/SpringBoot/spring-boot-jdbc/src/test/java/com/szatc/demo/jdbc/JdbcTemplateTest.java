package com.szatc.demo.jdbc;

import com.szatc.demo.BaseTest;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * JdbcTemplate 测试
 * @author Kuhn Wei, email@kuhnwei.com
 * @date 2018/5/17 14:04
 * @see JdbcTemplate
 **/
public class JdbcTemplateTest extends BaseTest {

    @Resource
    private JdbcTemplate jdbcTemplate;

    /**
     * 测试程序与数据库是否正常连接
     */
    @Test
    public void testConnection() {
        DataSource source = this.jdbcTemplate.getDataSource();
        try {
            // 连接是否关闭
            System.out.println(source.getConnection().isClosed());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
