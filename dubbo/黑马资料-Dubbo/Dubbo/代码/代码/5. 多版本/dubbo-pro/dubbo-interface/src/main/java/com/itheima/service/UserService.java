package com.itheima.service;

import com.itheima.pojo.User;

public interface UserService {


    public String sayHello();

    /**
     * 查询用户
     */
    public User findUserById(int id);
}
