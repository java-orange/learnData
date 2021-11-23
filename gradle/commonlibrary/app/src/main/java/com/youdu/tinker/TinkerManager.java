package com.youdu.tinker;

import android.content.Context;

import com.tencent.tinker.lib.listener.PatchListener;
import com.tencent.tinker.lib.patch.AbstractPatch;
import com.tencent.tinker.lib.patch.UpgradePatch;
import com.tencent.tinker.lib.reporter.DefaultLoadReporter;
import com.tencent.tinker.lib.reporter.DefaultPatchReporter;
import com.tencent.tinker.lib.reporter.LoadReporter;
import com.tencent.tinker.lib.reporter.PatchReporter;
import com.tencent.tinker.lib.tinker.Tinker;
import com.tencent.tinker.lib.tinker.TinkerInstaller;
import com.tencent.tinker.lib.util.TinkerLog;
import com.tencent.tinker.loader.app.ApplicationLike;
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals;

/**
 * @author: vision
 * @function:
 * @date: 17/3/15
 */
public class TinkerManager {

    private static final String TAG = "Tinker.TinkerManager";
    private static boolean isInstalled = false;
    private static ApplicationLike mAppLike;

    /**
     * 初始化Tinker，必须在最开始调用
     *
     * @param appLike
     */
    public static void installTinker(ApplicationLike appLike) {

        mAppLike = appLike;
        if (isInstalled) {
            TinkerLog.w(TAG, "install tinker, but has installed, ignore");
            return;
        }

        PatchListener patchListener = new CustomPatchListener(appLike.getApplication());
        LoadReporter loadReporter = new DefaultLoadReporter(appLike.getApplication());
        PatchReporter patchReporter = new DefaultPatchReporter(appLike.getApplication());
        AbstractPatch upgradePatchProcessor = new UpgradePatch();

        TinkerInstaller.install(appLike, loadReporter, patchReporter, patchListener, CustomResultService.class, upgradePatchProcessor);

        isInstalled = true;
    }


    /**
     * 加载对应路径的patch文件
     *
     * @param patchFile
     */
    public static void loadPatch(String patchFile) {

        if (Tinker.isTinkerInstalled()) {
            TinkerInstaller.onReceiveUpgradePatch(fetchApplicationContext(), patchFile);
        }
    }

    /**
     * 清除Patch文件
     */
    public static void cleanPatch() {
        if (Tinker.isTinkerInstalled()) {
            Tinker.with(fetchApplicationContext()).cleanPatch();
        }
    }

    /**
     * 清除指定版本的patch文件(都是指应用已经安装过的patch)
     * @param versionName
     */
    public static void cleanPatch(String versionName) {

        if (Tinker.isTinkerInstalled()) {

            Tinker.with(fetchApplicationContext()).cleanPatchByVersion(versionName);
        }
    }

    /**
     * 杀掉patch文件合并进程
     */
    public static void killTinkerProcess() {

        ShareTinkerInternals.killAllOtherProcess(fetchApplicationContext());
    }

    /**
     * 通过AppLike获取到全局Context
     *
     * @return
     */
    private static Context fetchApplicationContext() {

        if (mAppLike != null) {
            return mAppLike.getApplication().getApplicationContext();
        }
        return null;
    }
}


