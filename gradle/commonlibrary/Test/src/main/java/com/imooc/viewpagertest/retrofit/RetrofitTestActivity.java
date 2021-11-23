package com.imooc.viewpagertest.retrofit;

import android.app.ProgressDialog;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import com.imooc.viewpagertest.R;
import com.imooc.viewpagertest.module.realm.User;
import com.imooc.viewpagertest.module.search.BaseSearchModel;
import com.imooc.viewpagertest.realm.RealmManager;
import io.realm.Realm;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Retrofit的基本用法
 *
 * @into:使用gson定义对应的实体对象的时候与使用我们自己的工具类完全一样。
 */
public class RetrofitTestActivity extends AppCompatActivity {

  Realm realm;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle(getSystemString());
    setContentView(R.layout.activity_retrofit_test);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    //创建Realm数据库
    realm = RealmManager.getRealm();
    flatMapTest();
    //login();

  }

  private int step = 0;
  private Subscription loginSubscription;
  private ProgressDialog dialog;

  private void login() {
    loginSubscription =
        RequestCenter.login("18734924592", "999999q").subscribeOn(Schedulers.io()) //指定请求发生在IO线程
            .doOnSubscribe(new Action0() {  //执行在事件产生之前
              @Override public void call() {
                dialog = new ProgressDialog(RetrofitTestActivity.this);
                dialog.show();
              }
            }).subscribeOn(AndroidSchedulers.mainThread()) //保证上一方法执行在主线程中
            .observeOn(AndroidSchedulers.mainThread()) //指定后面的操作在UI线程
            .subscribe(new Action1<User>() {
              @Override public void call(final User user) {
                dialog.dismiss();
                //将数据存储到Relam中
                //写入数据库，写操作必须在事务中，可以分同步事务和异步事务两种
                //从Realm数据库查询
                realm.executeTransaction(new Realm.Transaction() {
                  @Override public void execute(Realm realm) {
                    realm.copyToRealm(user); //成功的将数据存入到了数据库。
                  }
                });
                //读取操作不需要放在事务中。
                User user2 = realm.where(User.class).findFirst();
                setTitle("从数据库取到的名字为:" + user2.data.name);
              }
            }, new Action1<Throwable>() {
              @Override public void call(Throwable throwable) {
                Log.e("error", throwable.getMessage());
              }
            });
  }

  private void flatMapTest() {
    loginSubscription = RequestCenter.getFunds().subscribeOn(Schedulers.io()) //指定请求发生在IO线程
        .doOnSubscribe(new Action0() {  //执行在事件产生之前
          @Override public void call() {
            dialog = new ProgressDialog(RetrofitTestActivity.this);
            dialog.show();
          }
        }).subscribeOn(AndroidSchedulers.mainThread()) //保证上一方法执行在主线程中
        .observeOn(AndroidSchedulers.mainThread()) //指定后面的操作在UI线程
        .subscribe(new Action1<BaseSearchModel>() {
          @Override public void call(final BaseSearchModel responseBody) {
            dialog.dismiss();
            setTitle(responseBody.data.list.get(0).abbrev + "");

            //使用Realm将复杂的数据对象直接存入数据库
            realm.executeTransaction(new Realm.Transaction() {
              @Override public void execute(Realm realm) {
                realm.copyToRealm(responseBody);
              }
            });
            //查找對應的數據对象。
            setTitle(realm.where(BaseSearchModel.class).findAll().size() + "XX");
          }
        }, new Action1<Throwable>() {
          @Override public void call(Throwable throwable) {
            dialog.dismiss();
            Log.e("activity", throwable.getMessage());
          }
        });
  }

  @Override protected void onDestroy() {
    super.onDestroy();
    loginSubscription.unsubscribe();
    realm.close();
  }

  private String getSystemString() {

    int ledId =
        Resources.getSystem().getIdentifier("permlab_accessNetworkState", "string", "android");

    return getString(ledId);
  }
}
