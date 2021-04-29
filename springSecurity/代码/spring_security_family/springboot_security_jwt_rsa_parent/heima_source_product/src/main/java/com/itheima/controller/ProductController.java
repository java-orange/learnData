package com.itheima.controller;

import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

    @Secured("ROLE_PRODUCT")
    @RequestMapping("/findAll")
    public String findAll(){
        return "产品列表查询成功！";
    }

}
