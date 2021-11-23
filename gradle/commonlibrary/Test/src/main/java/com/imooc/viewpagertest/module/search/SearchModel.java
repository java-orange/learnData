package com.imooc.viewpagertest.module.search;


import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * @author: vision
 * @function:
 * @date: 16/8/12
 */
@RealmClass
public class SearchModel implements RealmModel {

    public long uptime;
    //将服务端返回的json直接转为RealmList，方便直接全部存入数据库，
    // 但是我们的代码对Relam的依赖就比较大了，有得有失。不需要存的对象尽量不要依赖Relam
    public RealmList<ProductModel> list;
}
