package com.atguigu.spring.dao;

/**
 * @author maomao
 * @create 2022-07-24 20:23
 */
public interface BookDao {

    Integer getPriceByBookId(Integer bookId);
    void updateStock(Integer bookId);
    void updateBalance(Integer userId, Integer price);
}
