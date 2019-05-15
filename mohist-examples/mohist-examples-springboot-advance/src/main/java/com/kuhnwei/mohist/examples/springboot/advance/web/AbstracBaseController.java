package com.kuhnwei.mohist.examples.springboot.advance.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/3/3 16:13
 */
public abstract class AbstracBaseController {
    @Autowired
    private MessageSource messageSource;
    public String getMessage(String key, String... args) {
        return this.messageSource.getMessage(key, args, Locale.getDefault());
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        // 首先建立一个可以将字符串转换为日期的工具程序类
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 明确的描述此时需要注册一个日期格式的转化处理程序类
        binder.registerCustomEditor(java.util.Date.class, new CustomDateEditor(sdf, true));
    }
}
