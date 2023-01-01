package com.atguigu.user.feign;

import com.atguigu.bean.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/*************************************************
 时间: 2022-06-21
 讲师: 刘  辉
 出品: 尚硅谷教学团队
 **************************************************/
@FeignClient(value = "service-order")
public interface OrderFeignClient {

    @GetMapping("/order/info/{id}")
    public List<OrderInfo> getOrderList(@PathVariable("id") Integer id);
}
