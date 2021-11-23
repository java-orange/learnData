package com.youdu.application;

import cn.jpush.android.api.JPushInterface;
import com.lianjia.pullalive.PullAlive;
import com.youdu.application.callback.CustomActivityLifecycleCallbacks;
import com.youdu.application.callback.CustomComponentCallback;
import com.youdu.core.AdSDKManager;
import com.youdu.service.andfix.AndFixPatchManager;
import com.youdu.share.ShareManager;
import com.youdu.util.Util;

/**
 * *******************************************************
 *
 * @文件名称：CommonApplication.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月19日 下午10:38:25
 * @文件描述：App容器,继成于Tinker为我们生成的CustomTinkerApplication
 * @修改历史：2015年11月19日创建初始版本 ********************************************************
 */
public class ImoocApplication extends CustomTinkerApplication {

  private static ImoocApplication mApplication = null;

  @Override public void onCreate() {
    super.onCreate();
    mApplication = this;
    initPullAlive();
    if (Util.isUIProcess(getPackageName())) {
      //所有只在UI进程中初始化的东西
      initShareSDK();
      initJPush();
      initAdSDK();
      initAndFix();
      //注册Activity生命周期事件
      registerActivityLifecycleCallback();
      //注册内存事件监听事件
      registerComponentCallback();
    } else {
      //只在非UI进程中初始化的東西,如果有多个其它非UI进程，则可以使用switch-case根据进程名来进行不同的初始化
    }
  }

  public static ImoocApplication getInstance() {
    return mApplication;
  }

  public void initShareSDK() {
    ShareManager.initSDK(this);
  }

  private void initJPush() {
    JPushInterface.setDebugMode(true);
    JPushInterface.init(this);
  }

  private void initAdSDK() {
    AdSDKManager.init(this);
  }

  /**
   * 热修复AndFix初始化
   */
  private void initAndFix() {
    AndFixPatchManager.getInstance().initPatch(this);
  }

  /**
   * 初始化保活进程，使App尽可能不被回收
   */
  private void initPullAlive() {
    PullAlive.start(this);
  }

  /**
   * 为Application注册ActivityCallbacks
   */
  private void registerActivityLifecycleCallback() {
    registerActivityLifecycleCallbacks(new CustomActivityLifecycleCallbacks());
  }

  /**
   * 为Application注册CommonentCallbacks
   */
  private void registerComponentCallback() {
    registerComponentCallbacks(new CustomComponentCallback());
  }

  @Override public void onLowMemory() {
    super.onLowMemory();
  }
}