package com.kuhnwei.mohist.examples.ssm.dao;

import com.kuhnwei.mohist.examples.ssm.domain.ExampleDO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;
import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 14:51
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = {"classpath*:spring/spring-context.xml"})
public class TestExampleDAO {

    @Autowired
    private ExampleDAO exampleDAO;

    @Test
    public void save() {
        System.out.println("----------[ 保存信息 ]-------------");
        ExampleDO example = new ExampleDO();
        example.setExampleStr("str-" + System.currentTimeMillis());
        example.setGmtCreate(new Date());
        example.setGmtModified(new Date());
        example.setDeleted(false);
        int result = exampleDAO.save(example);
        System.out.println("result : " + result);
    }

    @Test
    public void delete() {
        System.out.println("----------[ 删除信息 ]-------------");
        ExampleDO example = new ExampleDO();
        example.setId(2L);
        int result = exampleDAO.delete(example);
        System.out.println("result : " + result);
    }

    @Test
    public void get() {
        System.out.println("----------[ 查询信息 ]-------------");
        ExampleDO example = new ExampleDO();
        example.setId(1L);
        ExampleDO result = exampleDAO.get(example);
        System.out.println("result : " + result);
    }

    @Test
    public void list() {
        System.out.println("----------[ 查询列表 ]-------------");
        List<ExampleDO> list = exampleDAO.list();
        list.forEach(example -> System.out.println(example));
    }

    @Test
    public void count() {
        System.out.println("----------[ 统计总数 ]-------------");
        long count = exampleDAO.count();
        System.out.println("count : " + count);
    }

    @Test
    public void update() {
        System.out.println("----------[ 修改信息 ]-------------");
        ExampleDO example = new ExampleDO();
        example.setId(1L);
        ExampleDO exampleDO = exampleDAO.get(example);
        exampleDO.setExampleStr("str-" + System.currentTimeMillis());
        exampleDO.setGmtModified(new Date());
        int result = exampleDAO.update(exampleDO);
        System.out.println("result : " + result);
    }

}
