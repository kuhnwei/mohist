package com.kuhnwei.mohist.examples.springboot.advance.web;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 15:42
 */
@RestController
public class AdvanceController {
    @RequestMapping("/")
    public String home() {
        return "www.kuhnwei.com";
    }

    @RequestMapping(value = "/echo/{message}", method = RequestMethod.GET)
    public String echo(@PathVariable("message") String msg) {
        return "【ECHO】 " + msg;
    }

    @RequestMapping("/object")
    public String object(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("*** 客户端IP地址：" + request.getRemoteAddr());
        System.out.println("*** 取得客户端响应编码：" + response.getCharacterEncoding());
        System.out.println("*** 取得SeesionID：" + request.getSession().getId());
        System.out.println("*** 取得真实路径：" + request.getServletContext().getRealPath("/folder/"));
        return "www.kuhnwei.com";
    }
}
