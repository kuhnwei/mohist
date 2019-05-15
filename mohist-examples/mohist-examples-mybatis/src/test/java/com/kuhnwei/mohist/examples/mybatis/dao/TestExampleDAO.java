package com.kuhnwei.mohist.examples.mybatis.dao;

import com.kuhnwei.mohist.examples.mybatis.dao.ExampleDAO;
import com.kuhnwei.mohist.examples.mybatis.domain.ExampleDO;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 10:31
 */
public class TestExampleDAO {

    private static SqlSessionFactory sqlSessionFactory;
    private static SqlSession session;

    static {
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybatis.config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Before
    public void before() {
        System.out.println();
        System.out.println("----------[ 开启事务 ]-------------");
        session = sqlSessionFactory.openSession();
    }

    @After
    public void after() {
        session.commit();
        System.out.println("----------[ 提交事务 ]-------------");
        session.close();
        System.out.println("----------[ 关闭事务 ]-------------");
        System.out.println();
    }

    @Test
    public void save() {
        ExampleDO example = new ExampleDO();
        example.setExampleStr("str-" + System.currentTimeMillis());
        example.setGmtCreate(new Date());
        example.setGmtModified(new Date());
        example.setDeleted(false);

        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        int result = exampleDAO.save(example);
        System.out.println("----------[ 保存信息 ]-------------");
        System.out.println(result);
//        System.out.println(example);
    }

    @Test
    public void delete() {
        ExampleDO example = new ExampleDO();
        example.setId(2L);

        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        int result = exampleDAO.delete(example);
        System.out.println("----------[ 删除信息 ]-------------");
        System.out.println(result);
    }

    @Test
    public void get() {
        ExampleDO example = new ExampleDO();
        example.setId(1L);

        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        ExampleDO result = exampleDAO.get(example);
        System.out.println("----------[ 查询信息 ]-------------");
        System.out.println(result);
    }

    @Test
    public void list() {
        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        List<ExampleDO> list = exampleDAO.list();
        System.out.println("----------[ 查询列表 ]-------------");
        list.forEach(example -> System.out.println(example));
    }

    @Test
    public void count() {
        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        long count = exampleDAO.count();
        System.out.println("----------[ 统计总数 ]-------------");
        System.out.println(count);
    }

    @Test
    public void update() {
        ExampleDO example = new ExampleDO();
        example.setId(1L);

        ExampleDAO exampleDAO = session.getMapper(ExampleDAO.class);
        ExampleDO exampleDO = exampleDAO.get(example);
        exampleDO.setExampleStr("str-" + System.currentTimeMillis());
        exampleDO.setGmtModified(new Date());
        int result = exampleDAO.update(exampleDO);
        System.out.println("----------[ 修改信息 ]-------------");
        System.out.println(result);
    }
}
