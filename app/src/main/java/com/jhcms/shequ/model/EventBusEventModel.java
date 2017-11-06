package com.jhcms.shequ.model;

/**
 * Created by admin on 2017/6/1.
 */
public class EventBusEventModel {
    /**
     * 0 回到个人中心
     */
    /*
    3 从订单详情页回到订单列表
     */
    private  int type;
    private String message;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
