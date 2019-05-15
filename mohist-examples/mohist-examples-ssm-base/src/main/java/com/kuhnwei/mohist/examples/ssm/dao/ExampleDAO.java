package com.kuhnwei.mohist.examples.ssm.dao;


import com.kuhnwei.mohist.examples.ssm.domain.ExampleDO;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 9:48
 */
@Repository
public interface ExampleDAO {
    int save(ExampleDO example);
    int delete(ExampleDO example);
    ExampleDO get(ExampleDO example);
    List<ExampleDO> list();
    long count();
    int update(ExampleDO example);
}
