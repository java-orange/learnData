package com.atguigu.controller;

import com.atguigu.service.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

/**
 * @author maomao
 * @create 2022-07-27 19:43
 */
@Controller
public class HelloController {
    @Autowired
    private HelloService helloService;
}
