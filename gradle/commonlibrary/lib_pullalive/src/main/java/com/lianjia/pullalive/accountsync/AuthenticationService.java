package com.lianjia.pullalive.accountsync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  账号认证Service类
 */
public class AuthenticationService extends Service {
  private Authenticator mAuthenticator = null;

  @Override public void onCreate() {
    super.onCreate();
    mAuthenticator = new Authenticator(this);
  }

  @Override public IBinder onBind(Intent intent) {
    if (mAuthenticator == null) {
      return null;
    }

    return mAuthenticator.getIBinder();
  }
}
