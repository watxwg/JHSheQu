package com.jhcms.waimaiV3.model;

/**
 * Created by wangyujie
 * Date 2017/6/30.
 * TODO
 */

public class MsgBean {
    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean read) {
        isRead = read;
    }

    public boolean isRead;

    public String getMessage_id() {
        return message_id;
    }

    public void setMessage_id(String message_id) {
        this.message_id = message_id;
    }

    public String message_id;

}
