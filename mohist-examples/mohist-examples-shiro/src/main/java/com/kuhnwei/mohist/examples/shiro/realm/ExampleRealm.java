package com.kuhnwei.mohist.examples.shiro.realm;

import com.kuhnwei.mohist.examples.shiro.domain.User;
import com.kuhnwei.mohist.examples.shiro.service.UserService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 *
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/2 20:17
 */
public class ExampleRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principal) {
        System.out.println();
        System.out.println("--->>> 用户角色与权限校验 [ doGetAuthorizationInfo ] ...");
        System.out.println();

        // 1.取得当前用户名
        String username = (String) principal.getPrimaryPrincipal();

        // 2.查询用户信息
        UserService userService = new UserService();
        User user = userService.getByUsername(username);

        // 3.定义授权信息的返回数据
        SimpleAuthorizationInfo auth = new SimpleAuthorizationInfo();
        auth.setRoles(userService.listRoleByUser(user));
        auth.setStringPermissions(userService.listPermissionsByUser(user));

        return auth;
    }

    /**
     * 用户登录认证
     * @param token token
     * @return AuthenticationInfo
     * @throws AuthenticationException 权限认证异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /*
        * 登录认证的方法需要先执行，因为需要用它来判断登录的用户信息是否合法
        * */
        System.out.println();
        System.out.println("--->>> 用户登录认证 [ doGetAuthenticationInfo ] ...");
        System.out.println();

        // 1.获取需要验证的用户名
        String username = (String) token.getPrincipal();
        // 2.通过用户名去查询用户的信息
        UserService userService = new UserService();
        User user = userService.getByUsername(username);
        // 3.通过查询到的用户信息进行校验
        if (user == null) {
            throw new UnknownAccountException("-> [用户不存在] ...");
        } else {
            // 3.1 进行密码的校验处理
            String password = new String((char[]) token.getCredentials());
            if (password.equals(user.getPassword())) {
                return new SimpleAuthenticationInfo(username, password, "exampleRealm");
            } else {
                throw new IncorrectCredentialsException("-> [密码错误！] ...");
            }
        }
    }
}
