package com.jhcms.mall.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.webkit.WebView;
import android.widget.LinearLayout;

/**
 * 作者：WangWei
 * 日期：2017/9/15 18:32
 * 描述：
 */

public class LinearLayoutForWebView extends LinearLayout {
    public LinearLayoutForWebView(Context context) {
        super(context);
    }

    public LinearLayoutForWebView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public LinearLayoutForWebView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        WebView webView = (WebView) getChildAt(0);
        int y= (int) webView.getScrollY();
        Log.e("TAG", "dispatchTouchEvent: "+webView.getScrollY() );
        if(y!=0){
            getParent().requestDisallowInterceptTouchEvent(true);
        }
        return super.dispatchTouchEvent(ev);
    }
}
