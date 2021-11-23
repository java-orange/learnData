package com.lianjia.pullalive.accountsync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import com.lianjia.pullalive.Env;
import com.lianjia.pullalive.R;

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  账号同步拉活管理类
 */

public class AccountHelper {

  public static final String TAG = "AccountPL";
  public static final String AUTHORITY = "com.youdu.accountsync.syncprovider";
  public static final String ACCOUNT_TYPE = "com.youdu.accountsync";
  private static final long PERIODIC_SYNC = 60 * 1; // 账号同步时间间隔 单位是秒

  public static boolean addAccount(Context context) {

    if (context == null) {
      return false;
    }
    String accountName = context.getResources().getString(R.string.app_name_pullalive);
    boolean bRet = true;
    try {
      // 如果同步总开关关闭 则先开总开关
      if (!ContentResolver.getMasterSyncAutomatically()) {
        ContentResolver.setMasterSyncAutomatically(true);
      }

      Account account = new Account(accountName, ACCOUNT_TYPE);
      AccountManager accountManager = AccountManager.get(context);
      if (!isAccountExist(accountManager, accountName)) {
        boolean r = accountManager.addAccountExplicitly(account, "", null);
        if (Env.DEBUG) {
          Log.d(TAG, "addAccount = " + r);
        }
      } else {
        if (Env.DEBUG) {
          Log.d(TAG, "Account already exist");
        }
      }

      ContentResolver.setSyncAutomatically(account, AUTHORITY, true);
      ContentResolver.addPeriodicSync(account, AUTHORITY, new Bundle(), PERIODIC_SYNC);
    } catch (Exception e) {
      bRet = false;
      if (Env.DEBUG) {
        Log.e(TAG, "", e);
      }
    }
    return bRet;
  }

  private static boolean isAccountExist(AccountManager accountManager, String name) {

    if (accountManager == null || TextUtils.isEmpty(name)) {
      return false;
    }

    Account[] accounts = accountManager.getAccountsByType(ACCOUNT_TYPE);
    if (accounts == null) {
      return false;
    }

    boolean bExist = false;
    for (Account account : accounts) {
      if (account != null && name.equalsIgnoreCase(account.name)) {
        bExist = true;
        break;
      }
    }

    return bExist;
  }

  public static boolean removeAccount(Context context) {

    if (context == null) {
      return false;
    }
    String accountName = context.getResources().getString(R.string.app_name_pullalive);
    boolean bRet = true;
    try {
      AccountManager mAccountManager = AccountManager.get(context);
      if (isAccountExist(mAccountManager, accountName)) {
        Account account = new Account(accountName, ACCOUNT_TYPE);
        // 关闭同步
        ContentResolver.setSyncAutomatically(account, AUTHORITY, false);
        ContentResolver.removePeriodicSync(account, AUTHORITY, new Bundle());
        // 删除账号
        mAccountManager.removeAccount(account, null, null);
        if (Env.DEBUG) {
          Log.d(TAG, "removeAccount");
        }
      } else {
        if (Env.DEBUG) {
          Log.d(TAG, "Account not exist");
        }
      }
    } catch (Exception e) {
      bRet = false;
      if (Env.DEBUG) {
        Log.e(TAG, "", e);
      }
    }
    return bRet;
  }
}
