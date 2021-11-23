package com.lianjia.pullalive.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;
import com.lianjia.pullalive.Env;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  JobService拉活管理类
 */
public class PLJobServiceHelper {

  public static String TAG = "JobSchedulerPL";

  private static JobScheduler sJobScheduler;

  // 解决小米Note在7.0.6.0(KXECNCI)特定机器上的崩溃问题
  // 这个版本的rom在有的机器上exported开关无作用
  // 场景：设置exported="false"，在4.4的版本通过adb shell启动KLJobService仍可以使得程序崩溃
  // 解决办法：KLService的enable属性默认为"false",只有当当前安卓版本在5.0及以上的时候才将其置为"true"
  public static void handleJobScheduler(Context context) {
    if (context == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }

    // 为了防止Android版本在5.0及以上的ROM将JobService阉割掉,此处做try-catch处理.
    try {
      PackageManager pm = context.getPackageManager();
      if (pm == null) {
        if (Env.DEBUG) {
          Log.e(TAG, "packageManager为null");
        }
        return;
      }
      pm.setComponentEnabledSetting(new ComponentName(context, PLJobService.class),
          PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);

      scheduleJobs(context);
    } catch (Throwable t) {
      if (Env.DEBUG) {
        Log.e(TAG, "", t);
      }
    }
  }

  @TargetApi(21) public static final void scheduleJobs(Context context) {
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    // 周期唤醒
    schedule(context, PLJobService.PERIODIC_JOB_ID);
    // 充电唤醒
    schedule(context, PLJobService.CHARGING_JOB_ID);
    // 设备空闲唤醒
    schedule(context, PLJobService.IDLE_JOB_ID);
    // 不计费网络连接时唤醒
    schedule(context, PLJobService.UNMETERED_NETWORK_JOB_ID);
  }

  @TargetApi(21) public static final void schedule(Context context, int jobId) {
    if (context == null) {
      return;
    }
    if (Build.VERSION.SDK_INT < 21) {
      return;
    }
    if (Env.DEBUG) {
      Log.d(TAG, "scheduleJob(): JobId=" + jobId);
    }

    if (sJobScheduler == null) {
      sJobScheduler = (JobScheduler) context.getSystemService(context.JOB_SCHEDULER_SERVICE);
      if (sJobScheduler == null) {
        // 错误时输出日志
        if (Env.DEBUG) {
          Log.e(TAG, "jobScheduler == null");
        }
        return;
      }
    }

    JobInfo.Builder builder =
        new JobInfo.Builder(jobId, new ComponentName(context, PLJobService.class));
    switch (jobId) {
      case PLJobService.PERIODIC_JOB_ID:
        builder.setPeriodic(PLJobService.PERIODIC_TIME);
        break;
      case PLJobService.CHARGING_JOB_ID:
        builder.setMinimumLatency(PLJobService.DELAY_TIME);
        builder.setRequiresCharging(true);
        break;
      case PLJobService.IDLE_JOB_ID:
        builder.setMinimumLatency(PLJobService.DELAY_TIME);
        builder.setRequiresDeviceIdle(true);
        break;
      case PLJobService.UNMETERED_NETWORK_JOB_ID:
        builder.setMinimumLatency(PLJobService.DELAY_TIME);
        builder.setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY);
        break;
      default:
        break;
    }
    // 支持设备重启
    builder.setPersisted(true);
    int result = sJobScheduler.schedule(builder.build());
    if (result < 0) {
      // 错误时输出日志
      if (Env.DEBUG) {
        Log.e(TAG, "schedule error result < 0 result = " + result);
      }
    }
  }

  @TargetApi(Build.VERSION_CODES.LOLLIPOP) public static void canleAllJob() {
    if (sJobScheduler != null) {
      sJobScheduler.cancelAll();
    }
  }
}
