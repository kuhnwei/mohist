package com.kuhnwei.mohist.examples.cas.web.flow;

import com.kuhnwei.mohist.examples.cas.authentication.BadCaptchaException;
import com.kuhnwei.mohist.examples.cas.authentication.CaptchaRememberMeUsernamePasswordCredential;
import com.kuhnwei.mohist.examples.cas.authentication.NullCaptchaException;
import org.jasig.cas.authentication.Credential;
import org.jasig.cas.authentication.RootCasException;
import org.jasig.cas.web.flow.AuthenticationViaFormAction;
import org.jasig.cas.web.support.WebUtils;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.util.StringUtils;
import org.springframework.webflow.execution.RequestContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * 用于验证码检测的Action类
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/31 15:53
 */
public class CaptchaAuthenticationViaFormAction extends AuthenticationViaFormAction {
    public final String validatorCaptcha(final RequestContext context, final Credential credential, final MessageContext messageContext) throws Exception {
        // 获取生成的验证码数据
        HttpServletRequest request = WebUtils.getHttpServletRequest(context);
        HttpSession session = request.getSession();
        String rand = (String) session.getAttribute("rand");
        // 取得生成的验证码后，验证码失效处理
        session.removeAttribute("rand");
        CaptchaRememberMeUsernamePasswordCredential crupc = (CaptchaRememberMeUsernamePasswordCredential) credential;
        // 取得用户输入的验证码数据
        String captcha = crupc.getCaptcha();
        // 用户输入的验证码和生成的验证码进行校验
        if (StringUtils.isEmpty(captcha) || StringUtils.isEmpty(rand)) {
            this.errorDisplay(new NullCaptchaException(), messageContext);
            return "error";
        } else if (captcha.equalsIgnoreCase(rand)) {
            return "success";
        }
        this.errorDisplay(new BadCaptchaException(), messageContext);
        return "error";
    }

    /**
     * 专门负责异常的信息提示
     * @param e 异常对象
     * @param messageContext 异常信息
     */
    private void errorDisplay(final RootCasException e, final MessageContext messageContext) {
        messageContext.addMessage(new MessageBuilder().error().code(e.getCode()).defaultText(e.getCode()).build());
    }
}
