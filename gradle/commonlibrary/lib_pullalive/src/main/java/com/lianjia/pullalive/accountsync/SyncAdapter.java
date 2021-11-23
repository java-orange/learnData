/*
 * Copyright (C) 2010 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.lianjia.pullalive.accountsync;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.AbstractThreadedSyncAdapter;
import android.content.ContentProviderClient;
import android.content.Context;
import android.content.SyncResult;
import android.os.Bundle;

/**
 * SyncAdapter implementation for syncing sample SyncAdapter contacts to the
 * platform ContactOperations provider.  This sample shows a basic 2-way
 * sync between the client and a sample server.  It also contains an
 * example of how to update the contacts' status messages, which
 * would be useful for a messaging or social networking client.
 */

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  账号同步Adapter实现类
 */
public class SyncAdapter extends AbstractThreadedSyncAdapter {

  private static final String TAG = "SyncAdapter";
  private static final String SYNC_MARKER_KEY = "com.example.android.samplesync.marker";
  private static final boolean NOTIFY_AUTH_FAILURE = true;

  private final AccountManager mAccountManager;

  private final Context mContext;

  public SyncAdapter(Context context, boolean autoInitialize) {
    super(context, autoInitialize);
    mContext = context;
    mAccountManager = AccountManager.get(context);
  }

  @Override public void onPerformSync(Account account, Bundle extras, String authority,
      ContentProviderClient provider, SyncResult syncResult) {
  }

  /**
   * This helper function fetches the last known high-water-mark
   * we received from the server - or 0 if we've never synced.
   *
   * @param account the account we're syncing
   * @return the change high-water-mark
   */
  private long getServerSyncMarker(Account account) {
    //        String markerString = mAccountManager.getUserData(account, SYNC_MARKER_KEY);
    //        if (!TextUtils.isEmpty(markerString)) {
    //            return Long.parseLong(markerString);
    //        }
    return 0;
  }

  /**
   * Save off the high-water-mark we receive back from the server.
   *
   * @param account The account we're syncing
   * @param marker The high-water-mark we want to save.
   */
  private void setServerSyncMarker(Account account, long marker) {
    // mAccountManager.setUserData(account, SYNC_MARKER_KEY, Long.toString(marker));
  }
}

