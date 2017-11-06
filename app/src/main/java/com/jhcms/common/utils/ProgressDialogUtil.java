package com.jhcms.common.utils;

import android.content.Context;

import com.kaopiz.kprogresshud.KProgressHUD;

/**
 * Created by Jack Wang on 2016/5/6.
 */
public class ProgressDialogUtil {
    private static KProgressHUD dialog;


    public static void showProgressDialog(Context context) {

        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
                dialog = null;
            }
            dialog = KProgressHUD.create(context).setStyle(KProgressHUD.Style.SPIN_INDETERMINATE).setDimAmount(0.3f);
            dialog.show();
            LogUtil.d("ProgressDialogUtil");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void dismiss(Context context) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        } catch (Exception e) {
        }

    }
}

