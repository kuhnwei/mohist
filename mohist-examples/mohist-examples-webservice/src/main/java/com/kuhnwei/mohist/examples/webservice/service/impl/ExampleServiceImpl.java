package com.kuhnwei.mohist.examples.webservice.service.impl;

import com.kuhnwei.mohist.examples.webservice.service.ExampleService;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/14 13:32
 */
@WebService(serviceName = "ExampleFacotry",
        portName = "ExampleServiceInstance",
        name = "ExampleService",
        targetNamespace = "http://mohist.kuhnwei.com/examples/webservice/client/")
public class ExampleServiceImpl implements ExampleService {

    @Override
    @WebMethod(operationName = "print", exclude = false)
    public @WebResult(name = "result") String print(@WebParam(name = "message") String message) {
        System.out.println("message = [" + message + "]");
        return ">> result：" + message;
    }
}
