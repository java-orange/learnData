package com.imooc.viewpagertest.retrofit;

import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by qndroid on 16/10/25.
 * @function 类似于一个中间件,
 * 底层请求使用的是okhttp,而API层可支持RxJava
 */

public class CommonRetrofitClient {
    private static final int TIME_OUT = 60;
    private static String BASE_URL = "https://i.qianjing.com/";
    private static Retrofit mRetrofit; //类似于okhttpclient

    static {
        Retrofit.Builder retrofitBuilder = new
                Retrofit.Builder();
        retrofitBuilder
                .callFactory(initOkHttpClitent())
                .baseUrl(BASE_URL) //基本url
                .addConverterFactory(GsonConverterFactory.create()) //会自动的将json转为实体对象
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create()) //将Call对象转化为rx的Observable,必须有
        ;
        mRetrofit = retrofitBuilder.build();
    }

    private static OkHttpClient initOkHttpClitent() {
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.hostnameVerifier(new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        });
//        okHttpClientBuilder.addInterceptor(new Interceptor() {
//            @Override
//            public Response intercept(Chain chain) throws IOException {
//                Request request = chain.request();
//                return chain.proceed(request);
//            }
//        });
        okHttpClientBuilder.connectTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.readTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.writeTimeout(TIME_OUT, TimeUnit.SECONDS);
        okHttpClientBuilder.followRedirects(true);
        /**
         * trust all the https point, 通过测试，适配到了okhttp3.3.0版本
         */
        okHttpClientBuilder.sslSocketFactory(initSSLSocketFactory(), initTrustManager());
        return okHttpClientBuilder.build();
    }

    public static <T> T create(Class<T> clazz) {
        return mRetrofit.create(clazz);
    }

    private static SSLSocketFactory initSSLSocketFactory() {
        SSLContext sslContext = null;
        try {
            sslContext = SSLContext.getInstance("SSL");
            X509TrustManager[] xTrustArray = new X509TrustManager[]
                    {initTrustManager()};
            sslContext.init(null,
                    xTrustArray, new SecureRandom());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sslContext.getSocketFactory();
    }

    private static X509TrustManager initTrustManager() {
        X509TrustManager mTrustManager = new X509TrustManager() {
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[]{};
            }

            @Override
            public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }

            @Override
            public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            }
        };
        return mTrustManager;
    }
}
