package com.atguigu.user.mapper;

import com.atguigu.bean.User;
import org.apache.ibatis.annotations.Select;

/*************************************************
 时间: 2022-06-21
 讲师: 刘  辉
 出品: 尚硅谷教学团队
 **************************************************/
public interface UserMapper {

    @Select("select id,username,email,gender from user where id = #{id}")
    public User getUserInfo(Integer id);
}
