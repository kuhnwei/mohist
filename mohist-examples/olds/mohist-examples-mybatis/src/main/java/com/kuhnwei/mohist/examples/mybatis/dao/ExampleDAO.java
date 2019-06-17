package com.kuhnwei.mohist.examples.mybatis.dao;

import com.kuhnwei.mohist.examples.mybatis.domain.ExampleDO;

import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 9:48
 */
public interface ExampleDAO {
    int save(ExampleDO example);
    int delete(ExampleDO example);
    ExampleDO get(ExampleDO example);
    List<ExampleDO> list();
    long count();
    int update(ExampleDO example);
}
