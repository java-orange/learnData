package com.imooc.viewpagertest.retrofit;

import com.imooc.viewpagertest.module.realm.User;
import com.imooc.viewpagertest.module.search.BaseSearchModel;

import rx.Observable;

/**
 * Created by renzhiqiang on 16/10/25.
 *
 * @function 获取所有请求的Observable
 */

public class RequestCenter {

    public static Observable<BaseSearchModel> getFunds() {
        IRequestCenter requestCenter = CommonRetrofitClient.create(IRequestCenter.class);
        return requestCenter.getFunds();
    }

    //发送登陆请求
    public static Observable<User> login(String mobile, String pwd) {

        IRequestCenter requestCenter = CommonRetrofitClient.create(IRequestCenter.class);
        return requestCenter.login(mobile, pwd);
    }
}
