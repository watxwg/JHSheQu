package com.jhcms.mall.model;

/**
 * Created by admin on 2017/6/5.
 */
public class EventBusModel {
    /**
     * 0 从购物车fragment 回到上一界面
     */
    private int type;
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
}
