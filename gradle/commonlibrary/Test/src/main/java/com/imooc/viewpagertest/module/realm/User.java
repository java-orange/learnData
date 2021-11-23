package com.imooc.viewpagertest.module.realm;


import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by renzhiqiang on 16/11/9.
 */
@RealmClass
public class User implements RealmModel {

    public int ecode;
    public String emsg;
    public UserContent data;
}
