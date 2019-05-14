package com.kuhnwei.mohist.examples.shiro.service;


import com.kuhnwei.mohist.examples.shiro.domain.User;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/2 20:27
 */
public class UserService {
    private static final String USER_NAME = "kuhnwei";
    private static final String PASSWORD = "650901";
    public User getByUsername(final String username) {
        if (USER_NAME.equals(username)) {
            User user = new User();
            user.setUsername(USER_NAME);
            user.setPassword(PASSWORD);
            return user;
        }
        return null;
    }

    public Set<String> listRoleByUser(User user) {
        if (USER_NAME.equals(user.getUsername())
                && PASSWORD.equals(user.getPassword())) {
            Set<String> roles = new HashSet<String>(5);
            roles.add("admin");
            roles.add("user");
            return roles;
        }
        return null;
    }

    public Set<String> listPermissionsByUser(User user) {
        if (USER_NAME.equals(user.getUsername())
                && PASSWORD.equals(user.getPassword())) {
            Set<String> permissions = new HashSet<String>(5);
            permissions.add("admin:save");
            permissions.add("admin:remove");
            permissions.add("admin:list");
            permissions.add("admin:update");
            permissions.add("user:list");
            return permissions;
        }
        return null;
    }
}
