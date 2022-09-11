package com.atguigu.spring.service;

/**
 * @author maomao
 * @create 2022-07-24 22:15
 */
public interface CheckoutService {
    void checkout(Integer[] bookIds, Integer userId);
}
