package com.jhcms.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.os.Build;
import android.view.View;
import android.widget.PopupWindow;

/**
 * Created by admin
 * on 2017/8/12.
 * TODO PopupWindow在Android7.0和7.1系统上显示位置不正确的问题解决
 */

public class MyPopupWindow extends PopupWindow {
    public MyPopupWindow(Context context) {
        super(context, null);
    }

    public MyPopupWindow(View view, int matchParent, int height) {
        super(view, matchParent, height);
    }

    @Override
    public void showAsDropDown(View anchor) {
        if(Build.VERSION.SDK_INT == 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = anchor.getResources().getDisplayMetrics().heightPixels - rect.bottom;
            setHeight(h);
        }
        super.showAsDropDown(anchor);
    }
}
