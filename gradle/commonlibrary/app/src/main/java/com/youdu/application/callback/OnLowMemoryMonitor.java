package com.youdu.application.callback;

import android.content.res.Configuration;

import java.util.ArrayList;

/**
 * Created by renzhiqiang on 17/4/5.
 */

public class OnLowMemoryMonitor {

    private static ArrayList<ComponentCallback> mAllCallbacks = new ArrayList<>();

    public static void registerComponentCallback(ComponentCallback callback) {
        synchronized (mAllCallbacks) {
            mAllCallbacks.add(callback);
        }
    }

    public static void unregisterComponentCallback(ComponentCallback callback) {
        synchronized (mAllCallbacks) {
            mAllCallbacks.remove(callback);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            //通知列表中的所有观察者
            for (ComponentCallback callback : mAllCallbacks) {
                callback.onConfigurationChanged(newConfig);
            }
        }
    }

    public void onLowMemory() {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ComponentCallback callback : mAllCallbacks) {
                callback.onLowMemory();
            }
        }
    }

    public interface ComponentCallback {

        void onConfigurationChanged(Configuration newConfig);

        void onLowMemory();
    }
}
