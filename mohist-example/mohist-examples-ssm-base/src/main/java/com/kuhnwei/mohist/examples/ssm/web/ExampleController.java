package com.kuhnwei.mohist.examples.ssm.web;

import com.kuhnwei.mohist.examples.ssm.domain.ExampleDO;
import com.kuhnwei.mohist.examples.ssm.service.ExampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 15:16
 */
@RestController
@RequestMapping(value = "/example")
public class ExampleController {

    @Autowired
    private ExampleService exampleService;

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public int save(ExampleDO example) {
        return exampleService.save(example);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public int delete(ExampleDO example) {
        return exampleService.delete(example);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ExampleDO get(ExampleDO example) {
        return exampleService.get(example);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public List<ExampleDO> list() {
        return exampleService.list();
    }

    @RequestMapping(value = "/count", method = RequestMethod.POST)
    public long count() {
        return exampleService.count();
    }

    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public int update(ExampleDO example) {
        return exampleService.update(example);
    }

}
