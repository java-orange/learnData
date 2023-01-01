package com.atguigu.user.service;

import com.atguigu.bean.User;
import com.atguigu.user.feign.OrderFeignClient;
import com.atguigu.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

/*====================================================
                时间: 2022-06-21
                讲师: 刘  辉
                出品: 尚硅谷讲师团队
======================================================*/
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Transactional(readOnly = true)
    public Map<String,Object> getUserInfo(Integer id) {
        Map<String,Object> map = new HashMap<String,Object>();
        map.put("user", userMapper.getUserInfo(id));
        map.put("orderList", orderFeignClient.getOrderList(id));
       return map;
    }
}
