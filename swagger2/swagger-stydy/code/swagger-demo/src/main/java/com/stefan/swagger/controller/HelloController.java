package com.stefan.swagger.controller;

import com.stefan.swagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping(value = "/hello")
    public String hello(){
        return "hello";
    }

    // 只要我们的接口中，返回值中存在实体类，就会被扫描到Swagger中
    @PostMapping(value = "/user")
    public User user(){
        return new User();
    }

    // ApiOperation不是放在类上的，是放在方法上面的
    @ApiOperation("hello方法")
    @GetMapping("/hello2")
    public String hello2(@ApiParam("用户名") String str){
        return "hello" + str;
    }

    @ApiOperation("post测试")
    @PostMapping("/testPro")
    public User testPro(@ApiParam("一个用户") User user){
        int i = 5/0;
        return user;
    }
}
