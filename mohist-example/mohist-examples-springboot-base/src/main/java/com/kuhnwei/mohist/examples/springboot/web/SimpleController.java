package com.kuhnwei.mohist.examples.springboot.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 16:31
 */
@RestController
@RequestMapping(value = "/simple")
public class SimpleController {

    @RequestMapping(value = "/hello")
    public String helloWorld() {
        return "Hello Spring Boot .";
    }

}
