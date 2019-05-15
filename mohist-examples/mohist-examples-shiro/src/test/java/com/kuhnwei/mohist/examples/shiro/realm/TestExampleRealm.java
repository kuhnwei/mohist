package com.kuhnwei.mohist.examples.shiro.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/2 20:43
 */
public class TestExampleRealm {

    @BeforeClass
    public static void init() {
        // 1.取得Factory接口对象，主要的目的是通过配置文件加载文件信息
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = factory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
    }

    @Test
    public void login() {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("kuhnwei", "650901");
        subject.login(token);
        System.out.println(subject.hasRole("admin"));
        System.out.println(subject.hasRole("user"));
        System.out.println(subject.hasRole("dept"));
        System.out.println(subject.isPermitted("admin:list"));
        System.out.println(subject.isPermitted("admin:save"));
        System.out.println(subject.isPermitted("user:save"));
    }

}
