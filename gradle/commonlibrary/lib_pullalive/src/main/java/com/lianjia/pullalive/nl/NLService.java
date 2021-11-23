package com.lianjia.pullalive.nl;

import android.annotation.TargetApi;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import com.lianjia.pullalive.Env;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  通知栏使用权Service实现类
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) public class NLService
    extends NotificationListenerService {

  private static final String TAG = "NLService";

  public static boolean openNLSetting(Context context) {
    try {
      Intent t = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
      t.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      context.startActivity(t);
      return true;
    } catch (Throwable t) {
      return false;
    }
  }

  public static boolean openAutoStartSetting(Context context) {
    ComponentName componentName = new ComponentName("com.miui.securitycenter",
        "com.miui.permcenter.autostart.AutoStartManagementActivity");
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setComponent(componentName);
    try {
      context.startActivity(intent);
    } catch (Throwable t) {
      componentName =
          new ComponentName("com.miui.securitycenter", "com.miui.permcenter.MainAcitivty");
      intent.setComponent(componentName);
      try {
        context.startActivity(intent);
      } catch (Throwable t2) {
        return false;
      }
    }
    return true;
  }

  @Override public void onCreate() {
    super.onCreate();
    if (Env.DEBUG) {
      Log.d(TAG, "NLService onCreate ");
    }
  }

  @Override public IBinder onBind(Intent intent) {
    if (Env.DEBUG) {
      Log.d(TAG, "NLService onBind ");
    }
    return super.onBind(intent);
  }

  @Override public void onDestroy() {
    if (Env.DEBUG) {
      Log.d(TAG, "NLService onDestroy ");
    }
    super.onDestroy();
  }

  //接收到新的通知栏消息事件
  @Override public void onNotificationPosted(StatusBarNotification sbn) {

  }

  //接收到通知栏消息被移除事件
  @Override public void onNotificationRemoved(StatusBarNotification sbn) {

  }
}
