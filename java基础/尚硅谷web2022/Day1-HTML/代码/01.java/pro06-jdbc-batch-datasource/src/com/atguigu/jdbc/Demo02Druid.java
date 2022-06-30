package com.atguigu.jdbc;

import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

//验证连接池中的connection可以重复使用
public class Demo02Druid {
    public static void main(String[] args) throws SQLException {

        DruidDataSource dataSource = new DruidDataSource();

        dataSource.setDriverClassName("org.gjt.mm.mysql.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/fruitdb?useSSL=false&useUnicode=true&characterEncoding=utf-8");
        dataSource.setUsername("root");
        dataSource.setPassword("123456");

        //证明两点：
        //1. 被close的连接对象并没有真正关闭，而是将状态重新设置为空闲状态，然后放回池子，这样下次获取连接对象，这个对象会被重复使用
        //2. 没有close的连接对象会被一直占用，那么下次继续获取连接对象，是不会获取到这个对象的（hashcode没有重复，只出现一次）
        for(int i = 0 ; i<5 ; i++){
            Connection conn1 = dataSource.getConnection();
            Connection conn2 = dataSource.getConnection();

            System.out.println(conn1);
            System.out.println(conn2);

            if(i%3==0){
                conn1.close();
                conn2.close();
            }
        }
    }
}
