package com.atguigu.spring.controller;

import com.atguigu.spring.service.UserService;

/**
 * @author maomao
 * @create 2022-07-23 16:57
 */
public class UserController {

    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }
    public void saveUser(){
        userService.saveUser();
    }

}
