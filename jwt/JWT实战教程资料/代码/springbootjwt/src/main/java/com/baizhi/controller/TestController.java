package com.baizhi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class TestController {

    @GetMapping("/test/test")
    public String test(String username, HttpServletRequest request){
        //认证成功之后放入session
        request.getSession().setAttribute("username",username);
        return "login ok~";
    }
}
