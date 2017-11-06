package com.jhcms.common.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.os.Environment;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.jhcms.common.model.Response;
import com.jhcms.waimaiV3.R;
import com.jhcms.waimaiV3.activity.WaiMaiMainActivity;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import cn.finalteam.toolsfinal.ApkUtils;
import okhttp3.Call;

/**
 * Created by admin
 * on 2017/8/18.
 * TODO
 */

public class DownUtils {
    private static boolean isFirstUpdata = true;
    private static KProgressHUD hud;


    /**
     * @param context
     * @param isPress 是否手动更新
     */
    public static void getAppver(Activity context, boolean isPress) {
        if (isPress || isFirstUpdata) {
            HttpUtils.postUrl(context, Api.MAGIC_APPVER, null, false, new OnRequestSuccessCallback() {
                @Override
                public void onSuccess(String url, String Json) {
                    try {
                        Gson gson = new Gson();
                        Response response = gson.fromJson(Json, Response.class);
                        if (response.error.equals("0")) {
                            String version = response.data.apk_client_version;
                            String intro = response.data.apk_client_intro;
                            String force = response.data.apk_client_force_update;
                            String download = response.data.apk_client_download;
                            if (!TextUtils.isEmpty(Utils.getVersion()) && Utils.getVersion().compareTo(version) < 0) {
                                showUpdateDialog(context, intro, download, force);
                            } else {
                                if (isPress) {
                                    ToastUtil.show(context.getString(R.string.当前已是最新本版));
                                }
                                WaiMaiMainActivity.instance.initNotification();
                            }
                            isFirstUpdata = false;
                        }
                    } catch (Exception e) {

                    }
                }

                @Override
                public void onBeforeAnimate() {

                }

                @Override
                public void onErrorAnimate() {
                }
            });
        }
    }


    /**
     * @param context
     * @param intro    更新信息
     * @param download 下载链接
     * @param force    是否强制升级 1 强制 0不强制
     */
    private static void showUpdateDialog(Activity context, String intro, String download, String force) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("发现新版本");
        builder.setMessage(intro);

        if ("1".equals(force)) {
            builder.setCancelable(false);
        } else {
            builder.setCancelable(true);
            builder.setNegativeButton("下次再说", (dialog, which) -> {
                dialog.dismiss();
            });
        }
        builder.setPositiveButton("立刻升级", (dialog, which) -> {
            if (Environment.getExternalStorageState().equals(
                    Environment.MEDIA_MOUNTED)) {
                if (null == hud) {
                    hud = KProgressHUD.create(context)
                            .setStyle(KProgressHUD.Style.BAR_DETERMINATE);
                }
                hud.setMaxProgress(100);
                hud.setCancellable(false);
                hud.setDimAmount(0.3f);
                hud.show();
                try {
                    OkGo.get(download)
                            .tag(context)
                            .execute(new FileCallback(context.getResources().getString(R.string.app_name)) {
                                @Override
                                public void onSuccess(File file, Call call, okhttp3.Response response) {
                                    ApkUtils.install(context, file);
                                    ActivityCollector.removeAllActivity();
                                    context.finish();
                                }

                                @Override
                                public void downloadProgress(long currentSize, long totalSize, float progress, long networkSpeed) {
                                    super.downloadProgress(currentSize, totalSize, progress, networkSpeed);
                                    hud.setLabel((int) (progress * 100) + "%");
                                    hud.setProgress((int) (progress * 100));
                                }
                            });
                } catch (Exception e) {
                    hud.dismiss();
                }
            }
        });
        builder.show();
    }

    public static void cancleOkGo(Context context) {
        //Activity销毁时，取消网络请求
        OkGo.getInstance().cancelTag(context);
    }
}
