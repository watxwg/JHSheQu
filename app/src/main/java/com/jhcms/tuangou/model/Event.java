package com.jhcms.tuangou.model;

/**
 * Created by admin on 2017/4/18.
 */
public class Event {
    /**
     * 1 关闭popupWimdow
     */
    private  int type;
    private String msg;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Event() {
        super();
    }
}
