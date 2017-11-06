package com.jhcms.common.listener;


public interface RequestCallback {
    void onSuccess(String url, String response);
    void onFailure(int errorCode, String msg);

}
