package com.itheima.service.impl;

import com.itheima.service.UserService;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    public String sayHello() {
        return "hello dubbo!~";
    }
}
