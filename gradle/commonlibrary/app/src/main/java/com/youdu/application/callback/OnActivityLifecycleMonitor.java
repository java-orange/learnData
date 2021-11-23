package com.youdu.application.callback;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;

/**
 * Created by renzhiqiang on 17/4/5.
 * @function: Activity生命周期事件源,采用观察者模式
 */
public class OnActivityLifecycleMonitor {

    private static ArrayList<ActivityLifeCallback> mAllCallbacks = new ArrayList<>();

    public static void registerActivityLifeCallback(ActivityLifeCallback callback) {
        synchronized (mAllCallbacks) {
            mAllCallbacks.add(callback);
        }
    }

    public static void unregisterActivityLifeCallback(ActivityLifeCallback callback) {
        synchronized (mAllCallbacks) {
            mAllCallbacks.remove(callback);
        }
    }

    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityCreated(activity, savedInstanceState);
            }
        }
    }

    public void onActivityStarted(Activity activity) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityStarted(activity);
            }
        }
    }

    public void onActivityResumed(Activity activity) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityResumed(activity);
            }
        }
    }

    public void onActivityPaused(Activity activity) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityPaused(activity);
            }
        }
    }

    public void onActivityStopped(Activity activity) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityStopped(activity);
            }
        }
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivitySaveInstanceState(activity, outState);
            }
        }
    }

    public void onActivityDestroyed(Activity activity) {
        if (mAllCallbacks != null && mAllCallbacks.size() > 0) {
            for (ActivityLifeCallback callback : mAllCallbacks) {
                callback.onActivityDestroyed(activity);
            }
        }
    }

    public interface ActivityLifeCallback {

        void onActivityCreated(Activity activity, Bundle savedInstanceState);

        void onActivityStarted(Activity activity);

        void onActivityResumed(Activity activity);

        void onActivityPaused(Activity activity);

        void onActivityStopped(Activity activity);

        void onActivitySaveInstanceState(Activity activity, Bundle outState);

        void onActivityDestroyed(Activity activity);
    }
}
