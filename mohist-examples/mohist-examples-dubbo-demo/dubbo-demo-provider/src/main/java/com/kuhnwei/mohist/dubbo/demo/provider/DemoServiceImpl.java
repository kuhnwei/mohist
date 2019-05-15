package com.kuhnwei.mohist.dubbo.demo.provider;

import com.alibaba.dubbo.rpc.RpcContext;
import com.kuhnwei.mohist.dubbo.demo.DemoService;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/4/1 22:11
 */
public class DemoServiceImpl implements DemoService {
    @Override
    public String sayHello(String name) {
        System.out.println("["+new SimpleDateFormat("HH:mm:ss").format(new Date())+"] Hello " + name + "" +
                ", request from consumer: " + RpcContext.getContext().getRemoteAddress());
        return "Hello " + name + ", response from provider: " + RpcContext.getContext().getLocalAddress();
    }
}
