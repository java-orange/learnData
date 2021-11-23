package com.youdu.service.notification;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.app.NotificationManagerCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 创建时间:  2017/11/06 11:30 <br>
 * 作者:  renzhiqiang <br>
 * 描述: 监听系统状态栏消息通知服务，必须要用户手动授权以后才可使用
 */
@TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2) public class CommonNotificationListenerService
    extends NotificationListenerService {

  private static final String TAG = CommonNotificationListenerService.class.getName();

  @Override public void onCreate() {
    super.onCreate();
    Log.e(TAG, "onCreate()");
  }

  @Override public int onStartCommand(Intent intent, int flags, int startId) {
    Log.e(TAG, "onStartCommand()");
    return super.onStartCommand(intent, flags, startId);
  }

  @Override public void onListenerConnected() {
    Log.e(TAG, "onListenerConnected()");
  }

  //有新Notification来时回调些方法
  @Override public void onNotificationPosted(StatusBarNotification sbn) {
    Log.e(TAG, "onNotificationPosted()");
    handleNotificationPost(sbn);
  }

  @Override public void onNotificationRemoved(StatusBarNotification sbn) {
    Log.e(TAG, "onNotificationRemoved()");
  }

  @Override public void onListenerDisconnected() {
    Log.e(TAG, "onListenerDisconnected()");
  }

  private void handleNotificationPost(StatusBarNotification sbn) {
    // 如果该通知的包名不是微信，那么 pass 掉
    if (!"com.tencent.mm".equals(sbn.getPackageName())) {
      return;
    }
    Notification notification = sbn.getNotification();
    if (notification == null) {
      return;
    }
    PendingIntent pendingIntent = null;
    // 当 API > 18 时，使用 extras 获取通知的详细信息
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      Bundle extras = notification.extras;
      if (extras != null) {
        // 获取通知标题
        String title = extras.getString(Notification.EXTRA_TITLE, "");
        // 获取通知内容
        String content = extras.getString(Notification.EXTRA_TEXT, "");
        if (!TextUtils.isEmpty(content) && content.contains("[微信红包]")) {
          pendingIntent = notification.contentIntent;
        }
      }
    } else {
      // 当 API = 18 时，利用反射获取内容字段
      List<String> textList = getText(notification);
      if (textList != null && textList.size() > 0) {
        for (String text : textList) {
          if (!TextUtils.isEmpty(text) && text.contains("[微信红包]")) {
            pendingIntent = notification.contentIntent;
            break;
          }
        }
      }
    }
    // 发送 pendingIntent 以此打开微信
    try {
      if (pendingIntent != null) {
        pendingIntent.send();
      }
    } catch (PendingIntent.CanceledException e) {
      e.printStackTrace();
    }
  }

  private List<String> getText(Notification notification) {
    if (null == notification) {
      return null;
    }
    RemoteViews views = notification.bigContentView;
    if (views == null) {
      views = notification.contentView;
    }
    if (views == null) {
      return null;
    }
    // Use reflection to examine the m_actions member of the given RemoteViews object.
    // It's not pretty, but it works.
    List<String> text = new ArrayList<>();
    try {
      Field field = views.getClass().getDeclaredField("mActions");
      field.setAccessible(true);
      @SuppressWarnings("unchecked") ArrayList<Parcelable> actions =
          (ArrayList<Parcelable>) field.get(views);
      // Find the setText() and setTime() reflection actions
      for (Parcelable p : actions) {
        Parcel parcel = Parcel.obtain();
        p.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        // The tag tells which type of action it is (2 is ReflectionAction, from the source)
        int tag = parcel.readInt();
        if (tag != 2) continue;
        // View ID
        parcel.readInt();
        String methodName = parcel.readString();
        if (null == methodName) {
          continue;
        } else if (methodName.equals("setText")) {
          // Parameter type (10 = Character Sequence)
          parcel.readInt();
          // Store the actual string
          String t = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel).toString().trim();
          text.add(t);
        }
        parcel.recycle();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return text;
  }

  /**
   * 判断当前应用是否有监听状态栏通知权限
   */
  public boolean isNotificationListenerEnabled(Context context) {
    Set<String> packageNames = NotificationManagerCompat.getEnabledListenerPackages(this);
    if (packageNames.contains(context.getPackageName())) {
      return true;
    }
    return false;
  }

  /**
   * 跳转到添加状态栏设置页面
   */
  public void openNotificationListenSettings() {
    try {
      Intent intent;
      if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP_MR1) {
        intent = new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS);
      } else {
        intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
      }
      startActivity(intent);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
