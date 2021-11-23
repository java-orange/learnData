package com.youdu.application.callback;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

/**
 * Created by renzhiqiang on 17/4/5.
 *
 * @function: 接收系统对于Activity生命周期的通知
 */
public class CustomActivityLifecycleCallbacks implements Application.ActivityLifecycleCallbacks {

  OnActivityLifecycleMonitor mOnLineMonitor;

  public CustomActivityLifecycleCallbacks() {

    mOnLineMonitor = new OnActivityLifecycleMonitor();
  }

  @Override public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    mOnLineMonitor.onActivityCreated(activity, savedInstanceState);
  }

  @Override public void onActivityStarted(Activity activity) {

    mOnLineMonitor.onActivityStarted(activity);
  }

  @Override public void onActivityResumed(Activity activity) {
    mOnLineMonitor.onActivityResumed(activity);
  }

  @Override public void onActivityPaused(Activity activity) {
    mOnLineMonitor.onActivityPaused(activity);
  }

  @Override public void onActivityStopped(Activity activity) {
    mOnLineMonitor.onActivityStopped(activity);
  }

  @Override public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
    mOnLineMonitor.onActivitySaveInstanceState(activity, outState);
  }

  @Override public void onActivityDestroyed(Activity activity) {
    mOnLineMonitor.onActivityDestroyed(activity);
  }
}
