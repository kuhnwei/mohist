package com.kuhnwei.mohist.examples.cas.authentication;

import org.jasig.cas.authentication.RootCasException;

/**
 * 用户输入的验证码错误 异常处理类
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/31 16:08
 */
public class BadCaptchaException extends RootCasException {
    public static final String CODE = "error.authentication.captcha.bad";
    public BadCaptchaException() {
        super(CODE);
    }
}
