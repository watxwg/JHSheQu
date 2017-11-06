package com.jhcms.common.model;

/**
 * Created by wangyujie
 * Date 2017/6/14.
 * TODO
 */

public class RefreshEvent {
    private String mMsg;
    private boolean mEnable;
    public RefreshEvent(boolean mEnable) {
        this.mEnable = mEnable;
    }
    public Boolean getmEnable() {
        return mEnable;
    }


    public RefreshEvent(String mMsg) {
        this.mMsg = mMsg;
    }

    public String getmMsg() {
        return mMsg;
    }
}

