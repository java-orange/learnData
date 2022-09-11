package com.atguigu.spring.test;

import com.atguigu.spring.controller.UserController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author maomao
 * @create 2022-07-23 17:11
 */
public class AutowireByXMLTest {

    @Test
    public void testAutoWireByXML(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-autowire-xml.xml");
        UserController userController = ac.getBean(UserController.class);
        userController.saveUser();
    }
}
