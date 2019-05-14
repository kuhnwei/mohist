package com.kuhnwei.mohist.examples.ssm.shiro.realm;

import com.kuhnwei.mohist.examples.ssm.shiro.domain.UserDO;
import com.kuhnwei.mohist.examples.ssm.shiro.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.CollectionUtils;
import org.apache.shiro.util.StringUtils;
import org.jasig.cas.client.authentication.AttributePrincipal;
import org.jasig.cas.client.validation.Assertion;
import org.jasig.cas.client.validation.TicketValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Kuhn Wei, email@kuhnwei.com
 * @version 2018/3/30 21:13
 */
public class UserCasRealm extends CasRealm {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Resource
    private UserService userService;

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        logger.info("-------------- 用户登录认证 -----------------");
        CasToken casToken = (CasToken) token;
        if (Objects.isNull(casToken)) {
            return null;
        }
        // cas一定要返回给用户一个票根，所以需要取得这个票根的内容
        String ticket = (String) casToken.getCredentials();
        // 对票根的有效性进行验证
        if (!StringUtils.hasText(ticket)) {
            return null;
        }
        TicketValidator ticketValidator = super.ensureTicketValidator();
        try {
            // 对票根做cas验证处理
            Assertion casAssertion = ticketValidator.validate(ticket, super.getCasService());
            // 通过cas取得用户信息
            AttributePrincipal casPrincipal = casAssertion.getPrincipal();
            // 取得当前登录的用户名
            String username = casPrincipal.getName();
            // 取出用户名之后需要将所有的相关信息（包括CAS相关信息）一起进行一个列表的创建
            List<Object> principals = CollectionUtils.asList(username, casPrincipal.getAttributes());
            PrincipalCollection principalCollection = new SimplePrincipalCollection(principals, super.getName());
            return new SimpleAuthenticationInfo(principalCollection, ticket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return super.doGetAuthenticationInfo(token);
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        logger.info("-------------- 用户权限认证 -----------------");
        // 取得用户登录名
        String username = (String) principals.getPrimaryPrincipal();
        // 定义授权信息的返回数据
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        try {
            UserDO user = userService.getByUsername(username);
            Map<String, Object> map = userService.listAuthByUserId(user.getId());
            Set<String> allRoles = (Set<String>) map.get("allRoles");
            Set<String> allPermissions = (Set<String>) map.get("allPermissions");
            auth.setRoles(allRoles);
            auth.setStringPermissions(allPermissions);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return auth;
    }
}
