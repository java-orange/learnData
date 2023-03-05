package com.atguig.ehcache;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import java.io.InputStream;

public class TestEH {
    public static void main(String[] args) {
        //获取编译目录下的资源的流对象
        InputStream input =
                TestEH.class.getClassLoader().getResourceAsStream("ehcache.xml");
        //获取EhCache的缓存管理对象
        CacheManager cacheManager = new CacheManager(input);
        //获取缓存对象
        Cache cache = cacheManager.getCache("HelloWorldCache");
        //创建缓存数据
        Element element = new Element("name","zhang3");
        //存入缓存
        cache.put(element);
        //从缓存中取出数据输出
        Element element1 = cache.get("name");
        System.out.println("缓存中数据 = " + element1.getObjectValue());

    }
}
