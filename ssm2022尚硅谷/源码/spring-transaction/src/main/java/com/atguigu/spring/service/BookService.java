package com.atguigu.spring.service;

/**
 * Date:2022/7/6
 * Author:ybc
 * Description:
 */
public interface BookService {

    /**
     * 买书
     * @param userId
     * @param bookId
     */
    void buyBook(Integer userId, Integer bookId);
}
