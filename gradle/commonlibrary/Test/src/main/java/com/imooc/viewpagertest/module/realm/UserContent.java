package com.imooc.viewpagertest.module.realm;


import io.realm.RealmModel;
import io.realm.annotations.RealmClass;

/**
 * Created by renzhiqiang on 15/11/20.
 */
@RealmClass
public class UserContent implements RealmModel {

    public String userId = "0006"; //用户唯一标识符
    public String photoUrl;
    public String name;
    public String tick;
    public String mobile;
    public String passwd;
    public String email;
    public String parent;
    public int sex;
}
