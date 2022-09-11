package com.atguigu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author maomao
 * @create 2022-07-25 15:29
 */
@Controller
//@RequestMapping("/test")
public class TestRequestMappingController {

    @RequestMapping(
            value = {"/hello","/abc"},
            method = {RequestMethod.GET, RequestMethod.POST},
            params = {"username","password!=123456"}
    )
    public String hello(){
        return "success";
    }
    @RequestMapping("/test/{username}/{password}")
    public String testRest(@PathVariable("username") String username,@PathVariable("password") Integer password){
        System.out.println("username:"+username);
        System.out.println("password:"+password);
        return "success";
    }
}
