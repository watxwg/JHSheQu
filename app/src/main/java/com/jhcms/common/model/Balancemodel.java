package com.jhcms.common.model;

import java.io.Serializable;

/**
 * Created by admin on 2017/6/24.
 */
public class Balancemodel implements Serializable {

    /**
     * log_id : 1166
     * uid : 56
     * type : money
     * number : 35.02
     * intro : 订单(ID:1087)取消退回到余额
     * dateline : 1498199521
     */

    private String log_id;
    private String uid;
    private String type;
    private String number;
    private String intro;
    private String dateline;

    public String getLog_id() {
        return log_id;
    }

    public void setLog_id(String log_id) {
        this.log_id = log_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }
}
