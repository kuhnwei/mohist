package com.kuhnwei.mohist.examples.cas.authentication;

import org.jasig.cas.authentication.RootCasException;

/**
 * 用户输入的验证码为空时 的异常处理类
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/31 16:08
 */
public class NullCaptchaException extends RootCasException {
    public static final String CODE = "captcha.required";
    public NullCaptchaException() {
        super(CODE);
    }
}
