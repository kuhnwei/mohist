package com.kuhnwei.mohist.examples.cas.authentication;

import org.jasig.cas.authentication.RememberMeUsernamePasswordCredential;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * 自定义的用户名密码凭证类，继承至org.jasig.cas.authentication.RememberMeUsernamePasswordCredential
 * 添加验证码的处理字段
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/31 15:47
 */
public class CaptchaRememberMeUsernamePasswordCredential extends RememberMeUsernamePasswordCredential {

    /**
     * 验证码
     */
    @NotNull
    @Size(min = 5, message = "captcha.required")
    private String captcha;

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        CaptchaRememberMeUsernamePasswordCredential that = (CaptchaRememberMeUsernamePasswordCredential) o;

        return captcha != null ? captcha.equals(that.captcha) : that.captcha == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (captcha != null ? captcha.hashCode() : 0);
        return result;
    }
}
