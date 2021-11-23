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

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.os.Bundle;

/**
 * This class is an implementation of AbstractAccountAuthenticator for
 * authenticating accounts in the com.example.android.samplesync domain. The
 * interesting thing that this class demonstrates is the use of authTokens as
 * part of the authentication process. In the account setup UI, the user enters
 * their username and password. But for our subsequent calls off to the service
 * for syncing, we want to use an authtoken instead - so we're not continually
 * sending the password over the wire. getAuthToken() will be called when
 * SyncAdapter calls AccountManager.blockingGetAuthToken(). When we get called,
 * we need to return the appropriate authToken for the specified account. If we
 * already have an authToken stored in the account, we return that authToken. If
 * we don't, but we do have a username and password, then we'll attempt to talk
 * to the sample service to fetch an authToken. If that fails (or we didn't have
 * a username/password), then we need to prompt the user - so we create an
 * AuthenticatorActivity intent and return that. That will display the dialog
 * that prompts the user for their login information.
 */

/**
 * 创建时间:  2016/09/07 <br>
 * 作者:  fujia <br>
 * 描述:  账号认证实现类
 */

class Authenticator extends AbstractAccountAuthenticator {

  /** The tag used to log to adb console. **/
  private static final String TAG = "Authenticator";

  // Authentication Service context
  private final Context mContext;

  public Authenticator(Context context) {
    super(context);
    mContext = context;
  }

  @Override public Bundle addAccount(AccountAuthenticatorResponse response, String accountType,
      String authTokenType, String[] requiredFeatures, Bundle options) {
    return null;
    //        final Intent intent = new Intent(mContext, AuthenticatorActivity.class);
    //        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, response);
    //        final Bundle bundle = new Bundle();
    //        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
    //        return bundle;
  }

  @Override public Bundle confirmCredentials(AccountAuthenticatorResponse response, Account account,
      Bundle options) {
    return null;
  }

  @Override
  public Bundle editProperties(AccountAuthenticatorResponse response, String accountType) {
    throw new UnsupportedOperationException();
  }

  @Override public Bundle getAuthToken(AccountAuthenticatorResponse response, Account account,
      String authTokenType, Bundle loginOptions) throws NetworkErrorException {
    return null;
  }

  @Override public String getAuthTokenLabel(String authTokenType) {
    // null means we don't support multiple authToken types
    return null;
  }

  @Override public Bundle hasFeatures(AccountAuthenticatorResponse response, Account account,
      String[] features) {
    // This call is used to query whether the Authenticator supports
    // specific features. We don't expect to get called, so we always
    // return false (no) for any queries.
    final Bundle result = new Bundle();
    result.putBoolean(AccountManager.KEY_BOOLEAN_RESULT, false);
    return result;
  }

  @Override public Bundle updateCredentials(AccountAuthenticatorResponse response, Account account,
      String authTokenType, Bundle loginOptions) {
    return null;
  }
}
