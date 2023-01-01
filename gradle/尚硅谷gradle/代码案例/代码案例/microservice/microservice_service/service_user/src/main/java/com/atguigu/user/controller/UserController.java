package com.atguigu.user.controller;

import com.atguigu.bean.User;
import com.atguigu.result.R;
import com.atguigu.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/*====================================================
                时间: 2022-06-21
                讲师: 刘  辉
                出品: 尚硅谷讲师团队
======================================================*/
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/info/{id}")
    public R getUserInfo(@PathVariable Integer id){
        Map<String, Object> userInfo = userService.getUserInfo(id);
        return R.ok().data(userInfo);
    }
}
