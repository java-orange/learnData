package com.imooc.viewpagertest.retrofit;

import com.imooc.viewpagertest.module.realm.User;
import com.imooc.viewpagertest.module.search.BaseSearchModel;

import okhttp3.ResponseBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by qndroid on 16/10/22.
 *
 * @function 定义所有请求接口, 这部分是和业务相关的，不必封装到框架中
 */
public interface IRequestCenter {

    //可见对请求的构建十分的灵活
    @Headers({"Cache-Control: max-age=640000",
            "User-Agent: Retrofit-Sample-App"
    })
    @GET("fund/search.php")
    Observable<BaseSearchModel> getFunds(); //返回的是一个被观察者，通过它来对数据进行一系列的处理

    //采用rxjava完成
    @Headers("User-Agent: Retrofit-Sample-App")
    @POST("/user/login_phone.php")
    @FormUrlEncoded
    Observable<User> login(@Field("mb") String mobile, @Field("pwd") String pwd);

    @GET("/fund/upsearch.php")
    Observable<ResponseBody> upsearch();
}
