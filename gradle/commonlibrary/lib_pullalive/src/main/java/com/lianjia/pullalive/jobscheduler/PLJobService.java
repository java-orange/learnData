package com.lianjia.pullalive.jobscheduler;

import android.annotation.TargetApi;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.util.Log;
import com.lianjia.pullalive.Env;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  JobService实现类
 */
@TargetApi(21) public class PLJobService extends JobService {
  public static final int PERIODIC_JOB_ID = 0x01;
  public static final int CHARGING_JOB_ID = 0x02;
  public static final int IDLE_JOB_ID = 0x03;
  public static final int UNMETERED_NETWORK_JOB_ID = 0x04;

  // 暂定唤醒周期为1分钟
  public static final long PERIODIC_TIME = 60 * 1000;
  // 推迟时间为1分钟
  public static final long DELAY_TIME = 60 * 1000;
  private static final String TAG = PLJobServiceHelper.TAG;
  private static final boolean DEBUG = Env.DEBUG;

  public void onStartJobImpl(int jobId) {
    switch (jobId) {
      case PERIODIC_JOB_ID:
        break;
      default:
        PLJobServiceHelper.schedule(getApplicationContext(), jobId);
        break;
    }
  }

  @Override public void onCreate() {
    super.onCreate();
    if (DEBUG) {
      Log.i(TAG, "---PLJobService onCreate---");
    }
  }

  @Override public boolean onStartJob(JobParameters params) {
    if (DEBUG) {
      Log.i(TAG, "---PLJobService start---" + params.getJobId());
    }
    onStartJobImpl(params.getJobId());
    return false;
  }

  @Override public boolean onStopJob(JobParameters params) {
    return false;
  }
}
