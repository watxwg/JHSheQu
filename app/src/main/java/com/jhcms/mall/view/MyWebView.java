package com.jhcms.mall.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;

/**
 * 作者：WangWei
 * 日期：2017/9/15 18:49
 * 描述：
 */

public class MyWebView extends WebView {
    public MyWebView(Context context) {
        super(context);
    }

    public MyWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.e("TAG", "onScrollChanged: "+t );
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.e("tag2", "dispatchTouchEvent: "+getScrollY());
        int i = computeVerticalScrollOffset();
        Log.e("TAG", "dispatchTouchEvent: offset"+i );
        return super.dispatchTouchEvent(ev);

    }
}
