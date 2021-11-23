package com.imooc.viewpagertest.application;

import android.app.Application;

import com.imooc.viewpagertest.realm.RealmManager;

import io.realm.Realm;

/**
 * Created by renzhiqiang on 16/11/9.
 */

public class TestApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        initRealmDB();
    }

    //初始化Relam数据库
    private void initRealmDB() {
        RealmManager.init(this);
    }
}
