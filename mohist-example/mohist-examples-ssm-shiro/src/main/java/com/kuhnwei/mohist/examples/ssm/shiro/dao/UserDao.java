package com.kuhnwei.mohist.examples.ssm.shiro.dao;


import com.kuhnwei.mohist.examples.ssm.shiro.domain.UserDO;
import org.springframework.stereotype.Repository;

import java.util.Set;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 14:51
 */
@Repository
public interface UserDao {

    UserDO get(Integer id);

    UserDO getByUsername(String username);

    Set<String> listAllRoleByUserId(Integer userId);

    Set<String> listAllPermissionByUserId(Integer userId);

}
