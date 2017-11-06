package com.jhcms.common.utils;


/**
 * Created by admin on 2017/6/9.
 */
public interface OnRequestSuccessCallback {
    void onSuccess(String url, String Json);
    void onBeforeAnimate();
    void onErrorAnimate();
}
