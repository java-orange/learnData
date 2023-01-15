package com.itheima.service.impl;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.apache.dubbo.config.annotation.Service;


//@Service//将该类的对象创建出来，放到Spring的IOC容器中  bean定义
//将这个类提供的方法（服务）对外发布。将访问的地址 ip，端口，路径注册到注册中心中
@Service(version = "v2.0")
public class UserServiceImpl2 implements UserService {

    public String sayHello() {
        return "hello dubbo hello!~";
    }


    public User findUserById(int id) {
        System.out.println("new....");
        //查询User对象
        User user = new User(1,"zhangsan","123");

        return user;
    }
}
