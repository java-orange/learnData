package com.youdu.service.andfix;

import android.content.Context;

import com.alipay.euler.andfix.patch.PatchManager;
import com.orhanobut.logger.Logger;
import com.youdu.manager.UserManager;
import com.youdu.util.Util;

/**
 * Created by qndroid on 17/1/11.
 * @function 热修复单例类
 * @notice: 1.AndFix存在部分机型的兼容问题，例如：mipad2会报验证失败
 *          2.同一个patch只能加载一次，加载多次会出问题,mi5会继续闪退
 *          3.patch文件是跟用户手机装的应用版本走，必须是对应版本的patch,当用户升级应用后，以前装过的patch都会被移除
 *          4.当某版本程序中出现多个错误时，需要一个个问题修改后，将多个patch文件merge起来(同时修改多个bug还未测试)
 */

public class AndFixPatchManager {

    private static AndFixPatchManager mAndFixPatchManager = null;
    private static PatchManager mPackManager = null;

    public static AndFixPatchManager getInstance() {
        if (mAndFixPatchManager == null) {
            synchronized (UserManager.class) {
                if (mAndFixPatchManager == null) {
                    mAndFixPatchManager = new AndFixPatchManager();
                }
                return mAndFixPatchManager;
            }
        } else {
            return mAndFixPatchManager;
        }
    }

    /**
     * 初始化AndFix
     *
     * @param context 应用上下文
     */
    public void initPatch(Context context) {
        mPackManager = new PatchManager(context);
        mPackManager.init(Util.getVersionName(context));
        mPackManager.loadPatch(); //加载patch文件
    }

    /**
     * 添加patch
     *
     * @param patchPath patch文件路径
     */
    public void addPatch(String patchPath) {
        try {
            if (mPackManager != null) {
                mPackManager.addPatch(patchPath);
                Logger.d("addPatch成功:" + patchPath);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
