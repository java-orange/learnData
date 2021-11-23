package com.imooc.viewpagertest.realm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by renzhiqiang on 16/11/9.
 */

public class RealmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //testRealm();
    }

    //  private void testRealm() {
    //创建Realm数据库
//        Realm realm =
//                Realm.getInstance(
//                        new RealmConfiguration.Builder()
//                                .name("test.realm") //指定数据库名字
//                                .build());
//        User user = new User();
//        user.setAge(12);
//        user.setName("xiaoqiang");
//        Email email = new Email();
//        email.address = "277451977@qq.com";
//        user.emails.add(email);      //直接将一对多的对象关系存储到了数据库中。
//
//        //写入数据库，写操作必须在事务中，可以分同步事务和异步事务两种
//        realm.beginTransaction();
//        final User insertUser = realm.copyToRealm(user); // Persist unmanaged objects
//        final User insertUser2 = realm.copyToRealm(user); // Persist unmanaged objects
//        insertUser2.setName("xiaogou");
//        realm.commitTransaction();
//
//        //从Realm数据库查询
//        User user2 = realm.where(User.class).findFirst();
//        Log.e("RealmActivity", user2.emails.get(0).address + "XXX");
//    }
}
