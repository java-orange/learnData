package com.youdu.application.callback;

import android.content.ComponentCallbacks;
import android.content.res.Configuration;

/**
 * Created by renzhiqiang on 17/4/5.
 */

public class CustomComponentCallback implements ComponentCallbacks {

    OnLowMemoryMonitor mOnLowMemoryMonitor;

    public CustomComponentCallback() {

        mOnLowMemoryMonitor = new OnLowMemoryMonitor();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        mOnLowMemoryMonitor.onConfigurationChanged(newConfig);
    }

    @Override
    public void onLowMemory() {
        mOnLowMemoryMonitor.onLowMemory();
    }
}
