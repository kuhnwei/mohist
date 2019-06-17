package com.kuhnwei.mohist.examples.ssm.shiro.realm;

import com.kuhnwei.mohist.examples.ssm.shiro.domain.UserDO;
import com.kuhnwei.mohist.examples.ssm.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 15:09
 */
@Component
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        /*
        * 用户角色与权限验证
        * */
        String username = (String) principal.getPrimaryPrincipal();
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        UserDO user = userService.getByUsername(username);
        Map<String, Object> map = userService.listAuthByUserId(user.getId());
        Set<String> allRoles = (Set<String>) map.get("allRoles");
        Set<String> allPermissions = (Set<String>) map.get("allPermissions");
        auth.setRoles(allRoles);
        auth.setStringPermissions(allPermissions);
        return auth;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /*
        * 用户登录认证
        * */
        String username = (String) token.getPrincipal();
        UserDO user = userService.getByUsername(username);
        if (user == null) {
            throw new UnknownAccountException("该用户名称不存在！");
        } else {
            String password = new String((char[]) token.getCredentials());
            if (user.getPassword().equals(password)) {
                return new SimpleAuthenticationInfo(username, password, "userRealm");
            } else {
                throw new IncorrectCredentialsException("密码错误！");
            }
        }
    }
}
