package com.atguigu.spring.test;

import com.atguigu.spring.controller.UserController;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author maomao
 * @create 2022-07-23 20:02
 */
public class IOCByAnnotationTest {

    @Test
    public void testAutowireByAnnotation(){
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-ioc-annotation.xml");
        UserController userController = ac.getBean(UserController.class);
//        System.out.println(userController);
//        UserService userService = ac.getBean(UserService.class);
//        System.out.println(userService);
//        UserDao userDao = ac.getBean(UserDao.class);
//        System.out.println(userDao);
        userController.saveUser();

    }
}
