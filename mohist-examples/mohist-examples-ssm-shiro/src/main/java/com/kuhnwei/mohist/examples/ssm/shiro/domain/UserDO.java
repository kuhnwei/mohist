package com.kuhnwei.mohist.examples.ssm.shiro.domain;

import java.io.Serializable;

/**
 * @author Kuhn Wei
 * @email email@kuhnwei.com
 * @created 2018/2/27 14:50
 */
public class UserDO implements Serializable {

    private Integer id;
    private String username;
    private String password;

    public UserDO() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
