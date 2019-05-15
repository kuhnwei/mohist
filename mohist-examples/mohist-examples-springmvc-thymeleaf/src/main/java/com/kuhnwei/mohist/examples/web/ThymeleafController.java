package com.kuhnwei.mohist.examples.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/19 21:47
 */
@Controller
public class ThymeleafController {


    @RequestMapping(value = "/hello")
    public ModelAndView hello() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", "Hello Thymeleaf.");
        mav.setViewName("hello");
        return mav;
    }
}
