package com.atguigu.spring.service;

/**
 * Date:2022/7/6
 * Author:ybc
 * Description:
 */
public interface CheckoutService {

    /**
     * 结账
     * @param userId
     * @param bookIds
     */
    void checkout(Integer userId, Integer[] bookIds);
}
