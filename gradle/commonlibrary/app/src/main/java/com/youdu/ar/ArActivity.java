//================================================================================================================================
//
//  Copyright (c) 2015-2017 VisionStar Information Technology (Shanghai) Co., Ltd. All Rights Reserved.
//  EasyAR is the registered trademark or trademark of VisionStar Information Technology (Shanghai) Co., Ltd in China
//  and other countries for the augmented reality technology developed by VisionStar Information Technology (Shanghai) Co., Ltd.
//
//================================================================================================================================

package com.youdu.ar;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import cn.easyar.Engine;
import com.youdu.R;
import java.util.HashMap;

public class ArActivity extends AppCompatActivity {
  /*
  * 必须使用自己应用包名对应的key
  */
  private static String key =
      "blosqUyvDvUcS9qeqWTtVfI4DiPXF87yJAVS8JdX313o94yQvkH1B8j9G4aQDia4fWRMiN2D5ECr1lUvNLdG4Fu51vwFWIOwxzql2xKrDF6431U1bMDv7C2XkziFvvMQ6orF3D1QHDcBgyllFYNjFfXbRwsIcB3EDFw9Lm0NZrEExvleOj6vmlCVwSw56dPBVNR4piGK";
  private GLView glView;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ar_layout);
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON,
        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

    if (!Engine.initialize(this, key)) {
      Log.e("ARManager", "Initialization Failed.");
    }

    glView = new GLView(this);

    requestCameraPermission(new PermissionCallback() {
      @Override public void onSuccess() {
        ((ViewGroup) findViewById(R.id.preview)).addView(glView,
            new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
      }

      @Override public void onFailure() {
      }
    });
  }

  private interface PermissionCallback {
    void onSuccess();

    void onFailure();
  }

  private HashMap<Integer, PermissionCallback> permissionCallbacks =
      new HashMap<Integer, PermissionCallback>();
  private int permissionRequestCodeSerial = 0;

  @TargetApi(23) private void requestCameraPermission(PermissionCallback callback) {
    if (Build.VERSION.SDK_INT >= 23) {
      if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
        int requestCode = permissionRequestCodeSerial;
        permissionRequestCodeSerial += 1;
        permissionCallbacks.put(requestCode, callback);
        requestPermissions(new String[] { Manifest.permission.CAMERA }, requestCode);
      } else {
        callback.onSuccess();
      }
    } else {
      callback.onSuccess();
    }
  }

  @Override public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
      @NonNull int[] grantResults) {
    if (permissionCallbacks.containsKey(requestCode)) {
      PermissionCallback callback = permissionCallbacks.get(requestCode);
      permissionCallbacks.remove(requestCode);
      boolean executed = false;
      for (int result : grantResults) {
        if (result != PackageManager.PERMISSION_GRANTED) {
          executed = true;
          callback.onFailure();
        }
      }
      if (!executed) {
        callback.onSuccess();
      }
    }
    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
  }

  @Override protected void onResume() {
    super.onResume();
    if (glView != null) {
      glView.onResume();
    }
  }

  @Override protected void onPause() {
    if (glView != null) {
      glView.onPause();
    }
    super.onPause();
  }
}
