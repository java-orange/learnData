package com.atguigu.spring.aop.xml;

import org.springframework.stereotype.Component;

/**
 * @author maomao
 * @create 2022-07-24 17:25
 */
@Component

public class ValidateAspect {

    public void beforeMethod(){
        System.out.println("ValidateAspect-->前置通知");
    }
}
