package com.kuhnwei.mohist.examples.ssm.shiro.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 15:33
 */
@Controller
public class SystemController {
    @RequestMapping("/shiroLogin.do")
    public ModelAndView login(String username, String password) {
        ModelAndView mav = new ModelAndView();
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            subject.login(token);
            mav.setViewName("welcome");
        } catch (Exception e) {
            e.printStackTrace();
            mav.setViewName("login");
        }
        return mav;
    }

    @RequestMapping("/loginUrl.do")
    private ModelAndView loginUrl() {
        return new ModelAndView("login");
    }

    @RequestMapping("/unauthUrl.do")
    private ModelAndView unauthUrl() {
        return new ModelAndView("unauthUrl");
    }

    @RequestMapping("/successUrl.do")
    private ModelAndView successUrl() {
        return new ModelAndView("welcome");
    }

    @RequestMapping("/logout.do")
    private String logout() {
        SecurityUtils.getSubject().logout();
        return "login";
    }

    @RequestMapping("/remember/isRemembered")
    @ResponseBody
    public String isRemembered() {
        return String.valueOf(SecurityUtils.getSubject().isRemembered());
    }
}
