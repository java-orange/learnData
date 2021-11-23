package com.lianjia.pullalive;

import android.content.Context;
import android.os.Build;
import com.lianjia.pullalive.accountsync.AccountHelper;
import com.lianjia.pullalive.jobscheduler.PLJobServiceHelper;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  拉活的管理类, 负责拉活的启动和取消
 */

public class PullAlive {

  private PullAlive() {
    throw new IllegalStateException("Do not need instantiate!");
  }

  /**
   * 启动拉活
   */
  public static void start(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      PLJobServiceHelper.handleJobScheduler(context);
    } else {
      AccountHelper.addAccount(context);
    }
  }

  public static void cancel(Context context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      PLJobServiceHelper.canleAllJob();
    } else {
      AccountHelper.removeAccount(context);
    }
  }
}
