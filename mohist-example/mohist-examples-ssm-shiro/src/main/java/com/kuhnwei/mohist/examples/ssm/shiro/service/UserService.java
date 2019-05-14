package com.kuhnwei.mohist.examples.ssm.shiro.service;

import com.kuhnwei.mohist.examples.ssm.shiro.dao.UserDao;
import com.kuhnwei.mohist.examples.ssm.shiro.domain.UserDO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 15:09
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public UserDO get(Integer id) {
        return userDao.get(id);
    }

    public UserDO getByUsername(String username) {
        return userDao.getByUsername(username);
    }

    public Map<String, Object> listAuthByUserId(Integer userId) {
        Map<String, Object> map = new HashMap<String, Object>(16);
        map.put("allRoles", userDao.listAllRoleByUserId(userId));
        map.put("allPermissions", userDao.listAllPermissionByUserId(userId));
        return map;
    }

}
