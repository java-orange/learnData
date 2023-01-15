package com.itheima.service.impl;

import com.itheima.pojo.User;
import com.itheima.service.UserService;
import org.apache.dubbo.config.annotation.Service;


//@Service//将该类的对象创建出来，放到Spring的IOC容器中  bean定义
//将这个类提供的方法（服务）对外发布。将访问的地址 ip，端口，路径注册到注册中心中
@Service(timeout = 3000,retries = 2)//当前服务3秒超时,重试2次，一共3次
public class UserServiceImpl implements UserService {

    int i = 1;
    public String sayHello() {
        return "hello dubbo hello!~";
    }


    public User findUserById(int id) {
        System.out.println("服务被调用了："+i++);
        //查询User对象
        User user = new User(1,"zhangsan","123");
        //数据库查询很慢，查了5秒

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return user;
    }
}
