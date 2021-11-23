package com.youdu.tinker;

import android.app.ActivityManager;
import android.content.Context;

import com.tencent.tinker.lib.listener.DefaultPatchListener;

/**
 * @author: qndroid
 * @function: 接收到patch包时的事件监听，主要用来判断patch文件是否合法和patch文件接收到以后的监听
 * @date: 17/3/15
 */
public class CustomPatchListener extends DefaultPatchListener {

    private static final String TAG = "Tinker.CustomPatchListener";

    protected static final long NEW_PATCH_RESTRICTION_SPACE_SIZE_MIN = 60 * 1024 * 1024;

    private final int maxMemory;

    public CustomPatchListener(Context context) {
        super(context);
        maxMemory = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE)).getMemoryClass();
    }

    //检查是否是合法的patch
    @Override
    protected int patchCheck(String path) {
        return super.patchCheck(path);
    }


    //获取到合法的patch后，真正patch前的监听
    @Override
    public int onPatchReceived(String path) {
        return super.onPatchReceived(path);
    }


}
