package com.youdu.service.andfix;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.TextUtils;

import com.orhanobut.logger.Logger;
import com.youdu.module.andfix.BasePatch;
import com.youdu.network.http.RequestCenter;
import com.youdu.okhttp.listener.DisposeDataListener;
import com.youdu.okhttp.listener.DisposeDownloadListener;

import java.io.File;

/**
 * @function 应用程序热更新服务：
 * 1.从服务器下载patch文件
 * 2.add patch文件到patchManager中
 * 3.检测版本是否发生了更新，一旦发生更新移除所有的老patch
 */
public class AndFixService extends Service {
    private static final String TAG = AndFixService.class.getSimpleName();
    private static final String FILE_END = ".apatch"; //文件后缀名
    private static final int DOWNLOAD_PATCH = 0x01; //下载patch文件信息
    private static final int UPDATE_PATCH = 0x02; //检查是否有patch更新

    private String mPatchFileDir; //patch要保存的文件夹
    private String mFilePtch; //patch文件保存路径
    private BasePatch mBasePatchInfo; //服务器patch信息

    //利用handler完成逻辑上的循环
    private Handler mHanler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case DOWNLOAD_PATCH:
                    downLoadPatch();
                    break;
                case UPDATE_PATCH:
                    checkPatchUpdate();
                    break;
            }
        }
    };

    @Override
    public void onCreate() {
        super.onCreate();
        Logger.d(TAG, "onCreate()");
        init();
    }


    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Logger.d(TAG, "onStartCommand()");
        //检查是否有patch更新
        mHanler.sendEmptyMessage(UPDATE_PATCH);
        return START_NOT_STICKY; //被系统回收不再重启
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //初始化变量
    private void init() {
        mPatchFileDir = getExternalCacheDir().getAbsolutePath() + "/apatch/";
        File patchFileDir = new File(mPatchFileDir);
        try {
            if (patchFileDir == null || !patchFileDir.exists()) {
                patchFileDir.mkdir(); //文件夹不存在则创建
            }
        } catch (Exception e) {
            e.printStackTrace();
            stopSelf(); //无法正常创建文件，则终止服务
        }
    }

    //检查是否有patch更新
    private void checkPatchUpdate() {
        RequestCenter.requestPatchUpdateInfo(new DisposeDataListener() {
            @Override
            public void onSuccess(Object responseObj) {
                //patch信息请求成功
                mBasePatchInfo = (BasePatch) responseObj;
                if (!TextUtils.isEmpty(mBasePatchInfo.data.downloadUrl)) {
                    //不为空，表明有可更新的patch
                    mHanler.sendEmptyMessage(DOWNLOAD_PATCH);
                } else {
                    //没有可更新的patch,直接终止service
                    stopSelf();
                }
            }

            @Override
            public void onFailure(Object reasonObj) {
                //本次请求失败.当作没有patch可更新处理,实际中可以加入3次重试机制
                stopSelf();
            }
        });
    }

    //从服务器下载patch文件
    private void downLoadPatch() {
        Logger.d(TAG, "downLoadPatch()");
        mFilePtch = mPatchFileDir.concat(String.valueOf(System.currentTimeMillis()).concat(FILE_END));
        RequestCenter.downloadFile(mBasePatchInfo.data.downloadUrl, mFilePtch,
                new DisposeDownloadListener() {
                    @Override
                    public void onSuccess(Object responseObj) {
                        Logger.d(TAG, "patch文件下载成功: " + mFilePtch);
                        //patch下载成功后，添加到andfix中
                        AndFixPatchManager.getInstance().addPatch(mFilePtch);
                    }

                    @Override
                    public void onFailure(Object reasonObj) {
                        Logger.d(TAG, "onFailure()");
                        stopSelf();
                    }

                    @Override
                    public void onProgress(int progrss) {
                        Logger.d(TAG, "当前文件下载进度: " + progrss);
                    }
                });
    }
}
