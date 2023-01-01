package com.atguigu.order.mapper;

import com.atguigu.bean.OrderInfo;
import com.atguigu.bean.User;
import org.apache.ibatis.annotations.Select;
import org.springframework.core.annotation.Order;

import java.util.List;

/*************************************************
 时间: 2022-06-21
 讲师: 刘  辉
 出品: 尚硅谷教学团队
 **************************************************/
public interface OrderMapper {

    @Select("select oid,uid,product_name productName from orderinfo where uid = #{uid}")
    public List<OrderInfo> getOrderList(Integer uid);
}
