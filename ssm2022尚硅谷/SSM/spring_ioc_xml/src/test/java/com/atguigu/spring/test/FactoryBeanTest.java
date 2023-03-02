package com.atguigu.spring.test;

import com.atguigu.spring.factory.UserFactoryBean;
import com.atguigu.spring.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author maomao
 * @create 2022-07-23 16:46
 */
public class FactoryBeanTest {

    @Test
    public void testUserFactoryBean() throws Exception {
        //获取IOC容器
        ApplicationContext ac = new ClassPathXmlApplicationContext("spring-factory.xml");
        User user = ac.getBean(User.class);
        System.out.println(user);
        UserFactoryBean bean = ac.getBean(UserFactoryBean.class);
        User object = bean.getObject();
        System.out.println("object = " + object);
    }

}
