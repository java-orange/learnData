package com.imooc.viewpagertest.realm;

import android.content.Context;

import io.realm.Realm;
import io.realm.RealmConfiguration;

/**
 * Created by renzhiqiang on 16/11/10.
 */

public class RealmManager {

    private static final String DB_NAME = "imooc.realm";
    private static Realm mRealm;

    public static Realm getRealm() {
        mRealm = Realm.getInstance(new RealmConfiguration
                .Builder()
                .name(DB_NAME) //指定数据库名字
                .build());
        return mRealm;
    }

    //负责初始化整个Relam数据库
    public static void init(Context context) {
        Realm.init(context);
    }

    //关闭Realm数据库
    public static void closeRealm() {
        if (mRealm != null && !mRealm.isClosed()) {
            mRealm.close();
        }
    }
}
