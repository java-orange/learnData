package com.atguigu.order;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/*====================================================
                时间: 2022-06-21
                讲师: 刘  辉
                出品: 尚硅谷讲师团队
======================================================*/
@SpringBootApplication
@ComponentScan("com.atguigu")
@MapperScan("com.atguigu.order.mapper")
@EnableDiscoveryClient
public class OrderMainStarterApp {

    public static void main(String[] args) {
        try {
            SpringApplication.run(OrderMainStarterApp.class,args);
        }catch (Exception ex){
            ex.printStackTrace();
        }

    }
}
