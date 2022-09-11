package com.atguigu.spring.test;

import com.atguigu.spring.pojo.Student;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author maomao
 * @create 2022-07-22 21:42
 */
public class IOCByXMLTest {

    @Test
    public void testIOC(){

        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        Student studentOne = (Student) ioc.getBean("studentOne");
        System.out.println(studentOne);
    }
    @Test
    public void testDI(){

        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring-ioc.xml");
        Student student = ioc.getBean("studentSix", Student.class);
        System.out.println(student);
//        Clazz clazz = ioc.getBean("clazzOne", Clazz.class);
//        System.out.println(clazz);
    }
}
