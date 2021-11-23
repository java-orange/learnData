package com.lianjia.pullalive.accountsync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;
import com.lianjia.pullalive.Env;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  账号同步Service
 */
public class SyncService extends Service {

  private static Object sSyncAdapterLock = new Object();
  private static SyncAdapter sSyncAdapter = null;

  @Override public void onCreate() {
    super.onCreate();
    if (Env.DEBUG) {
      Log.w(AccountHelper.TAG, "SyncService onCreate");
    }

    synchronized (sSyncAdapterLock) {
      if (sSyncAdapter == null) {
        sSyncAdapter = new SyncAdapter(getApplicationContext(), true);
      }
    }
  }

  @Override public IBinder onBind(Intent intent) {
    if (Env.DEBUG) {
      Log.w(AccountHelper.TAG, "SyncService onBinder");
    }
    if (sSyncAdapter == null) {
      return null;
    }

    return sSyncAdapter.getSyncAdapterBinder();
  }

  @Override public void onDestroy() {
    if (Env.DEBUG) {
      Log.w(AccountHelper.TAG, "SyncService onDestroy");
    }
    super.onDestroy();
  }
}
