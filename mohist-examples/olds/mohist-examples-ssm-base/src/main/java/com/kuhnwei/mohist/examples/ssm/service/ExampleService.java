package com.kuhnwei.mohist.examples.ssm.service;

import com.kuhnwei.mohist.examples.ssm.dao.ExampleDAO;
import com.kuhnwei.mohist.examples.ssm.domain.ExampleDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 15:00
 */
@Service
public class ExampleService {

    @Autowired
    private ExampleDAO exampleDAO;

    public int save(ExampleDO example) {
        example.setGmtCreate(new Date());
        example.setGmtModified(new Date());
        example.setDeleted(false);
        return exampleDAO.save(example);
    }

    public int delete(ExampleDO example) {
        return exampleDAO.delete(example);
    }

    public ExampleDO get(ExampleDO example) {
        return exampleDAO.get(example);
    }

    public List<ExampleDO> list() {
        return exampleDAO.list();
    }

    public long count() {
        return exampleDAO.count();
    }

    public int update(ExampleDO example) {
        ExampleDO e = exampleDAO.get(example);
        e.setGmtModified(new Date());
        e.setExampleStr(example.getExampleStr());
        return exampleDAO.update(e);
    }
}
