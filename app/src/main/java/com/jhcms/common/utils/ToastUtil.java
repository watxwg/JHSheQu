package com.jhcms.common.utils;

import android.app.Activity;
import android.view.Gravity;
import android.widget.Toast;

import com.jhcms.waimaiV3.MyApplication;


/**
 * Created by Administrator on 2015/12/10.
 */
public class ToastUtil {
    static Toast mToast;
    static Activity topActivity = ActivityCollector.getTopActivity();

    public static void show(int info) {
        show(String.valueOf(Double.valueOf(info)));
    }

    public static void show(String info) {
        if (mToast == null) {
            mToast = Toast.makeText(MyApplication.getContext(), info, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(info);
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 隐藏toast
     */
    public static void hideToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }
}
