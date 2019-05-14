package com.kuhnwei.mohist.examples.springboot.advance.service.impl;


import com.kuhnwei.mohist.examples.springboot.advance.service.MessageService;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 16:32
 */

public class MessageServiceImpl implements MessageService {
    @Override
    public String info() {
        return "www.kuhnwei.com";
    }
}
